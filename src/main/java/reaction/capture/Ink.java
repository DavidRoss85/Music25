package reaction.capture;

import graphics.elements.Box;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import config.UConstants;
import graphics.interfaces.Show;

/**
 * Class for Ink which captures mouse input
 */
public class Ink implements Show, Serializable{
    private static Buffer BUFFER = new Buffer();
    private Norm norm;
    private Box inkBox;

    /**
     * Returns the current ink Buffer
     * @return ink Buffer as {@code Buffer}
     */
    public static Buffer getBuffer(){
        return BUFFER;
    }

    /**
     * Constructor for Ink
     */
    public Ink(){
        norm=new Norm();
        inkBox = BUFFER.bBox.getNewBox();
    }

    public Box getBox(){
        return inkBox;
    }
    /**
     * Getter for Norm
     * @return norm as Norm
     */
    public Norm getNorm(){
        return norm;
    }

    @Override
    public void show(Graphics g) {
        g.setColor(UConstants.inkColor);
        norm.drawAt(g, inkBox);
    }


    //---------List-----------
    /** List of Inks*/
    public static class List extends ArrayList<Ink> implements Show, Serializable{

        @Override
        public void show(Graphics g) {for(Ink ink: this){ink.show(g);}}
    }
}
