package reaction.capture;

import graphics.elements.Box;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import config.UConstants;
import graphics.interfaces.Show;

public class Ink implements Show, Serializable{
    public static Buffer BUFFER = new Buffer();
    public Norm norm;
    public Box vs;
    public Ink(){
        norm=new Norm();
        vs= BUFFER.bBox.getNewVS();
    }

    @Override
    public void show(Graphics g) {g.setColor(UConstants.inkColor); norm.drawAt(g,vs);}


    //---------List-----------
    /** List of Inks*/
    public static class List extends ArrayList<Ink> implements Show, Serializable{

        @Override
        public void show(Graphics g) {for(Ink ink: this){ink.show(g);}}
    }
}
