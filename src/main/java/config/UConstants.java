package config;

import java.awt.Color;
import java.util.HashMap;

public class UConstants {
  public static final int screenWidth = 1000;
  public static final int screenHeight = 750;
  public static final int inkBufferMax = 200;
  public static Color inkColor = Color.BLACK;
  public static final int normSampleSize = 25;
  public static final int normCoordMax = 1000;
  public static final int noMatchDist = 700_000;
  public static final int dotThreshold = 5;
  public static String shapeDatabaseFileName = "shapeDB.dat";
  public static final int noBid = 10000;
  public static final int minStaffGap = 40;
  public static final int minSysGap = 40;
  public static final int barToMarginSnap = 20;
  public static final String FontName = "sinfonia";
  public static final int snapTime = 30;
  public static final int AugDotOffset = 28;
  public static final int AugDotSpacing = 11;
  public static final int initialClefOffset = 30;
  public static final int marginKeyOffset = 60;
  public static final int barKeyOffset = 10;
  public static final int headAccidOffset = 25;

  public static HashMap<String, String> functionDescriptions = new HashMap<>();
  static {
    functionDescriptions.put("ADD_NOTE", "Adds a new note head to the musical score sheet with the designated tone and length. "
        + "The Parameters for this command are the note and the duration formatted as a single string separated by a | delimiter. \"<Note letter>|<Octave>|<Accidental>|<Duration>|<Dots>|<Extras> for example: A C# in the 4th Octave quarter note with 1 dot would be \"C|4|#|8|1|L\" "); // Parameters should be in format {"Note": "<Note>", "Duration": "<Length>" }
    functionDescriptions.put("ADD_REST", "Adds a new rest period to the musical score sheet with the designated length. "
        + "The Parameters for this command is the time period. for example: A quarter rest would b 8"); // Parameters should be in format {"Note": "<Note>", "Duration": "<Length>" }

    functionDescriptions.put("IMPORT_MUSIC", "Imports all or most of the notes from a song to the musical score sheet.");
    functionDescriptions.put("LOWER", "Lowers the pitch of the selected note or group of notes by a predefined interval.");
    functionDescriptions.put("RAISE", "Raises the pitch of the selected note or group of notes by a predefined interval.");
    functionDescriptions.put("DELETE_NOTE", "Removes a specific note from the musical score sheet.");
    functionDescriptions.put("UNDO", "Reverts the last performed actionName on the musical score sheet.");
    functionDescriptions.put("REDO", "Restores the last undone actionName on the musical score sheet.");
    functionDescriptions.put("CHANGE_TEMPO", "Adjusts the speed of the musical score to a new beats-per-minute value.");
    functionDescriptions.put("APPLY_ARTICULATION", "Adds articulation marks to selected notes, such as staccato or legato.");
    functionDescriptions.put("TRANSPOSITION", "Shifts all notes in the score up or down by a given number of semitones.");
    functionDescriptions.put("EXPORT_PDF", "Exports the musical score as a PDF file for printing or sharing.");
    functionDescriptions.put("SAVE_PROJECT", "Saves the current musical score and all changes to a file.");
    functionDescriptions.put("PLAYBACK", "Plays the musical score using MIDI synthesis.");
    functionDescriptions.put("FIND_NOTE", "Locate the specified note in the musical score sheet to perform actions on it. You must do this before you can take actionName on any note.");
    functionDescriptions.put("GET_ACTIONS", "Gets the list of actions available to the object.");

  }

}
