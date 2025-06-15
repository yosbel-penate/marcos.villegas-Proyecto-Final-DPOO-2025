package businesslogic;

public class Trap {
    private String type;
    private int x, y;
    private boolean isActive;

    public Trap(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isActive = true;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void checkConflictWithDoor(int doorX, int doorY) throws IllegalArgumentException {
        if (this.x == doorX && this.y == doorY) {
            throw new IllegalArgumentException("Trap cannot be placed on the same position as a door.");
        }
    }

    public void interactWithTrap(PlayerCharacter player) {
        GameLogic gameLogic = GameLogic.getInstance();
        try {
            if (this.x == player.getX() && this.y == player.getY() && this.isActive) {
                switch (this.type) {
                    case "LockedDoor":
                        boolean hasKey = gameLogic.getSharedInventory().stream()
                                .anyMatch(item -> item.getName().equals("Llave"));
                        if (hasKey) {
                            this.setActive(false);
                            gameLogic.getSharedInventory().removeIf(item -> item.getName().equals("Llave"));
                            gameLogic.addGameMessage(player.getName() + " abre la puerta con una llave.");
                            gameLogic.advanceToNextLevel(null);
                        } else {
                            gameLogic.addGameMessage("Necesitas una llave para abrir la puerta.");
                        }
                        break;
                    case "Pitfall":
                        player.setHealth(0);
                        this.setActive(false);
                        gameLogic.addGameMessage(player.getName() + " muere al caer en un hoyo.");
                        gameLogic.getDeadPlayers().add(player);
                        gameLogic.getTurnOrder().remove(player);
                        gameLogic.endTurn(); // Advance turn after player death
                        break;
                    case "Laser":
                        player.setHealth(player.getHealth() - 90);
                        this.setActive(false);
                        gameLogic.addGameMessage(player.getName() + " pierde 90 HP por láser.");
                        if (player.getHealth() <= 0) {
                            gameLogic.getDeadPlayers().add(player);
                            gameLogic.getTurnOrder().remove(player);
                            gameLogic.addGameMessage(player.getName() + " ha muerto.");
                            gameLogic.endTurn(); // Advance turn after player death
                        } else {
                            gameLogic.endTurn(); // Advance turn if player survives
                        }
                        break;
                    default:
                        gameLogic.addGameMessage("Tipo de trampa desconocido.");
                        break;
                }
            }
        } catch (Exception e) {
            gameLogic.addGameMessage("Error al interactuar con la trampa: " + e.getMessage());
        }
    }
}
