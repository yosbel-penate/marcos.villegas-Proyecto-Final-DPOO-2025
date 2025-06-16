package businesslogic;
public class Engineer extends AbstractPlayer {
    public Engineer(String name, int x, int y) {
        super(name, "Ingeniero", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 90;
        damage = 15;
        maxMoves = 3;
        range = 3;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        logic.getTraps().add(new Trap("Laser", x, y));
        logic.addGameMessage(name + " coloca un láser.");
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
