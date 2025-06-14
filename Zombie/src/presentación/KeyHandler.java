package presentación;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import businesslogic.Enemy;
import businesslogic.GameLogic;
import businesslogic.Item;
import businesslogic.PlayerCharacter;

public class KeyHandler extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        GameLogic logic = GameLogic.getInstance();
        if (!logic.isGameRunning() || logic.getTurnOrder().isEmpty()) return;

        Object currentEntity = logic.getTurnOrder().get(logic.getCurrentTurnIndex());
        if (!(currentEntity instanceof PlayerCharacter)) return;

        PlayerCharacter currentPlayer = (PlayerCharacter) currentEntity;
        if (currentPlayer.getHealth() <= 0) return;

        int key = e.getKeyCode();
        int newX = currentPlayer.getX();
        int newY = currentPlayer.getY();

        if (logic.getMovesLeft() > 0) {
            switch (key) {
                case KeyEvent.VK_UP:
                    newY--;
                    break;
                case KeyEvent.VK_DOWN:
                    newY++;
                    break;
                case KeyEvent.VK_LEFT:
                    newX--;
                    break;
                case KeyEvent.VK_RIGHT:
                    newX++;
                    break;
                default:
                    break;
            }

            if (newX >= 0 && newX < GameLogic.BOARD_SIZE && newY >= 0 && newY < GameLogic.BOARD_SIZE &&
                    !logic.isOccupied(newX, newY)) {
                currentPlayer.setX(newX);
                currentPlayer.setY(newY);
                logic.setMovesLeft(logic.getMovesLeft() - 1);
                logic.addGameMessage(currentPlayer.getName() + " se mueve a (" + newX + "," + newY + ").");

                for (Item item : new ArrayList<>(logic.getItems())) {
                    if (item.getX() == newX && item.getY() == newY) {
                        if (logic.getSharedInventory().size() < GameLogic.MAX_INVENTORY_SIZE) {
                            logic.getSharedInventory().add(item);
                            logic.getItems().remove(item);
                            logic.addGameMessage(currentPlayer.getName() + " recoge " + item.getName() + ".");
                        } else {
                            logic.addGameMessage("Inventario lleno. No se puede recoger " + item.getName() + ".");
                        }
                    }
                }

                for (Item keyItem : new ArrayList<>(logic.getKeys())) {
                    if (keyItem.getX() == newX && keyItem.getY() == newY) {
                        if (logic.getSharedInventory().size() < GameLogic.MAX_INVENTORY_SIZE) {
                            logic.getSharedInventory().add(keyItem);
                            logic.getKeys().remove(keyItem);
                            logic.addGameMessage(currentPlayer.getName() + " recoge una Llave.");
                        } else {
                            logic.addGameMessage("Inventario lleno. No se puede recoger la Llave.");
                        }
                    }
                }

                logic.interactWithTrap(currentPlayer, null);
            }
        }

        if (key == KeyEvent.VK_SPACE && !logic.hasAttacked() && logic.canAttack(currentPlayer)) {
            int attackRange = currentPlayer.getClassType().equals("Cazador de Zombies") ? 2 : currentPlayer.getRange();
            Enemy targetEnemy = null;
            for (Enemy enemy : logic.getEnemies()) {
                if (Math.abs(enemy.getX() - currentPlayer.getX()) <= attackRange &&
                        Math.abs(enemy.getY() - currentPlayer.getY()) <= attackRange) {
                    targetEnemy = enemy;
                    break;
                }
            }
            if (targetEnemy != null) {
                currentPlayer.attack(targetEnemy);
                if (targetEnemy.getHealth() <= 0) {
                    logic.getEnemies().remove(targetEnemy);
                    logic.getTurnOrder().remove(targetEnemy);
                    logic.addGameMessage("Enemigo eliminado.");
                    if (logic.getEnemies().isEmpty()) {
                        Item newKey = logic.createItem("Llave", true, false, 0);
                        logic.getKeys().add(newKey);
                        logic.addGameMessage("¡Último enemigo derrotado! Una llave aparece en el tablero.");
                    }
                }
                logic.setHasAttacked(true);
            }
        }

        if (key == KeyEvent.VK_V) {
            currentPlayer.useSpecial();
        }

        if (key == KeyEvent.VK_C) {
            currentPlayer.useSecondSpecial();
        }

        if (key >= KeyEvent.VK_0 && key <= KeyEvent.VK_9) {
            int index = key - KeyEvent.VK_0;
            logic.useSharedItem(index);
        }

        if (key == KeyEvent.VK_ENTER) {
            logic.endTurn();
        }

        logic.notifyRepaint();
    }
}