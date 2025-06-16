package businesslogic;
public class Explorer extends AbstractPlayer {
    public Explorer(String name, int x, int y) {
        super(name, "Explorador", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 100;
        damage = 20;
        maxMoves = 5;
        range = 2;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        for (Trap t : logic.getTraps()) {
            if (Math.abs(t.getX() - x) <= 3 && Math.abs(t.getY() - y) <= 3 && t.isActive()) {
                logic.addGameMessage(name + " detecta una trampa en (" + t.getX() + "," + t.getY() + ").");
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
