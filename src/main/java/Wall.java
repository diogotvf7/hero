import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Wall {private Position position;

    // Class methods
    Wall(int x_, int y_) {
        position = new Position(x_, y_) ;
    }

    public Position getPosition() {return position;}
    public void setPosition(Position position) {this.position = position;}

    public void draw(TextGraphics graphics) {

        graphics.setCharacter(position.getX(), position.getY(), TextCharacter.fromCharacter('X')[0]);
        graphics.setForegroundColor(TextColor.Factory.fromString("#38202c"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(),position.getY()), "O");
    }

}
