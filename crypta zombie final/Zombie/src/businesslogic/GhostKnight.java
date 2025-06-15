package businesslogic;
public class GhostKnight extends AbstractPlayer {
    public GhostKnight(String name, int x, int y) {
        super(name, "Caballero Fantasma", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 120;
        damage = 25;
        maxMoves = 2;
        range = 2;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        isInvisible = true;
        logic.addGameMessage(name + " se vuelve invisible por un turno.");
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
