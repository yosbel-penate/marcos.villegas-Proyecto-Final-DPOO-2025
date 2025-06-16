package businesslogic;
import java.util.ArrayList;

public class FieldMedic extends AbstractPlayer {
    public FieldMedic(String name, int x, int y) {
        super(name, "Médico de Campo", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 80;
        damage = 10;
        maxMoves = 4;
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
            if (obj instanceof PlayerCharacter) {
                PlayerCharacter p = (PlayerCharacter) obj;
                if (p.getHealth() <= 0) {
                    p.setHealth(p.getMaxHealth() / 2);
                    logic.addGameMessage(name + " revive a " + p.getName() + " con mitad de salud.");
                    logic.getTurnOrder().add(p);
                    break;
                }
            }
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
        boolean foundDead = false;
        for (Object obj : new ArrayList<>(logic.getDeadPlayers())) {
            if (!(obj instanceof PlayerCharacter)) continue;
            PlayerCharacter p = (PlayerCharacter) obj;
            foundDead = true;
            int[] offsets = {0, 1, -1, 2, -2, 3, -3};
            int newX = -1, newY = -1;
            boolean positionFound = false;
            for (int dx : offsets) {
                for (int dy : offsets) {
                    int tryX = x + dx;
                    int tryY = y + dy;
                    if (tryX >= 0 && tryX < GameLogic.BOARD_SIZE && tryY >= 0 && tryY < GameLogic.BOARD_SIZE && !logic.isOccupied(tryX, tryY)) {
                        newX = tryX;
                        newY = tryY;
                        positionFound = true;
                        break;
                    }
                }
                if (positionFound) break;
            }
            if (!positionFound) {
                logic.addGameMessage("No hay espacio para revivir a " + p.getName() + ".");
                break;
            }
            p.setHealth(p.getMaxHealth() / 2);
            p.setX(newX);
            p.setY(newY);
            logic.getDeadPlayers().remove(p);
            if (!logic.getPlayers().contains(p)) {
                logic.getPlayers().add(p);
            }
            logic.addGameMessage(name + " revive a " + p.getName() + " con " + p.getHealth() + " HP en (" + newX + "," + newY + ").");
            if (!logic.getTurnOrder().contains(p)) {
                logic.getTurnOrder().add(p);
            }
            break;
        }
        if (!foundDead) {
            logic.addGameMessage(name + " no encuentra jugadores muertos para revivir.");
        }
        hasSecondSpecialUsed = true;
        logic.notifyRepaint();
    }
}﻿
