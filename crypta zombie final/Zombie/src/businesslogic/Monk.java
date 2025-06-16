package businesslogic;
public class Monk extends AbstractPlayer {
    public Monk(String name, int x, int y) {
        super(name, "Monje", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 100;
        damage = 20;
        maxMoves = 4;
        range = 1;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        health = Math.min(maxHealth, health + 40);
        GameLogic logic = GameLogic.getInstance();
        logic.addGameMessage(name + " medita y recupera salud (+40 HP).");
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}﻿
