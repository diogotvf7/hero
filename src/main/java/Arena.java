import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Arena {

    // Class fields
    private int width;
    private int height;
    Hero hero = new Hero(10, 10);

    // Class functions
    Arena(int width_, int height_) {

        width = width_; height = height_;
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

        return ((0 <= position.getX() && position.getX() < width) &&
              (0 <= position.getY() && position.getY() < height));
    };
    public void draw(Screen screen) {

        hero.draw(screen);
    }


}
