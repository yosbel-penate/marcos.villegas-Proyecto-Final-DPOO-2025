package businesslogic;
import java.util.Random;

public class NecromanteEnemigo extends Enemy {
    private Random random = new Random();

    public NecromanteEnemigo(int x, int y, int health, int damage) {
        super(x, y, health, damage, 5);
    }

    @Override
    public void moveTowardsPlayer(PlayerCharacter player) {
        int dx = Integer.compare(player.getX(), x);
        int dy = Integer.compare(player.getY(), y);
        int newX = x + dx;
        int newY = y + dy;
        GameLogic logic = GameLogic.getInstance();
        for (Trap t : logic.getTraps()) {
            if (t.getX() == newX && t.getY() == newY && t.isActive() && t.getType().equals("Pitfall")) {
                health = 0;
                logic.addGameMessage("Necromante cae en una trampa y muere.");
                return;
            }
        }
        if (newX >= 0 && newX < GameLogic.BOARD_SIZE && !logic.isOccupied(newX, y)) {
            x = newX;
        } else if (newY >= 0 && newY < GameLogic.BOARD_SIZE && !logic.isOccupied(x, newY)) {
            y = newY;
        }
    }

    @Override
    public void attack(PlayerCharacter player) {
        GameLogic logic = GameLogic.getInstance();
        if (Math.abs(player.getX() - x) <= range && Math.abs(player.getY() - y) <= range) {
            player.setHealth(player.getHealth() - damage);
            logic.addGameMessage("Necromante ataca a " + player.getName() + " (-" + damage + " HP).");
            if (random.nextBoolean() && logic.getEnemies().size() < 10) {
                int newX, newY;
                do {
                    newX = x + random.nextInt(3) - 1;
                    newY = y + random.nextInt(3) - 1;
                } while (logic.isOccupied(newX, newY) || newX < 0 || newX >= GameLogic.BOARD_SIZE || newY < 0 || newY >= GameLogic.BOARD_SIZE);
                Enemy newEnemy = random.nextBoolean() ?
                        new ZombieComun(newX, newY, 50, 10) :
                        new EsqueletoArmado(newX, newY, 40, 15);
                logic.getEnemies().add(newEnemy);
                logic.getTurnOrder().add(newEnemy);
                logic.addGameMessage("Necromante invoca un nuevo enemigo.");
            }
        }
    }
}
