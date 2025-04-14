package sandbox.llmTests;


//https://docs.langchain4j.dev/integrations/language-models/ollama/
//Speech to text api/ Mozilla
// Deep speech Music 21 Vex flow

import config.UConstants;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.ArrayList;
import java.util.HashMap;
import parser.JSONContainer;
import parser.JSONParser;

public class TestLLMJSONProduction {

  private static final String LAST_TEXT = "";//"\nWhat is your response?";
  private static final String PRE_TEXT = "\nHere is the chat history:\n";

  private ChatLanguageModel chatLanguageModel;
  private String modelName = "mistral"; // try other local ollama model names
  private String base_URL = "http://localhost:11434"; // local ollama base url


  public TestLLMJSONProduction() {
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(0.0)
        .responseFormat(ResponseFormat.JSON)
        .build();
  }

  public TestLLMJSONProduction(Double temperature) {
    if(temperature<0.0 || temperature>1.0) {
      temperature = 0.0;
    }
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(temperature)
        .build();
  }

  public TestLLMJSONProduction(String modelName, String base_URL, Double temperature) {
    if(temperature<0.0 || temperature>1.0) {
      temperature = 0.0;
    }
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(temperature)
        .build();
  }

  public String getGenericResponse(String message){
    message += "\nWhat is your response?";
    String response = chatLanguageModel.chat(message);
    return response;
  }

  public String testJSONResponse(String message){
    String inPrompt =
      """
  
       {
         "instruction":  You are an AI assistant for a musical score application. Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.,
         "requirements": {
           "response_format": "Always return responses in valid JSON format.",
           "details": {
             "detail1": "Whenever you receive a comprehensive request, create a response in JSON format.",
             "detail2": "Create one JSON response for each object, and ensure each item has its own JSON object.",
             "detail3": "There is a hierarchy of items. A \"Page\" contains a list of \"Sys\", a \"Sys\" contains a list of \"Staff\", and a \"Staff\" can contain a list of \"Note\" or a list of \"Measure\" which can have a list of \"Note\" as well. So to choose a particular item you would need to specify which number of \"Page\", \"Sys\", \"Staff\", and \"Note\" in the format Page#|Sys#|Staff#|Note#. If no number is given to you for a particular item, assume it is 1.",
             "detail4": "The response should be an array of objects, each with 1 object and 1 parameter. Create as many objects as necessary to execute all parameters.",
           }
           "data_structure": [
             {"object":  "Page-#|Sys-#|Staff-#|Note-#", "parameters": "<The rest of the command specifying what to do with that object>"},
             {"object":  "Page-#|Sys-#|Staff-#|Note-#", "parameters": "<The rest of the command specifying what to do with that object>"},
           ],
          
         },
          "examples": [
            {
              "request": "I want to add 2 notes A and B to the first staff",
               "response":[
                 {"object": "Page-1|Sys-1|Staff-1", "parameters": "Add Note A with duration 8"},
                 {"object": "Page-1|Sys-1|Staff-1", "parameters": "Add Note B with duration 4"},
               ],
            }
          ]
       }
         
      """;


    String inputText = inPrompt + PRE_TEXT + message + LAST_TEXT;
    String response = chatLanguageModel.chat(inputText);
    return response;
  }

  public String testJSONResponse2(String message, HashMap<String,String> commandList){
    StringBuilder commandString = new StringBuilder();
    commandString.append(" \"available_functions\": [\n");

        for (String key : commandList.keySet()) {
          commandString.append("{\"FuncName\": \"" +  key + "\", \"Description\": \"" + commandList.get(key) + "\"},\n");
        }
        commandString.append("]\n");
    String inPrompt =
        """
            {
              "instruction": "You are an AI assistant for a musical score application. Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.",
              "requirements": {
                "response_format": "Always return responses in valid JSON format.",
                "details": {
                  "detail1": "Whenever you receive an individual request, create a response in JSON format.",
                  "detail2": "Each response should contain one function and its respective parameters.",
                  "detail3": "If an action requires multiple steps or functions, return multiple JSON objects inside an array.",
                  "detail4": "If necessary, lookup additional information (e.g., song notes) before creating the response."
                }
              },
              "data_structure": [
                {
                  "FuncName": "<Function name>",
                  "Parameters": "<parameters for execution>"
                },
                {
                  "FuncName": "<Function name>",
                  "Parameters": "<parameters for execution>"
                }
              ],
              "examples": [
                {
                  "request": "Add Notes Happy Birthday (1-2)",
                  "response": [
                    {
                      "FuncName": "ADD_NOTE",
                      "Parameters": "C"
                    },
                    {
                      "FuncName": "ADD_NOTE",
                      "Parameters": "D"
                    }
                  ]
                }
              ],
              %s
            }
        """.formatted(commandString.toString());


        String inputText = inPrompt + PRE_TEXT + message + LAST_TEXT;
        String response = chatLanguageModel.chat(inputText);
        return response;

//    "available_functions": [
//    {
//      "FuncName": "ADD_NOTE",
//        "Description": "Adds a new note head to the musical score sheet with the designated tone and length. The Parameters for this command are the note and the duration formatted as a single string separated by a | delimiter. Fill empty values with N/A. \"<Note letter>|<Octave>|<Accidental>|<Duration>|<Dots>|<Extras> for example: A C# in the 4th Octave quarter note with 1 dot would be \"C|4|#|8|1|L\" "
//    },
//    {
//      "FuncName": "DELETE_NOTE",
//        "Description": "Removes a specified note from the score."
//    },
//    {
//      "FuncName": "CHANGE_DURATION",
//        "Description": "Modifies the duration of a selected note."
//    }
//              ]
  }

  public static void main(String[] args){
    TestLLMJSONProduction tester = new TestLLMJSONProduction();

    String response = tester.testJSONResponse("Add notes a b c d");
    System.out.println(response);

    ArrayList<JSONContainer> jsonList = JSONParser.extractJSONCommands(response);
    System.out.println("Objects List:");

    System.out.println(jsonList);

//    jsonList = JSONParser.extractJSONCommands(jsonList.get(0).get("response"));
//    System.out.println("Objects List:");
//
//    System.out.println(jsonList);
    response = tester.testJSONResponse2("Add Note with pitch 'F' and duration 8", UConstants.functionDescriptions);
    System.out.println(response);

  }

}


//************************** TEMPLATE **********************************
//
//      """
//
//       {
//         "instruction":  You are an AI assistant for a musical score application. Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.,
//         "requirements": {
//           "response_format": "Always return responses in valid JSON format.",
//           "data_structure": {
//             "command": "String specifying the function to activate.",
//             "parameters": "Object containing necessary parameters for execution."
//           },
//           "error_handling": "If an error occurs, return a JSON object with an 'error' field containing details."
//         },
//         "examples": [
//           {
//             "command": "fetch_user_data",
//             "parameters": {
//               "user_id": "12345"
//             }
//           },
//           {
//             "error": "Invalid command. Available commands: ['fetch_user_data', 'update_settings', 'send_notification']"
//           }
//         ]
//       }
//
//      """;

