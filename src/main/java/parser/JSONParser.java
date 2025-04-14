package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class JSONParser {

  /**
   * Method to fix a specific edge case of AI Agent not closing quotation marks in JSON String:
   * @param jsonString
   * @return
   */
  public static String fixMalformedJSON(String jsonString) {
    // Find occurrences of missing closing quotation marks before '}'
    jsonString = jsonString.replaceAll("(\"Rest\": \"[^\"]+)(?=})", "$1\"");

    return jsonString;
  }


  public static ArrayList<JSONContainer> extractJSONCommands(String jsonString){
//    jsonString = "["
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note A\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note B\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note C\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Remove Note 8\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Remove Note 1\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Add Notes Fur Elise 1-8\"}"
//        + "]";


    jsonString = fixMalformedJSON(jsonString); // Fix certain malformed JSON Strings

    // List to store extracted objects
    ArrayList<JSONContainer> JSONCommands = new ArrayList<>();

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonString);
      rootNode = rootNode.get("response");

      if(rootNode.isArray()){
        // Iterate through JSON array and extract each item into JSONContainers
        for (JsonNode node : rootNode) {
          JSONCommands.add(parseNode(node));
        }
      }else {
        JSONCommands.add(parseNode(rootNode));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return JSONCommands;
  }

  private static JSONContainer parseNode(JsonNode node) throws Exception{
    Iterator<String> fieldNames = node.fieldNames();
    JSONContainer jsonContainer = new JSONContainer();

    while (fieldNames.hasNext()) {
      String fieldName = fieldNames.next();
      JsonNode fieldNode = node.get(fieldName);

      if(!fieldNode.isTextual()){
        ObjectMapper objectMapper = new ObjectMapper();
        jsonContainer.put(fieldName, objectMapper.writeValueAsString(fieldNode)); //Throws Exception
      } else{
        jsonContainer.put(fieldName, fieldNode.asText());
      }
    }
    return jsonContainer;
  }

  public static ArrayList<String > spliceNoteCode(String noteCode){
    ArrayList<String> codes = new ArrayList<>(
        Arrays.asList(noteCode.split("\\|"))
    );
    return codes;
  }

  public static ArrayList<String > spliceObjectNameAndNumber(String ObjectNumCombo){
    ArrayList<String> objectNameAndNumberList = new ArrayList<>(
        Arrays.asList(ObjectNumCombo.split("-"))
    );
    return objectNameAndNumberList;
  }


  public static void main(String[] args) {
  //Test
    ArrayList<JSONContainer> commands = JSONParser.extractJSONCommands("[{\"ObjectType\": \"Staff\"}]");
    for(JSONContainer command : commands){
      System.out.println(command);
    }
  }
}
