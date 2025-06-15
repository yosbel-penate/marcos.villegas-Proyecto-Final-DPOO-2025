package businesslogic;
public class Alchemist extends AbstractPlayer {
    public Alchemist(String name, int x, int y) {
        super(name, "Alquimista", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 80;
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
        if (logic.getSharedInventory().size() < logic.MAX_INVENTORY_SIZE) {
            Item potion = logic.createItem("Poción de Vida", true, false, 30 + (logic.getCurrentLevel() * 5));
            logic.getSharedInventory().add(potion);
            logic.addGameMessage(name + " crea una Poción de Vida.");
        } else {
            logic.addGameMessage("Inventario lleno. No se puede crear la poción.");
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        if (hasSecondSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad secundaria.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        if (logic.getSharedInventory().size() < logic.MAX_INVENTORY_SIZE) {
            Item potion = logic.createItem("Poción de Daño", true, false, 10 + (logic.getCurrentLevel() * 3));
            logic.getSharedInventory().add(potion);
            logic.addGameMessage(name + " crea.GeoPoción de Daño.");
        } else {
            logic.addGameMessage("Inventario lleno. No se puede crear la poción.");
        }
        hasSecondSpecialUsed = true;
        logic.notifyRepaint();
    }
}