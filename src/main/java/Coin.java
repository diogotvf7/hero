import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element {

    // Class methods
    Coin(Position position) {

        super(position);
    }
    public void draw(TextGraphics graphics) {

        //graphics.setCharacter(position.getX(), position.getY(), TextCharacter.fromCharacter('O')[0]);
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFF00"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(),position.getY()), "O");
    }
}
