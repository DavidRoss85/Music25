package llm;


//https://docs.langchain4j.dev/integrations/language-models/ollama/
//Speech to text api/ Mozilla
// Deep speech Music 21 Vex flow

import config.UConstants;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.HashMap;

public class LLMFeed {

  private static final String FIND_OBJECT_PROMPT = UConstants.LanguageModelIdentifyObjectPrompt;
  private static final String EXECUTE_ACTION_PROMPT = UConstants.LanguageExecuteActionPrompt;

  private static final String LAST_TEXT = "\nWhat is your response?";
  private static final String PRE_TEXT = "\nHere is the chat history:\n";



  private ChatLanguageModel chatLanguageModel;
  private String modelName = "mistral"; // try other local ollama model names
  private String base_URL = "http://localhost:11434"; // local ollama base url


  private void llmFeedConstructor(String modelName, String base_URL, Double temperature){
    if(temperature<0.0 || temperature>1.0) {
      temperature = 0.0;
    }
    chatLanguageModel = OllamaChatModel.builder()
        .baseUrl(base_URL)
        .modelName(modelName)
        .temperature(temperature)
        .responseFormat(ResponseFormat.JSON)
        .build();
  }

  public LLMFeed() {
    this.llmFeedConstructor(this.modelName, this.base_URL, 0.0);
  }

  public LLMFeed(Double temperature) {
    this.llmFeedConstructor(this.modelName, this.base_URL, temperature);
  }

  public LLMFeed(String modelName, String base_URL, Double temperature) {
    this.llmFeedConstructor(modelName, base_URL, temperature);
  }

  public String getGenericResponse(String message){
    message += "\nWhat is your response?";
    String response = chatLanguageModel.chat(message);
    return response;
  }

  public String getObjectsJSON(String message){
    String inPrompt = FIND_OBJECT_PROMPT;


    String inputText = inPrompt + PRE_TEXT + message + LAST_TEXT;
    String response = chatLanguageModel.chat(inputText);
    return response;
  }

  public String getExecuteJSON(String message, HashMap<String, String> commandList){

    StringBuilder commandString = new StringBuilder();
    commandString.append(" \"available_functions\": [\n");

    for (String key : commandList.keySet()) {
      commandString.append("{\"FuncName\": \"" +  key + "\", \"Description\": \"" + commandList.get(key) + "\"},\n");
    }
    commandString.append("]\n");
    String inPrompt = EXECUTE_ACTION_PROMPT.formatted(commandString.toString());

    String inputText = inPrompt + PRE_TEXT + message + LAST_TEXT;
    String response = chatLanguageModel.chat(inputText);
    return response;
  }

}

