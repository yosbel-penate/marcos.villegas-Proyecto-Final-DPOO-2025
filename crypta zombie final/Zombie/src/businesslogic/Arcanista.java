package businesslogic;
import java.util.ArrayList;

public class Arcanista extends AbstractPlayer {
    public Arcanista(String name, int x, int y) {
        super(name, "Arcanista", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 100;
        damage = 50;
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
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy e : logic.getEnemies()) {
            if (e != null) {
                e.setHealth(e.getHealth() - 50);
                if (e.getHealth() <= 0) {
                    enemiesToRemove.add(e);
                }
            }
        }
        logic.getEnemies().removeAll(enemiesToRemove);
        logic.getTurnOrder().removeAll(enemiesToRemove);
        if (logic.getEnemies().isEmpty()) {
            Item key = logic.createItem("Llave", true, false, 0);
            logic.getKeys().add(key);
            logic.addGameMessage(name + " elimina a " + enemiesToRemove.size() + " enemigos y una llave aparece en el tablero.");
        } else if (!enemiesToRemove.isEmpty()) {
            logic.addGameMessage(name + " daña a todos los enemigos, elimina a " + enemiesToRemove.size() + ".");
        } else {
            logic.addGameMessage(name + " daña a todos los enemigos.");
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
