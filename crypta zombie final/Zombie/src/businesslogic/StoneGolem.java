package businesslogic;
public class StoneGolem extends AbstractPlayer {
    public StoneGolem(String name, int x, int y) {
        super(name, "Gólem de Piedra", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 200;
        damage = 40;
        maxMoves = 1;
        range = 1;
    }

    @Override
    public void attack(Enemy enemy) {
        GameLogic logic = GameLogic.getInstance();
        enemy.setHealth(enemy.getHealth() - damage);
        logic.addGameMessage(name + " ataca a enemigo (-" + damage + " HP).");
        health = Math.min(maxHealth, health + 20);
        logic.addGameMessage(name + " se regenera (+20 HP).");
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        damage += 10;
        GameLogic.getInstance().addGameMessage(name + " aumenta su daño (+10).");
        hasSpecialUsed = true;
        GameLogic.getInstance().notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
