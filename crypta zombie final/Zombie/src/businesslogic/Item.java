package businesslogic;

public class Item {
    private String name;
    private int x, y;
    private boolean isConsumable;
    private boolean isWeapon;
    private int effectValue;

    public Item(String name, int x, int y, boolean isConsumable, boolean isWeapon, int effectValue) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.isConsumable = isConsumable;
        this.isWeapon = isWeapon;
        this.effectValue = effectValue;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isConsumable() {
        return isConsumable;
    }

    public boolean isWeapon() {
        return isWeapon;
    }

    public int getEffectValue() {
        return effectValue;
    }
}
