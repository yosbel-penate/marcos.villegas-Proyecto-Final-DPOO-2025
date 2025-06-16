package businesslogic;
public class Gunner extends AbstractPlayer {
    public Gunner(String name, int x, int y) {
        super(name, "Artillero", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 90;
        damage = 25;
        maxMoves = 3;
        range = 5;
    }

    @Override
    public void useSpecial() {
        if (hasSpecialUsed) {
            GameLogic.getInstance().addGameMessage(name + " ya usó su habilidad especial.");
            return;
        }
        GameLogic logic = GameLogic.getInstance();
        Enemy target = null;
        for (Enemy e : logic.getEnemies()) {
            if (Math.abs(e.getX() - x) <= range && Math.abs(e.getY() - y) <= range) {
                target = e;
                break;
            }
        }
        if (target != null) {
            target.setHealth(target.getHealth() - 50);
            logic.addGameMessage(name + " dispara un tiro preciso (-50 HP).");
            if (target.getHealth() <= 0) {
                logic.getEnemies().remove(target);
                logic.getTurnOrder().remove(target);
                logic.addGameMessage("Enemigo eliminado.");
                if (logic.getEnemies().isEmpty()) {
                    Item key = logic.createItem("Llave", true, false, 0);
                    logic.getKeys().add(key);
                    logic.addGameMessage("¡Último enemigo eliminado! Una llave aparece en el tablero.");
                }
            }
        } else {
            logic.addGameMessage(name + " no encuentra enemigos en rango.");
        }
        hasSpecialUsed = true;
        logic.notifyRepaint();
    }

    @Override
    public void useSecondSpecial() {
        GameLogic.getInstance().addGameMessage(name + " no tiene habilidad secundaria.");
    }
}﻿
