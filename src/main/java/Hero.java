import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Hero {

    // Class fields
    private int x;
    private int y;

    // Class methods
    Hero(int x_, int y_) {
        x = x_; y = y_;
    }

    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}

    public void moveUp() {y--;}
    public void moveRight() {x++;}
    public void moveDown() {y++;}
    public void moveLeft() {x--;}

    public void draw(Screen screen) {
        screen.setCharacter(x, y, TextCharacter.fromCharacter('X')[0]);
    }


}
