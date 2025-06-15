package businesslogic;
import java.util.Random;

public class Fantasma extends Enemy {
    private Random random = new Random();

    public Fantasma(int x, int y, int health, int damage) {
        super(x, y, health, damage, 2); // Higher range for ghostly attacks
    }

    @Override
    public void moveTowardsPlayer(PlayerCharacter player) {
        int dx = Integer.compare(player.getX(), x);
        int dy = Integer.compare(player.getY(), y);
        int newX = x + dx;
        int newY = y + dy;
        GameLogic logic = GameLogic.getInstance();
        // Fantasma can move through obstacles but checks for traps
        for (Trap t : logic.getTraps()) {
            if (t.getX() == newX && t.getY() == newY && t.isActive() && t.getType().equals("Pitfall")) {
                health = 0;
                logic.addGameMessage("Fantasma cae en una trampa y se disipa.");
                return;
            }
        }
        // Only check for player/enemy occupation, not obstacles
        if (newX >= 0 && newX < GameLogic.BOARD_SIZE && !isOccupiedByEntity(newX, y)) {
            x = newX;
        } else if (newY >= 0 && newY < GameLogic.BOARD_SIZE && !isOccupiedByEntity(x, newY)) {
            y = newY;
        }
    }

    @Override
    public void attack(PlayerCharacter player) {
        if (Math.abs(player.getX() - x) <= range && Math.abs(player.getY() - y) <= range) {
            int attackDamage = damage;
            if (random.nextInt(100) < 20) { // 20% chance for chilling touch
                attackDamage *= 2;
                GameLogic.getInstance().addGameMessage("Fantasma usa toque escalofriante en " + player.getName() + " (-" + attackDamage + " HP).");
            } else {
                GameLogic.getInstance().addGameMessage("Fantasma ataca a " + player.getName() + " (-" + attackDamage + " HP).");
            }
            player.setHealth(player.getHealth() - attackDamage);
        }
    }

    private boolean isOccupiedByEntity(int x, int y) {
        GameLogic logic = GameLogic.getInstance();
        for (Object obj : logic.getPlayers()) {
            PlayerCharacter p = (PlayerCharacter) obj;
            if (p.getX() == x && p.getY() == y && p.getHealth() > 0) return true;
        }
        for (Enemy e : logic.getEnemies()) {
            if (e.getX() == x && e.getY() == y && e != this) return true;
        }
        return false;
    }
}
