package businesslogic;
public class FriendlyZombie extends AbstractPlayer {
    public FriendlyZombie(String name, int x, int y) {
        super(name, "Zombie Amistoso", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 150;
        damage = 15;
        maxMoves = 2;
        range = 1;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        health = Math.min(maxHealth, health + 50);
        GameLogic.getInstance().addGameMessage(name + " se regenera (+50 HP).");
        hasSpecialUsed = true;
        GameLogic.getInstance().notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
