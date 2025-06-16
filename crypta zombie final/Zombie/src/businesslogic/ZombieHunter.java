package businesslogic;
import java.util.ArrayList;

public class ZombieHunter extends AbstractPlayer {
    public ZombieHunter(String name, int x, int y) {
        super(name, "Cazador de Zombies", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 100;
        damage = 30;
        maxMoves = 3;
        range = 4;
    }

    @Override
    public void attack(Enemy enemy) {
        GameLogic logic = GameLogic.getInstance();
        for (Enemy e : new ArrayList<>(logic.getEnemies())) {
            if (Math.abs(e.getX() - x) <= 2 && Math.abs(e.getY() - y) <= 2) {
                e.setHealth(e.getHealth() - damage);
                logic.addGameMessage(name + " ataca a enemigo (-" + damage + " HP).");
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
        boolean trapExists = false;
        for (Trap t : logic.getTraps()) {
            if (t.getX() == x && t.getY() == y && t.isActive()) {
                trapExists = true;
                break;
            }
        }
        if (!trapExists) {
            logic.getTraps().add(new Trap("Pitfall", x, y));
            logic.addGameMessage(name + " coloca una trampa.");
        } else {
            logic.addGameMessage(name + " ya hay una trampa aquí.");
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
