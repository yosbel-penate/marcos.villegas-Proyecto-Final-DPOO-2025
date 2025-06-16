package businesslogic;
import java.util.ArrayList;

public class WindSorcerer extends AbstractPlayer {
    public WindSorcerer(String name, int x, int y) {
        super(name, "Hechicero del Viento", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 80;
        damage = 20;
        maxMoves = 3;
        range = 4;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        for (Enemy e : new ArrayList<>(logic.getEnemies())) {
            if (Math.abs(e.getX() - x) <= range && Math.abs(e.getY() - y) <= range) {
                int newX = e.getX() + random.nextInt(3) - 1;
                int newY = e.getY() + random.nextInt(3) - 1;
                if (newX >= 0 && newX < GameLogic.BOARD_SIZE && newY >= 0 && newY < GameLogic.BOARD_SIZE && !logic.isOccupied(newX, newY)) {
                    e.setX(newX);
                    e.setY(newY);
                    logic.addGameMessage(name + " empuja a enemigo a (" + newX + "," + newY + ").");
                }
            }
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
