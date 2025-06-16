package businesslogic;
public class Priest extends AbstractPlayer {
    public Priest(String name, int x, int y) {
        super(name, "Sacerdote", x, y);
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
        for (Object obj : logic.getPlayers()) {
            PlayerCharacter p = (PlayerCharacter) obj;
            if (Math.abs(p.getX() - x) <= range && Math.abs(p.getY() - y) <= range && p.getHealth() > 0) {
                p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 30));
                logic.addGameMessage(name + " cura a " + p.getName() + " (+30 HP).");
            }
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}﻿
