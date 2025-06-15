package businesslogic;
import java.util.Random;

public abstract class AbstractPlayer implements PlayerCharacter {
    protected String name;
    protected String classType;
    protected int health;
    protected int maxHealth;
    protected int x, y;
    protected int damage;
    protected int maxMoves;
    protected int range;
    protected boolean hasSpecialUsed;
    protected boolean hasSecondSpecialUsed;
    protected boolean isInvisible;
    protected Random random = new Random();

    public AbstractPlayer(String name, String classType, int x, int y) {
        this.name = name;
        this.classType = classType;
        this.x = x;
        this.y = y;
        this.hasSpecialUsed = false;
        this.hasSecondSpecialUsed = false;
        this.isInvisible = false;
        initializeStats();
    }

    protected abstract void initializeStats();

    @Override
    public void attack(Enemy enemy) {
        GameLogic logic = GameLogic.getInstance();
        boolean isMagical = isMagicalClass();
        if (enemy instanceof Fantasma && !isMagical) {
            logic.addGameMessage(name + " no puede atacar a un Fantasma con un ataque no mágico.");
            return;
        }
        if (enemy instanceof EsqueletoArmado && Math.abs(enemy.getX() - x) <= 1 && Math.abs(enemy.getY() - y) <= 1) {
            enemy.setHealth(enemy.getHealth() - damage * 2);
            logic.addGameMessage(name + " ataca a Esqueleto Armado (-" + (damage * 2) + " HP).");
        } else {
            enemy.setHealth(enemy.getHealth() - damage);
            logic.addGameMessage(name + " ataca a enemigo (-" + damage + " HP).");
        }
    }

    protected boolean isMagicalClass() {
        return classType.equals("Nigromante") || classType.equals("Bruja Oscura") ||
               classType.equals("Sacerdote") || classType.equals("Hechicero del Viento") ||
               classType.equals("Druida") || classType.equals("Arcanista");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getClassType() {
        return classType;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public int getMaxMoves() {
        return maxMoves;
    }

    @Override
    public int getRange() {
        return range;
    }

    @Override
    public boolean hasSpecialUsed() {
        return hasSpecialUsed;
    }

    @Override
    public void setHasSpecialUsed(boolean hasSpecialUsed) {
        this.hasSpecialUsed = hasSpecialUsed;
    }

    @Override
    public boolean hasSecondSpecialUsed() {
        return hasSecondSpecialUsed;
    }

    @Override
    public void setHasSecondSpecialUsed(boolean hasSecondSpecialUsed) {
        this.hasSecondSpecialUsed = hasSecondSpecialUsed;
    }

    @Override
    public boolean isInvisible() {
        return isInvisible;
    }

    @Override
    public void setInvisible(boolean invisible) {
        this.isInvisible = invisible;
    }
}
