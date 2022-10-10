import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    // Class fields
    private Screen screen;

    private int width;
    private int height;
    private Arena arena = new Arena(width, height);
    Hero hero = new Hero(new Position(40, 12));
    private List<Wall> walls;
    private List<Coin> coins;
    private List<Monster> monsters;

    // Class methods
    Game() {
        
        try {
            TerminalSize terminalSize = new TerminalSize(80, 24);
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize);
            Terminal terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(null); // we don't need a cursor
            screen.startScreen(); // screens must be started
            screen.doResizeIfNecessary(); // resize screen if necessary
            this.width = 80; this.height = 24;
            this.walls = createWalls(); this.coins = createCoins(); this.monsters = createMonsters();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
    private List<Monster> createMonsters() {

        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(new Position(1, 1)));
        monsters.add(new Monster(new Position(width - 2, 1)));
        monsters.add(new Monster(new Position(width - 2, height - 2)));
        monsters.add(new Monster(new Position(1, height - 2)));
        return monsters;
    }
    private void processKey(KeyStroke key) throws IOException {

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
    private void moveHero(Position position) {

        if (canHeroMove(position))
            hero.setPosition(position);
    }
    private void moveMonsters() {

        for (Monster monster : monsters) {

            boolean go_right = monster.getPosition().getX() < hero.getPosition().getX();
            boolean go_up = monster.getPosition().getY() > hero.getPosition().getY();

            Random rand = new Random(); int j = rand.nextInt(2);

            if (j == 0) {
                if (go_up && canMonsterMove(monster.moveUp())) {monster.setPosition(new Position(monster.getPosition().getX() + 0, monster.getPosition().getY() - 1));}
                else if (!go_up && canMonsterMove(monster.moveDown())) {monster.setPosition(new Position(monster.getPosition().getX() + 0, monster.getPosition().getY() + 1));}
                else if (go_right && canMonsterMove(monster.moveRight())) {monster.setPosition(new Position(monster.getPosition().getX() + 1, monster.getPosition().getY() + 0));}
                else if (!go_right && canMonsterMove(monster.moveLeft())) {monster.setPosition(new Position(monster.getPosition().getX() - 1, monster.getPosition().getY() + 0));}
            } else {
                if (go_right && canMonsterMove(monster.moveRight())) {monster.setPosition(new Position(monster.getPosition().getX() + 1, monster.getPosition().getY() + 0));}
                else if (!go_right && canMonsterMove(monster.moveLeft())) {monster.setPosition(new Position(monster.getPosition().getX() - 1, monster.getPosition().getY() + 0));}
                else if (go_up && canMonsterMove(monster.moveUp())) {monster.setPosition(new Position(monster.getPosition().getX() + 0, monster.getPosition().getY() - 1));}
                else if (!go_up && canMonsterMove(monster.moveDown())) {monster.setPosition(new Position(monster.getPosition().getX() + 0, monster.getPosition().getY() + 1));}
            }

        }
    }
    private boolean canMonsterMove(Position position) {

        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(position))
                return false;
        }
        return true;
    }
    private boolean canHeroMove(Position position) {

        for (Wall wall : walls)
            if (wall.getPosition().equals(position))
                return false;

        return true;
    };
    private void retrieveCoins() {

        for (Coin coin : coins) {

            if (hero.getPosition().equals(coin.getPosition())) {
                coins.remove(coin);
                break;
            }
        }
    }
    private boolean verifyMonsterHeroCollision() {

        for (Monster monster1 : monsters) {

            if (monster1.getPosition().equals(hero.getPosition()))
                return true;
        }
        return false;
    }
    private void draw() throws IOException {
        screen.clear();
        arena.draw(screen.newTextGraphics());
        hero.draw(screen.newTextGraphics());
        for (Wall wall : walls)
            wall.draw(screen.newTextGraphics());
        for (Coin coin : coins)
            coin.draw(screen.newTextGraphics());
        for (Monster monster : monsters)
            monster.draw(screen.newTextGraphics());
        screen.refresh();
    }
    public void run() throws IOException {

        while (true) {

            draw();
            KeyStroke key = screen.readInput();
            if ((key.getKeyType() == KeyType.Character && key.getCharacter() == 'q') || key.getKeyType() == KeyType.EOF) {

                screen.close();
                break;
            }
            processKey(key);
            moveMonsters();
            retrieveCoins();
            if (coins.size() == 0) {

                screen.close();
                System.out.println("+------------------+\n" +
                                   "|    You win!!!    |\n" +
                                   "+------------------+");
            }
            if (verifyMonsterHeroCollision()) {
                screen.close();
                System.out.println("+------------------+\n" +
                                   "|    You lost!!!   |\n" +
                                   "+------------------+");
            }
        }
    }

}
