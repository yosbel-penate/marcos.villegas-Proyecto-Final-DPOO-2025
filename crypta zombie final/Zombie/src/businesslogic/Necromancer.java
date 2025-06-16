package businesslogic;
import java.util.ArrayList;

public class Necromancer extends AbstractPlayer {
    public Necromancer(String name, int x, int y) {
        super(name, "Nigromante", x, y);
    }

    @Override
    protected void initializeStats() {
        health = maxHealth = 70;
        damage = 20;
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
        int skeletonsSummoned = 0;
        int[][] offsets = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] offset : offsets) {
            int newX = x + offset[0];
            int newY = y + offset[1];
            if (newX >= 0 && newX < GameLogic.BOARD_SIZE && newY >= 0 && newY < GameLogic.BOARD_SIZE && !logic.isOccupied(newX, newY)) {
                PlayerCharacter skeleton = new AlliedSkeleton("Esqueleto Aliado " + (skeletonsSummoned + 1), newX, newY);
                logic.getPlayers().add(skeleton);
                logic.getTurnOrder().add(skeleton);
                logic.addGameMessage(name + " invoca un Esqueleto Aliado en (" + newX + "," + newY + ").");
                skeletonsSummoned++;
                if (skeletonsSummoned >= 2) break;
            }
        }
        if (skeletonsSummoned == 0) {
            logic.addGameMessage(name + " no encuentra espacio para invocar esqueletos.");
        } else {
            logic.addGameMessage(name + " invoca " + skeletonsSummoned + " esqueletos aliados.");
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
        Enemy controlledZombie = null;
        for (Enemy e : logic.getEnemies()) {
            if ((e instanceof ZombieComun || e instanceof ZombieCorredor) &&
                    Math.abs(e.getX() - x) <= range && Math.abs(e.getY() - y) <= range) {
                controlledZombie = e;
                break;
            }
        }
        if (controlledZombie != null) {
            Enemy targetEnemy = null;
            double minDistance = Double.MAX_VALUE;
            for (Enemy e : logic.getEnemies()) {
                if (e != controlledZombie) {
                    double distance = Math.sqrt(Math.pow(e.getX() - controlledZombie.getX(), 2) + Math.pow(e.getY() - controlledZombie.getY(), 2));
                    if (distance < minDistance && distance <= controlledZombie.getRange()) {
                        minDistance = distance;
                        targetEnemy = e;
                    }
                }
            }
            if (targetEnemy != null) {
                targetEnemy.setHealth(targetEnemy.getHealth() - controlledZombie.getDamage());
                logic.addGameMessage(name + " controla un zombie para atacar a otro enemigo (-" + controlledZombie.getDamage() + " HP).");
                if (targetEnemy.getHealth() <= 0) {
                    logic.getEnemies().remove(targetEnemy);
                    logic.getTurnOrder().remove(targetEnemy);
                    logic.addGameMessage("El enemigo atacado muere.");
                    if (logic.getEnemies().isEmpty()) {
                        Item key = logic.createItem("Llave", true, false, 0);
                        logic.getKeys().add(key);
                        logic.addGameMessage("¡Último enemigo derrotado! Una llave aparece en el tablero.");
                    }
                }
            } else {
                logic.addGameMessage(name + " no encuentra un enemigo válido para atacar con el zombie controlado.");
            }
        } else {
            logic.addGameMessage(name + " no encuentra un zombie para controlar.");
        }
        hasSecondSpecialUsed = true;
        logic.notifyRepaint();
    }
}
