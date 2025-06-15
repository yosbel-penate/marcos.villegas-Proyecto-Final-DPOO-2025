package businesslogic;
public interface PlayerCharacter {
    void attack(Enemy enemy);
    void useSpecial();
    void useSecondSpecial();
    String getName();
    String getClassType();
    int getHealth();
    void setHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
    int getX();
    void setX(int x);
    int getY();
    void setY(int y);
    int getDamage();
    void setDamage(int damage);
    int getMaxMoves();
    int getRange();
    boolean hasSpecialUsed();
    void setHasSpecialUsed(boolean hasSpecialUsed);
    boolean hasSecondSpecialUsed();
    void setHasSecondSpecialUsed(boolean hasSecondSpecialUsed);
    boolean isInvisible();
    void setInvisible(boolean invisible);
}