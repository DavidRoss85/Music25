package graphics.window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;

public class TextBox extends JTextField implements KeyListener {

  private String lastText = "";
  private TextFunction textFunc = this::doNothing;

  public TextBox(int size) {
    super(size);
    this.addKeyListener(this);
  }

  public void setTextFunc(TextFunction textFunc) {
    this.textFunc = textFunc;
  }

  public String getTextAndClear() {
    this.lastText = this.getText();
    this.setText("");
    return this.lastText;
  }


  private void doNothing(String text) {}
  /**
   * Invoked when a key has been typed. See the class description for {@link KeyEvent} for a
   * definition of a key typed event.
   *
   * @param ke the event to be processed
   */
  @Override
  public void keyTyped(KeyEvent ke) {
  }

  /**
   * Invoked when a key has been pressed. See the class description for {@link KeyEvent} for a
   * definition of a key pressed event.
   *
   * @param ke the event to be processed
   */
  @Override
  public void keyPressed(KeyEvent ke) {
    if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
      this.textFunc.acceptText(getTextAndClear());
    }
  }

  /**
   * Invoked when a key has been released. See the class description for {@link KeyEvent} for a
   * definition of a key released event.
   *
   * @param ke the event to be processed
   */
  @Override
  public void keyReleased(KeyEvent ke) {

  }
}
