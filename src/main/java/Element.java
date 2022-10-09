import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public abstract class Element {

    // Class fields
    protected Position position;

    // Class methods
    Element(Position position) {

        this.position = position;
    }
    public Position getPosition() {return position;}
    public void setPosition(Position pos) {this.position = pos;}
    public abstract void draw(TextGraphics graphics);
}
