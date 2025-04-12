package graphics.window;


import chart.ChartNavigator;
import graphics.elements.Box;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import config.UConstants;
import graphics.drawing.G;
import graphics.drawing.Layer;
import java.util.ArrayList;
import llm.LLMFeed;
import masses.Mass;
import masses.MassList;
import masses.glyph.Glyph;
import masses.head.Head;
import masses.key.Key;
import masses.page.Page;
import masses.rest.Rest;
import masses.staff.Staff;
import masses.sys.Sys;
import masses.time.Time;
import parser.JSONContainer;
import parser.JSONParser;
import reaction.action.ActionContainer;
import reaction.capture.Area;
import reaction.capture.*;

import audio.SimpleMidiPlayer;
import reaction.recognition.Shape;

public class MusicEd extends WinApp {



    //public Layer BACK = new Layer("BACK"), FORE = new Layer("FORE");
    static{ //static blocks will run before other code
        new Layer("BACK");
        new Layer("NOTE");
        new Layer("FORE");
    }
    public static final int SCROLL_SPEED = 10;
  public static boolean training = false;
  public static GestureArea DRAW_AREA = new GestureArea();
  public static Area curArea = DRAW_AREA;
  public static Page PAGE;

    //Added by me
    public static SimpleMidiPlayer midiPlayer;

    /**
     * Reaction.initialReactions is a static list in the Reaction class that holds reactions
     */
    public MusicEd(){
        super("Music Editor", UConstants.screenWidth,UConstants.screenHeight);
        PAGE = new Page(50);
//        Reaction.intialReactions.addReaction(new Reaction("W-W") {
//            public int bid(Gesture g) {return 0;}
//            public void act(Gesture g) {
//                int y=g.vs.yM();
//                PAGE=new Page(y);
//                this.disable();
//            }
//        });
    }

    public void paintComponent(Graphics g){
        G.fillBack(g);
        if(training){
          Shape.TRAINER.show(g);}
        g.setColor(Color.BLACK);
        Ink.getBuffer().show(g);
        Layer.ALL.show(g);
        g.drawString(GestureArea.recognized,900,30);
        if(PAGE!=null){
        }
    }

    public void trainButton(MouseEvent me){
        if(me.getX()>UConstants.screenWidth-40 && me.getY()<40){
            training=!training;
            curArea=training?Shape.TRAINER:DRAW_AREA;
        }
    }

    public void mousePressed(MouseEvent me){curArea.cursorDown(me.getX(),me.getY());repaint();}
    public void mouseDragged(MouseEvent me){curArea.cursorDrag(me.getX(),me.getY());repaint();}
    public void mouseReleased(MouseEvent me){
        curArea.cursorUp(me.getX(),me.getY());
        trainButton(me);
        repaint();
    }

    public void keyTyped(KeyEvent ke){
        if(training){Shape.TRAINER.keyTyped(ke);repaint();}
    }

    public void keyPressed(KeyEvent ke){

        if(PAGE!=null){
            if(ke.getKeyCode()==KeyEvent.VK_0){
                //Playmusic code here
                this.renderAndPlay(PAGE);
            }
            if(ke.getKeyCode()==KeyEvent.VK_UP){
                PAGE.pageTop.setDv(
                    PAGE.pageTop.v()<= PAGE.margins.top ?
                        PAGE.pageTop.v()+SCROLL_SPEED
                        : PAGE.margins.top
                );
            }
            if(ke.getKeyCode()==KeyEvent.VK_DOWN){
                PAGE.pageTop.setDv(PAGE.pageTop.v()-SCROLL_SPEED);
            }
        }
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent me){
        int amount=me.getWheelRotation();
        if(PAGE!=null){
            PAGE.pageTop.setDv(
                PAGE.pageTop.v()<= PAGE.margins.top ?
                    PAGE.pageTop.v()-(amount*SCROLL_SPEED)
                    : PAGE.margins.top
            );
        }
        repaint();
    }

    /**
     * Cycles through a specified page and plays all notes as MIDI
     * @param page page object
     */
    private void renderAndPlay(Page page){

        if(page==null || midiPlayer.sequencer.isRunning()){
            return;
        }else{
            midiPlayer.sequencer.close();
        }


        try {
            midiPlayer = new SimpleMidiPlayer(1, 16);
        }catch(Exception e){
            System.out.println(e);
        }


        int PPQ = 16;

        MassList<Sys> sysList = page.sysList;
        int staffCount = sysList.getFirst().staffs.size(); //number of staffs in sys
        int[] lastDuration= new int[staffCount];
        int[] currTime= new int[staffCount];

        try{
            //For each Sys
            for(Sys theSys: sysList ){

                //Each time
                for(Time time: theSys.times){

                    //Get duration if note is a rest
                    for(Rest rest: time.rests){
                        int nFlags = rest.nFlag;
                        int duration = convertFlagToTime(nFlags,PPQ); //length
                        int staffNum = theSys.staffs.indexOf(rest.staff);
                        lastDuration[staffNum]=duration;
                    }
                    //Each head in time
                    for(Head head: time.heads){
                        System.out.println(head.line);
                        int note = convertHeadToNote(head);
                        int duration = calculateDuration(head,PPQ);
                        int staffNum = theSys.staffs.indexOf(head.staff);
                        int channel = staffNum + 1;
                        midiPlayer.addToTrack(channel,note,currTime[staffNum],duration);
                        lastDuration[staffNum]= Math.max(lastDuration[staffNum],duration);
                    }
                    System.out.println("\n\n");

                    for(int i=0;i<staffCount;i++){
                        currTime[i]+=lastDuration[i];
                        lastDuration[i]=0;
                    }
                }
            }

            //play track
            midiPlayer.playTrack();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Converts a head's position to a MIDI note value
     * @param head the head to analyze
     * @return MIDI note value as {@code int}
     */
    private int convertHeadToNote(Head head){
        int note =0;

        int G_KEY = 60; // 60 is Middle C
        int F_KEY = 36; // Two octaves below middle C
        int[] keyArr = {11,9,7,5,4,2,0,-1,-3,-5,-7,-8,-10};// piano scale. Adds to key to produce notes
        int G_OFFSET = 3;
        int F_OFFSET = 1;
        int arrOffset = F_OFFSET;
        int startNote = F_KEY;

        //Sets the offset for the array for note scaling and start note

        if(head.staff.initialClef()==null || head.staff.initialClef().glyph.equals(Glyph.CLEF_G)) {
            startNote = G_KEY;
            arrOffset = G_OFFSET;
        }

        note = startNote + keyArr[head.line + arrOffset]; //note
        note +=convertAccidToGain(head);
        note +=convertKeyToGain(head);
        return note;
    }

    /**
     * Converts an accidental to a note gain or loss
     * @param head the head object for the note
     * @return note gain as {@code int}
     */
    private int convertAccidToGain(Head head){
        if(head.accid==null) return 0;
        return head.accid.iGlyph-2;
    }

    /**
     * Converts key signature to note changes
     * @param head head containing note
     * @return change in note value as {@code int}
     */
    private int convertKeyToGain(Head head){
        int keyN = head.staff.sys.initialKey.n;
        int noteAjustment = 0;
        int[] sArr, fArr;
        if(keyN==0) return 0;
        if(head.staff.initialClef().glyph.equals(Glyph.CLEF_G)) {
            sArr = Key.sG; fArr = Key.fG;
        }else{
            sArr = Key.sF; fArr = Key.fF;
        }

        for(int i=0; i<Math.abs(keyN); i++){
            if(keyN>0 && head.line==sArr[i]){
                noteAjustment++;
                break;
            }
            if(keyN<0 && head.line==fArr[i]){
                noteAjustment--;
                break;
            }
        }
        return noteAjustment;
    }

    /**
     * Calculate the duration of a note
     * @param head head object to reference
     * @param ppq current ppq setBounds
     * @return duration of note as {@code int}
     */
    private int calculateDuration(Head head, int ppq){
        if (head.stem==null) return 0;

        int nFlags = head.stem.nFlag, nDots = head.stem.nDot;

        int duration = convertFlagToTime(nFlags,ppq); //length
        duration += convertDotToTime(duration, nDots);

        return duration;
    }

    /**
     * Converts the flags on a stem to a duration (range -2 to 4).
     * A bpq of < 16 will cause shorter notes to play incorrectly.
     * @param nFlags as {@code int}
     * @param bpq beats per quarter note as {@code int}
     * @return duration as {@code int}
     */
    private int convertFlagToTime(int nFlags, int bpq){
        nFlags*=-1; //more flags... less time

        return (int) (Math.pow(2,nFlags) * bpq);
    }

    /**
     * Adds the dots on a stem to the duration of the note
     * @param duration the duration of the note
     * @param nDots number of dots per head
     * @return new duration
     */
    private int convertDotToTime(int duration, int nDots){
        int durationIncrease = duration;
        int totalIncrease = 0;
        for(int i=0;i<nDots;i++){
            durationIncrease /= 2;
            totalIncrease += durationIncrease;
        }
        return totalIncrease;
    }

    public static void main(String[] args){
      TextBox textField = new TextBox(40);
      textField.setTextFunc(MusicEd::testTextBox);
      PANEL= new MusicEd();
      PANEL.add(textField);
        try{
            midiPlayer = new SimpleMidiPlayer(1,16);
        }catch(Exception e){
            //Silently fail:
            System.out.println(e);
        }
        WinApp.launch();
    }

    private static LLMFeed llmFeed = new LLMFeed();
    private static void testTextBox(String text){

      String objectsJSON = llmFeed.getObjectsJSON(text);
      System.out.println(objectsJSON);
//      String executeJSON = llmFeed.getExecuteJSON(objectsJSON,UConstants.functionDescriptions);
//      System.out.println(executeJSON);

      ArrayList<JSONContainer> jsonList = JSONParser.extractJSONCommands(objectsJSON);
      System.out.println("Objects List:");

      System.out.println(jsonList);



      int x = 80;
      for(JSONContainer json : jsonList){

        ChartNavigator chartNavigator = new ChartNavigator(); // Stores object indexes that point to the right item

        String theObject =  json.get("Object");
        ArrayList<String> theObjectList = JSONParser.spliceNoteCode(theObject); // Separate code into different parts ex "Page-1","Sys-1"
        System.out.println(theObjectList);
        for(String s : theObjectList){
          ArrayList<String> objAndNum = JSONParser.spliceObjectNameAndNumber(s);  // Split item like "Page-1" into array [Page, 1]
          chartNavigator.setItemNum(objAndNum.getFirst(),Integer.parseInt(objAndNum.get(1))); // Map item like "Page" to the chartNavigator
          System.out.println(objAndNum);
          //Function here that will use objAndNum to get  a particular object
        }
        Page myPage;
        Sys mySys;
        Staff myStaff;
        Head myNote;
        try{
          myPage= MusicEd.PAGE;
          mySys = myPage.chartPage.sysList.get(chartNavigator.getItemNum("Sys")).sys;
          myStaff = mySys.chartSys.staffs.get(chartNavigator.getItemNum("Staff")).staff;
          myNote = myStaff.chartStaff.getHead(chartNavigator.getItemNum("Note"));

        }catch(Exception e){
          myPage = MusicEd.PAGE;
          mySys = myPage.chartPage.sysList.getFirst().sys;
          myStaff = mySys.chartSys.staffs.getFirst().staff;

          e.printStackTrace();
        }

        if(chartNavigator.getHighestMentionedItem().equals("Staff")){
          Mass myMass = myStaff;
//            int myValue = (Integer.parseInt(objAndNum.get(1))) -1;
//          Mass myMass = MusicEd.PAGE.chartPage.sysList.get(chartNavigator.getItemNum("Sys"))
//              .staffs.get(chartNavigator.getItemNum("Staff")).staff;
          System.out.println(myMass);
        }


        //--------------------------------------------

        String command = json.get("Rest");
        if(command==null) continue;

        System.out.println("Execution List:");
        String executeJSON = llmFeed.getExecuteJSON(command,UConstants.functionDescriptions);
        System.out.println(executeJSON);

        jsonList = JSONParser.extractJSONCommands(executeJSON);

        for(JSONContainer jsonComm : jsonList){
          System.out.println(jsonComm);
          if(jsonComm.get("FuncName").equals("ADD_NOTE")){
            Staff theStaff = myStaff;// MusicEd.PAGE.sysList.getFirst().staffs.getFirst();
            String note = JSONParser.spliceNoteCode(jsonComm.get("Parameters")).getFirst();
            int y = Staff.convertLetterToLine(note);
            y = theStaff.yOfLine(y);//G.rnd(919-89) + 89;
            y-=7;
            x += 30;//G.rnd(107-40) + 40;
            Box box = new Box(x,y,17,14);
            ActionContainer action = new ActionContainer("ADD_NEW_HEAD",box, null);
            theStaff.doAction(action);
            PANEL.repaint();
          }
        }
      }

//      89 40 36 23
//      311 74 17 14
//      471 57 17 20
//      556 100 34 34
//      668 68 27 23
//      919 107 33 30

    }
}
