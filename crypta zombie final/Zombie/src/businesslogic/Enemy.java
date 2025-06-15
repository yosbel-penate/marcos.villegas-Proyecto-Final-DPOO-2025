package businesslogic;
public abstract class Enemy {
    protected int x, y;
    protected int health;
    protected int damage;
    protected int range;

    public Enemy(int x, int y, int health, int damage, int range) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
        this.range = range;
    }

    public abstract void moveTowardsPlayer(PlayerCharacter player);
    public abstract void attack(PlayerCharacter player);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getRange() {
        return range;
    }
}
