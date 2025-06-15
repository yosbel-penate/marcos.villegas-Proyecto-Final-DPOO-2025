package businesslogic;
public class Berserker extends AbstractPlayer {
    public Berserker(String name, int x, int y) {
        super(name, "Berserker", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 130;
        damage = 35;
        maxMoves = 2;
        range = 1;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        damage += 15;
        health -= 20;
        GameLogic logic = GameLogic.getInstance();
        logic.addGameMessage(name + " entra en frenesí (+15 daño, -20 HP).");
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
