package businesslogic;
import java.util.Random;

public class ZombieCorredor extends Enemy {
    private Random random = new Random();

    public ZombieCorredor(int x, int y, int health, int damage) {
        super(x, y, health, damage, 1);
    }

    @Override
    public void moveTowardsPlayer(PlayerCharacter player) {
        for (int i = 0; i < 2; i++) {
            int dx = Integer.compare(player.getX(), x);
            int dy = Integer.compare(player.getY(), y);
            int newX = x + dx;
            int newY = y + dy;
            GameLogic logic = GameLogic.getInstance();
            for (Trap t : logic.getTraps()) {
                if (t.getX() == newX && t.getY() == newY && t.isActive() && t.getType().equals("Pitfall")) {
                    health = 0;
                    logic.addGameMessage("Zombie Corredor cae en una trampa y muere.");
                    return;
                }
            }
            if (newX >= 0 && newX < GameLogic.BOARD_SIZE && !logic.isOccupied(newX, y)) {
                x = newX;
            } else if (newY >= 0 && newY < GameLogic.BOARD_SIZE && !logic.isOccupied(x, newY)) {
                y = newY;
            }
        }
    }

    @Override
    public void attack(PlayerCharacter player) {
        if (Math.abs(player.getX() - x) <= range && Math.abs(player.getY() - y) <= range) {
            player.setHealth(player.getHealth() - damage);
            GameLogic.getInstance().addGameMessage("Zombie Corredor ataca a " + player.getName() + " (-" + damage + " HP).");
        }
    }
}
