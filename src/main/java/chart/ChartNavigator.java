package chart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ChartNavigator {

  private String PAGE_STRING = "Page";
  private String SYS_STRING = "Sys";
  private String STAFF_STRING = "Staff";
  private String MEASURE_STRING = "Measure";
  private String NOTES_STRING = "Note";

  private int MIN_INDEX = 1;
  private HashMap<String, Integer> indexMap = new HashMap<>();
  private ArrayList<String> rankList = new ArrayList<>(
      Arrays.asList(PAGE_STRING,SYS_STRING,STAFF_STRING,MEASURE_STRING,NOTES_STRING)
  );
  private Integer highestMentionedRank = 0;

  public ChartNavigator() {
    resetValues();
  }

  private void resetValues() {
    highestMentionedRank = 0;
    indexMap.clear();
    indexMap.put(PAGE_STRING, MIN_INDEX);
    indexMap.put(SYS_STRING, MIN_INDEX);
    indexMap.put(STAFF_STRING, MIN_INDEX);
    indexMap.put(MEASURE_STRING, MIN_INDEX);
    indexMap.put(NOTES_STRING, MIN_INDEX);
  }

  public void setItemNum(String item, int num) {
    highestMentionedRank = Math.max(highestMentionedRank, rankList.indexOf(item));
    indexMap.put(item, num);
  }

  public int getItemNum(String item) {
    return indexMap.get(item) -1;
  }

  public String getHighestMentionedItem() {
    return rankList.get(highestMentionedRank);
  }
}
