package sandbox.llmTests;


//https://docs.langchain4j.dev/integrations/language-models/ollama/
//Speech to text api/ Mozilla
// Deep speech Music 21 Vex flow


import static dev.langchain4j.model.chat.request.ResponseFormat.JSON;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.ArrayList;
import java.util.HashMap;

class TestLLMChooseFunctionsFromList {
  static String MODEL_NAME = "mistral"; // try other local ollama model names
  static String BASE_URL = "http://localhost:11434"; // local ollama base url
  static HashMap<String, String> functionDescriptions = new HashMap<>();
  static {
    functionDescriptions.put("ADD_NOTE", "Adds a new note head to the musical score sheet with the designated tone and length. Parameters should be in format {Note: <Note>, Duration: <Length> }");
    functionDescriptions.put("IMPORT_MUSIC", "Imports all or most of the notes from a song to the musical score sheet.");
    functionDescriptions.put("LOWER", "Lowers the pitch of the selected note or group of notes by a predefined interval.");
    functionDescriptions.put("RAISE", "Raises the pitch of the selected note or group of notes by a predefined interval.");
    functionDescriptions.put("DELETE_NOTE", "Removes a specific note from the musical score sheet.");
    functionDescriptions.put("UNDO", "Reverts the last performed action on the musical score sheet.");
    functionDescriptions.put("REDO", "Restores the last undone action on the musical score sheet.");
    functionDescriptions.put("CHANGE_TEMPO", "Adjusts the speed of the musical score to a new beats-per-minute value.");
    functionDescriptions.put("APPLY_ARTICULATION", "Adds articulation marks to selected notes, such as staccato or legato.");
    functionDescriptions.put("TRANSPOSITION", "Shifts all notes in the score up or down by a given number of semitones.");
    functionDescriptions.put("EXPORT_PDF", "Exports the musical score as a PDF file for printing or sharing.");
    functionDescriptions.put("SAVE_PROJECT", "Saves the current musical score and all changes to a file.");
    functionDescriptions.put("PLAYBACK", "Plays the musical score using MIDI synthesis.");
    functionDescriptions.put("FIND_NOTE", "Locate the specified note in the musical score sheet to perform actions on it. You must do this before you can take action on any note.");
    functionDescriptions.put("GET_ACTIONS", "Gets the list of actions available to the object.");

  }


  public static void main(String[] args) {

    System.out.println("\n\n********OBJECT FIND*********");
    ArrayList<String> commandList = extractJSON(llmGetObjectTest());
    System.out.println("\n\n********OBJECT EXECUTE*********");
    for(String command : commandList) {
      llmExecuteTest(command);
    }


  }


  private static void llmExecuteTest(String command) {

    ChatLanguageModel model = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
//        .responseFormat(JSON)
        .temperature(0.0)
        .build();
    String message =

        """
            You are an AI assistant for a musical score application.
            Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.
            You will receive individual requests to perform on objects that have already been chosen.
            You must choose the most appropriate function to execute from the list provide.
            Each response should look like:
            {FuncName: <Function name>, Parameters: <parameters>}
            If there are multiple actions to perform, or one action that requires several actions to complete,
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

    for (String key : functionDescriptions.keySet()) {
      message += key + ": " + functionDescriptions.get(key) + "\n";
    }
    message += "Here is the chat history:\n"; // User: Add Notes C D A B C with the lengths 1 3 2 1 1 respectively.";
    message += command + "\n";
//    message += "I also want to remove the second note, the eighth note, and the first note from the 3rd staff.";
//    message += "Then I want to add the first 8 notes from Fur Elise to the 3rd staff.";
//    message += """
//        Assistant: {
//                             "Target": "FIND_NOTE",
//                             "Command": null,
//                             "Parameters": {
//                                "Notes": ["A", "B", "C"]
//                             }
//                          }
//        User: Target is NoteObjectXYZ
//        """;
    message += "What is your response?";
    String json = model.chat(message);
    System.out.println(json);

  }


  private static String llmGetObjectTest(){
    ChatLanguageModel model = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .build();
//    String answer = model.chat("List top 10 cites in China");
//    System.out.println(answer);

    model = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
//        .responseFormat(JSON)
        .temperature(0.0)
        .build();
    String message =
//        """
//        You are an AI assistant that helps manage a musical score application.
//        Your role is to interpret my requests and map them to the most relevant command from the predefined dictionary.
//        When I describe an action I want to perform, you should select the best command that matches my intent and return it.
//        Here are a list of the commands and their descriptions.:
//        """;
        """
            You are an AI assistant for a musical score application.
            Your job is to interpret my requests and map them to the most relevant command from a predefined dictionary.
            Whenever you receive a comprehensive request, create a response in JSON format.
            Create one response for each object, and ensure each object has its own object.
            Each response should look like:
            {ObjectType: <Object Type>, ObjectNo: <Object Number>, Rest: <The rest of the command specifying what to do with that object>}
            For example:
            The request "I want to add 2 notes A and B" to the first staff should yield the response
            [
              {"ObjectType": "Staff", "ObjectNo": "1", "Rest": "Add Note A"}
              {"ObjectType": "Staff", "ObjectNo": "1", "Rest": "Add Note B"}
            ]
            If the user wants to add multiple notes from a song for instance, look up the notes for the song,
            then for each note create an object like the ones above.
        """;

//    for (String key : functionDescriptions.keySet()) {
//      message += key + ": " + functionDescriptions.get(key) + "\n";
//    }
//    message += "Format your JSON answers in the following format:\n "
//        + "{Target: <The target of the command>, Command: <The command name>, Parameter: <Arguments>}";
//    message += "Rules:\n Notes go on staffs so to add a note target the staff.";
//    message += "If you need to divide your actions into several commands or JSONs, feel free to do so";
    message += "Here is the chat history:\n User: I want to add 3 notes to the first staff. A,B, and C.";
    message += "I also want to remove the second note, the eighth note, and the first note from the 3rd staff.";
    message += "Then I want to add the first 8 notes from Happy Birthday to the 3rd staff.";
//    message += """
//        Assistant: {
//                             "Target": "FIND_NOTE",
//                             "Command": null,
//                             "Parameters": {
//                                "Notes": ["A", "B", "C"]
//                             }
//                          }
//        User: Target is NoteObjectXYZ
//        """;
    message += "What is your response?";
    String json = model.chat(message);
    System.out.println(json);
    return json;
  }

  private static ArrayList<String> extractJSON(String jsonString){
//    String jsonString = "["
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 1, \"Rest\": \"Add Note A\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 1, \"Rest\": \"Add Note B\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 1, \"Rest\": \"Add Note C\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 3, \"Rest\": \"Remove Note 8\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 3, \"Rest\": \"Remove Note 1\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": 3, \"Rest\": \"Add Notes Fur Elise 1-8\"}"
//        + "]";
    // List to store extracted objects
    ArrayList<String> musicObjects = new ArrayList<>();

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonString);


      // Iterate through JSON array and extract each item into MusicObject instances
      for (JsonNode node : rootNode) {
        String objectType = node.get("ObjectType").asText();
        int objectNo = node.get("ObjectNo").asInt();
        String rest = node.get("Rest").asText();

        musicObjects.add(rest);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return musicObjects;
  }

}



