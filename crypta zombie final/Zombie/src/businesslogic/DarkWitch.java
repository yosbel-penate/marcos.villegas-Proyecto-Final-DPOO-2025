package businesslogic;
import java.util.ArrayList;

public class DarkWitch extends AbstractPlayer {
    public DarkWitch(String name, int x, int y) {
        super(name, "Bruja Oscura", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 80;
        damage = 25;
        maxMoves = 3;
        range = 4;
    }

    @Override
    public void attack(Enemy enemy) {
        GameLogic logic = GameLogic.getInstance();
        enemy.setHealth(enemy.getHealth() - damage);
        logic.addGameMessage(name + " ataca a enemigo (-" + damage + " HP).");
        for (Enemy e : new ArrayList<>(logic.getEnemies())) {
            if (e != enemy && Math.abs(e.getX() - enemy.getX()) <= 1 && Math.abs(e.getY() - enemy.getY()) <= 1) {
                e.setHealth(e.getHealth() - (damage / 2));
                logic.addGameMessage(name + " daña a enemigo cercano (-" + (damage / 2) + " HP).");
            }
        }
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        for (Enemy e : logic.getEnemies()) {
            if (Math.abs(e.getX() - x) <= range && Math.abs(e.getY() - y) <= range) {
                e.setHealth(e.getHealth() - 20);
                logic.addGameMessage(name + " maldice a enemigo (-20 HP).");
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
