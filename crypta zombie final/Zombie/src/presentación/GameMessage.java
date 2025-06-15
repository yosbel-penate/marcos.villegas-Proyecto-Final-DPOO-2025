package presentación;

public class GameMessage {
    private String text;
    private long creationTime;
    private final long LIFETIME = 5000;

    public GameMessage(String text) {
        this.text = text;
        this.creationTime = System.currentTimeMillis();
    }

    public String getText() {
        return text;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - creationTime > LIFETIME;
    }
}
