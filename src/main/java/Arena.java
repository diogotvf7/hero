import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    // Class fields
    private int width;
    private int height;
    Hero hero = new Hero(new Position(10, 10));
    private List<Wall> walls;
    private List<Coin> coins;

    // Class functions
    Arena(int width_, int height_) {

        width = width_; height = height_; this.walls = createWalls(); this.coins = createCoins();
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

        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;

        return true;
    };
    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(new Position(c, 0)));
            walls.add(new Wall(new Position(c, height - 1)));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(new Position(0, r)));
            walls.add(new Wall(new Position(width - 1, r)));
        }
        return walls;
    }
    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        while (coins.size() < 5) {

            Position random_position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            if (!random_position.equals(hero.getPosition())) {
                coins.add(new Coin(random_position));
            }
        }
        return coins;
    }
    private void retrieveCoins() {

        for (Coin coin : coins) {

            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }
    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(graphics);
        retrieveCoins();
        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
    }


}
