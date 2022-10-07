import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arena {

    // Class fields
    private int width;
    private int height;
    Hero hero = new Hero(10, 10);
    private List<Wall> walls;

    // Class functions
    Arena(int width_, int height_) {

        width = width_; height = height_; this.walls = createWalls();
    }
    public void processKey(KeyStroke key) throws IOException {

        switch (key.getKeyType()) {

            case ArrowUp:
                moveHero(hero.moveUp());
                break;
            case ArrowRight:
                moveHero(hero.moveRight());
                break;
            case ArrowDown:
                moveHero(hero.moveDown());
                break;
            case ArrowLeft:
                moveHero(hero.moveLeft());
                break;
        }
    }
    public void moveHero(Position position) {

        if (canHeroMove(position))
            hero.setPosition(position);
    }
    public boolean canHeroMove(Position position) {

        return ((1 <= position.getX() && position.getX() < width - 1) &&
              (1 <= position.getY() && position.getY() < height - 1));
    };
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }
    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#583660"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        for (Wall wall : walls)
            wall.draw(graphics);
    }


}
