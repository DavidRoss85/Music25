package sandbox.llmTests;

//https://docs.langchain4j.dev/integrations/language-models/ollama/

import static dev.langchain4j.model.chat.request.ResponseFormat.JSON;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

class OllamaChatLocalModelTest {
  static String MODEL_NAME = "mistral"; // try other local ollama model names
  static String BASE_URL = "http://localhost:11434"; // local ollama base url

  public static void main(String[] args) {

    ChatLanguageModel model = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .build();
//    String answer = model.chat("List top 10 cites in China");
//    System.out.println(answer);

    model = OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .responseFormat(JSON)
        .build();
    String message =
        """
            What are the first 50 notes in the musical composition Fur Elise?
            Also include the length of each note.
            """;
    String json = model.chat(message);
    System.out.println(json);

  }
  //Speech to text api/ Mozilla
  // Deep speech Music 21 Vex flow
}
