package businesslogic;
public class AlliedSkeleton extends AbstractPlayer {
    public AlliedSkeleton(String name, int x, int y) {
        super(name, "Esqueleto Aliado", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 50;
        damage = 15;
        maxMoves = 2;
        range = 1;
    }

    @Override
    public void useSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad especial.");
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}
