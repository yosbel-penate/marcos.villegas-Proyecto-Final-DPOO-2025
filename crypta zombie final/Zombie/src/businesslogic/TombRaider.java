package businesslogic;
public class TombRaider extends AbstractPlayer {
    public TombRaider(String name, int x, int y) {
        super(name, "Ladrón de Tumbas", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 90;
        damage = 20;
        maxMoves = 4;
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
            if (t.getX() == x && t.getY() == y && t.isActive()) {
                t.setActive(false);
                logic.addGameMessage(name + " desactiva una trampa.");
                break;
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
