package parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;

public class JSONParser {

  public static ArrayList<JSONContainer> extractJSONCommands(String jsonString){
//    jsonString = "["
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note A\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note B\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"1\", \"Rest\": \"Add Note C\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Remove Note 8\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Remove Note 1\"},"
//        + "{\"ObjectType\": \"Staff\", \"ObjectNo\": \"3\", \"Rest\": \"Add Notes Fur Elise 1-8\"}"
//        + "]";
    // List to store extracted objects
    ArrayList<JSONContainer> JSONCommands = new ArrayList<>();

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode rootNode = objectMapper.readTree(jsonString);


      // Iterate through JSON array and extract each item into JSONContainers
      for (JsonNode node : rootNode) {

        Iterator<String> fieldNames = node.fieldNames();
        JSONContainer jsonContainer = new JSONContainer();

        while (fieldNames.hasNext()) {
          String fieldName = fieldNames.next();
          jsonContainer.put(fieldName,node.get(fieldName).asText());
        }
        JSONCommands.add(jsonContainer);

      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return JSONCommands;
  }

  public static void main(String[] args) {
  //Test
    ArrayList<JSONContainer> commands = JSONParser.extractJSONCommands("[{\"ObjectType\": \"Staff\"}]");
    for(JSONContainer command : commands){
      System.out.println(command);
    }
  }
}
