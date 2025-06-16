package businesslogic;
public class ShadowHunter extends AbstractPlayer {
    public ShadowHunter(String name, int x, int y) {
        super(name, "Cazador de Sombras", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 90;
        damage = 25;
        maxMoves = 4;
        range = 2;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        isInvisible = true;
        damage += 10;
        GameLogic logic = GameLogic.getInstance();
        logic.addGameMessage(name + " se vuelve invisible y aumenta daño (+10).");
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}﻿
