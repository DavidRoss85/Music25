package llm;


//https://docs.langchain4j.dev/integrations/language-models/ollama/
//Speech to text api/ Mozilla
// Deep speech Music 21 Vex flow

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.HashMap;

public class LLMFeed {

  private static final String LAST_TEXT = "\nWhat is your response?";
  private static final String PRE_TEXT = "\nHere is the chat history:\n";

  private ChatLanguageModel chatLanguageModel;
  private String modelName = "mistral"; // try other local ollama model names
  private String base_URL = "http://localhost:11434"; // local ollama base url


  public LLMFeed() {
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(0.0)
        .build();
  }

  public LLMFeed(Double temperature) {
    if(temperature<0.0 || temperature>1.0) {
      temperature = 0.0;
    }
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(temperature)
        .build();
  }

  public LLMFeed(String modelName, String base_URL, Double temperature) {
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

  public String getObjectsJSON(String message){
    String inPrompt =
//    """
//          You are an AI assistant for a musical score application.
//          Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.
//          Whenever you receive a comprehensive request, create a response in JSON format.
//          Create one response for each object, and ensure each object has its own object.
//          Each response should look like:
//          {"ObjectType": "<Object Type>", "ObjectNo": "<Object Number>", "Rest": "<The rest of the command specifying what to do with that object>"}
//          Pay extra attention to the placement of the quotes around values. It must be placed correctly.
//          For example:
//          The request "I want to add 2 notes A and B" to the first staff should yield the response
//          [
//            {"ObjectType": "Staff", "ObjectNo": "1", "Rest": "Add Note A with duration 8"}
//            {"ObjectType": "Staff", "ObjectNo": "1", "Rest": "Add Note B with duration 4"}
//          ]
//          If the user wants to add multiple notes from a song for instance, look up the notes for the song,
//          then for each note create an object like the ones above.
//
//      """;
        """
      You are an AI assistant for a musical score application.
      Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.
      Whenever you receive a comprehensive request, create a response in JSON format.
      Create one response for each object, and ensure each object has its own object.
      Please note that there is a hierarchy of items. A "Page" contains a list of "Sys", a "Sys" contains a list of "Staff",
      and a "Staff"can contain a list of "Note" or a list of "Measure" which can have a list of "Note" as well. So to choose a particular item you would need to specify which number of
      "Page", "Sys", "Staff", and "Note" in the format Page#|Sys#|Staff#|Note#. If no number is given to you for a particular item, assume it is 1.
      Each response should look like:
      {"Object": "Page-#|Sys-#|Staff-#|Note-#", "Rest": "<The rest of the command specifying what to do with that object>"}
      Pay extra attention to the placement of the quotes around values. It must be placed correctly.
      For example:
      The request "I want to add 2 notes A and B" to the first staff should yield the response
      [
        {"Object": "Page-1|Sys-1|Staff-1", "Rest": "Add Note A with duration 8"}
        {"Object": "Page-1|Sys-1|Staff-1", "Rest": "Add Note B with duration 4"}
      ]
      The request "Remove the second note from the third staff of the second page should yield the response:
      [
        {"Object": "Page-2|Sys-1|Staff-3|Note-2", "Rest": "Delete note"}
      ]
      If the user wants to add multiple notes from a song for instance, look up the notes for the song,
      then for each note create an object like the ones above.
  """;


    String inputText = inPrompt + PRE_TEXT + message + LAST_TEXT;
    String response = chatLanguageModel.chat(inputText);
    return response;
  }

  public String getExecuteJSON(String message, HashMap<String, String> commandList){

    String commandString = "";
    String inPrompt =

        """
            You are an AI assistant for a musical score application.
            Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.
            You will receive individual requests to perform on objects that have already been chosen.
            You must choose the most appropriate function to execute from the list provide.
            Each response should look like:
            {"FuncName": <Function name>, "Parameters": <parameters>}.
            If there are multiple actions to perform, or one actionName that requires several actions to complete,
            split it up into several responses and place in brackets.
            For example:
            The request "Add Notes Happy Birthday (1-2) should yield the response
            [
              {"FuncName": "ADD_NOTE", "Parameters": "C"},
              {"FuncName": "ADD_NOTE", "Parameters": "D"}
            ]
            If you need to look up the notes for the song before creating the request, then do that.
            
            Here is a list of available functions with their descriptions:
        """;
//    For example:
//    The request "Add Notes Happy Birthday (1-2) should yield the response
//        [
//        {"FuncName": "ADD_NOTE", "Parameters": "C"},
//    {"FuncName": "ADD_NOTE", "Parameters": "D"}
//            ]
    for (String key : commandList.keySet()) {
      commandString += key + ": " + commandList.get(key) + "\n";
    }

    String inputText = inPrompt + commandString + PRE_TEXT + message + LAST_TEXT;
    String response = chatLanguageModel.chat(inputText);
    return response;
  }

}
