package presentación;
import businesslogic.Enemy;
import businesslogic.EsqueletoArmado;
import businesslogic.Fantasma;
import businesslogic.GameLogic;
import businesslogic.Item;
import businesslogic.NecromanteEnemigo;
import businesslogic.PlayerCharacter;
import businesslogic.Trap;
import businesslogic.ZombieComun;
import businesslogic.ZombieCorredor;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private Image doorImage;
    private Image keyImage;
    private Image healthPotionImage;
    private Image damagePotionImage;
    private Image pitfallImage;
    private Image laserImage;
    private Image lockedDoorImage;
    private Image weaponSprite;
    private Clip menuAudioClip;
    private Map<String, Image> obstacleSprites;
    private Map<String, Image> backgroundImages;
    private Map<String, String> worldAudioPaths;
    private Map<String, Image> playerSprites;
    private Map<String, Image> enemySprites;

    public GamePanel(Map<String, Image> obstacleSprites, Image weaponSprite) {
        this.obstacleSprites = obstacleSprites;
        this.weaponSprite = weaponSprite;
        this.backgroundImages = new HashMap<>();
        this.worldAudioPaths = new HashMap<>();
        this.playerSprites = new HashMap<>();
        this.enemySprites = new HashMap<>();
        setFocusable(true);
        addKeyListener(new KeyHandler());
        setPreferredSize(new Dimension(GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE + 800, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE + 200));
        GameLogic.getInstance().setGamePanel(this);

        try {
            backgroundImages.put("Bosques Oscuros", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/fondo1.1.png")).getImage());
            backgroundImages.put("Cementerios", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/fondo1.0.png")).getImage());
            backgroundImages.put("Catacumbas", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/fondo3.png")).getImage());
            backgroundImages.put("Castillos Embrujados", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/fondo4.png")).getImage());
            System.out.println("Se cargaron las imágenes de fondo: " + backgroundImages.size());
        } catch (Exception e) {
            System.err.println("Error al cargar imágenes de fondo: " + e.getMessage());
        }

        worldAudioPaths.put("Bosques Oscuros", "Recursos/audios/cancion_fondo.wav");
        worldAudioPaths.put("Cementerios", "Recursos/audios/cancion_fondo.wav");
        worldAudioPaths.put("Catacumbas", "Recursos/audios/cancion_fondo.wav");
        worldAudioPaths.put("Castillos Embrujados", "Recursos/audios/cancion_fondo.wav");

        try {
            doorImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janierpuerta.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la puerta: " + e.getMessage());
        }

        try {
            keyImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janierllave.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la llave: " + e.getMessage());
        }

        try {
            healthPotionImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janierpocvida.png")).getImage();
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la poción de vida: " + e.getMessage());
        }

        try {
            String damagePotionPath = "Recursos/imagenes/janierposdano.png";
            System.out.println("Intentando cargar la imagen de la poción de daño desde: " + damagePotionPath);
            damagePotionImage = new ImageIcon(getClass().getClassLoader().getResource(damagePotionPath)).getImage();
            if (damagePotionImage == null) {
                System.err.println("La imagen de la poción de daño no se pudo cargar (es nula).");
            } else {
                System.out.println("Imagen de la poción de daño cargada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la poción de daño: " + e.getMessage());
        }

        try {
            pitfallImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/trampa1.png")).getImage();
            if (pitfallImage == null) {
                System.err.println("La imagen de la trampa Pitfall no se pudo cargar (es nula).");
            } else {
                System.out.println("Imagen de la trampa Pitfall cargada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la trampa Pitfall: " + e.getMessage());
        }

        try {
            laserImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/trampa1.png")).getImage();
            if (laserImage == null) {
                System.err.println("La imagen de la trampa Laser no se pudo cargar (es nula).");
            } else {
                System.out.println("Imagen de la trampa Laser cargada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la trampa Laser: " + e.getMessage());
        }

        try {
            lockedDoorImage = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janierpuerta.png")).getImage();
            if (lockedDoorImage == null) {
                System.err.println("La imagen de la trampa LockedDoor no se pudo cargar (es nula).");
            } else {
                System.out.println("Imagen de la trampa LockedDoor cargada correctamente.");
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen de la trampa LockedDoor: " + e.getMessage());
        }

        try {
            playerSprites.put("Cazador de Zombies", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/cazador.png")).getImage());
            playerSprites.put("Médico de Campo", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/medico.png")).getImage());
            playerSprites.put("Nigromante", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/negromante.png")).getImage());
            playerSprites.put("Caballero Fantasma", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/caballeroF.png")).getImage());
            playerSprites.put("Ladrón de Tumbas", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/ladron.png")).getImage());
            playerSprites.put("Bruja Oscura", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/bruja.png")).getImage());
            playerSprites.put("Zombie Amistoso", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/zombiA.png")).getImage());
            playerSprites.put("Explorador", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/explorador.png")).getImage());
            playerSprites.put("Sacerdote", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/sacerdote.png")).getImage());
            playerSprites.put("Gólem de Piedra", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/golem.png")).getImage());
            playerSprites.put("Artillero", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/artillero.png")).getImage());
            playerSprites.put("Alquimista", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/alquimista.png")).getImage());
            playerSprites.put("Berserker", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/berserker.png")).getImage());
            playerSprites.put("Hechicero del Viento", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/hechiceroV.png")).getImage());
            playerSprites.put("Cazador de Sombras", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/mago oscuro.png")).getImage());
            playerSprites.put("Paladín", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/guerrero.png")).getImage());
            playerSprites.put("Druida", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/druida.png")).getImage());
            playerSprites.put("Ingeniero", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/ingeniero.png")).getImage());
            playerSprites.put("Monje", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/monje.png")).getImage());
            playerSprites.put("Arcanista", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/arcanista.png")).getImage());
            playerSprites.put("Esqueleto Aliado", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/esqueleto.png")).getImage());
            System.out.println("Se cargaron los sprites de jugadores: " + playerSprites.size());
        } catch (Exception e) {
            System.err.println("Error al cargar sprites de jugadores: " + e.getMessage());
        }

        try {
            enemySprites.put("ZombieComun", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/zombi.png")).getImage());
            enemySprites.put("ZombieCorredor", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/zombiC.png")).getImage());
            enemySprites.put("EsqueletoArmado", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/esqueleto.png")).getImage());
            enemySprites.put("Fantasma", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/fantasma.png")).getImage());
            enemySprites.put("NecromanteEnemigo", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/negromanteE.png")).getImage());
            System.out.println("Se cargaron los sprites de enemigos: " + enemySprites.size());
        } catch (Exception e) {
            System.err.println("Error al cargar sprites de enemigos: " + e.getMessage());
        }

        try {
            String currentWorld = GameLogic.getInstance().getCurrentWorld();
            String audioPath = worldAudioPaths.getOrDefault(currentWorld, "Recursos/audios/menu.wav");
            System.out.println("Intentando cargar audio para: " + currentWorld + " desde: " + audioPath);
            java.net.URL audioURL = getClass().getClassLoader().getResource(audioPath);
            if (audioURL == null) {
                System.err.println("No se encontró el archivo de audio: " + audioPath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            menuAudioClip = AudioSystem.getClip();
            menuAudioClip.open(audioStream);
            try {
                FloatControl volumeControl = (FloatControl) menuAudioClip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(-10.0f);
            } catch (IllegalArgumentException e) {
                System.err.println("No se pudo ajustar el volumen: " + e.getMessage());
            }
            menuAudioClip.start();
            menuAudioClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Audio cargado y reproduciendo para: " + currentWorld);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el audio del juego: " + e.getMessage());
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (menuAudioClip != null && menuAudioClip.isRunning()) {
            menuAudioClip.stop();
            menuAudioClip.close();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
    }

    private void drawGame(Graphics g) {
        GameLogic logic = GameLogic.getInstance();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        String currentWorld = logic.getCurrentWorld();
        Image backgroundImage = backgroundImages.get(currentWorld);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE, this);
            System.out.println("Dibujando imagen de fondo para: " + currentWorld);
        } else {
            g.setColor(logic.getWorldBackgroundColor());
            g.fillRect(0, 0, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE);
            System.err.println("No se pudo dibujar la imagen de fondo para: " + currentWorld + ", usando color de fondo.");
        }

        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < GameLogic.BOARD_SIZE; i++) {
            for (int j = 0; j < GameLogic.BOARD_SIZE; j++) {
                g.drawRect(i * GameLogic.TILE_SIZE, j * GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, GameLogic.TILE_SIZE);
            }
        }

        String worldKey = getWorldKey(logic.getCurrentWorld());
        Image obstacleSprite = obstacleSprites.get(worldKey);
        for (Point o : logic.getObstacles()) {
            if (obstacleSprite != null) {
                g.drawImage(obstacleSprite, o.x * GameLogic.TILE_SIZE, o.y * GameLogic.TILE_SIZE,
                        GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
            } else {
                g.setColor(logic.getWorldObstacleColor());
                g.fillRect(o.x * GameLogic.TILE_SIZE, o.y * GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, GameLogic.TILE_SIZE);
            }
        }

        for (Item item : logic.getItems()) {
            if (item.getName().equals("Poción de Vida")) {
                if (healthPotionImage != null) {
                    g.drawImage(healthPotionImage, item.getX() * GameLogic.TILE_SIZE, item.getY() * GameLogic.TILE_SIZE,
                            GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                } else {
                    g.setColor(Color.YELLOW);
                    g.fillOval(item.getX() * GameLogic.TILE_SIZE + 10, item.getY() * GameLogic.TILE_SIZE + 10, 10, 10);
                }
            } else if (item.getName().equals("Poción de Daño")) {
                if (damagePotionImage != null) {
                    g.drawImage(damagePotionImage, item.getX() * GameLogic.TILE_SIZE, item.getY() * GameLogic.TILE_SIZE,
                            GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                } else {
                    g.setColor(Color.RED);
                    g.fillOval(item.getX() * GameLogic.TILE_SIZE + 10, item.getY() * GameLogic.TILE_SIZE + 10, 10, 10);
                }
            } else if (item.isWeapon()) {
                if (weaponSprite != null) {
                    g.drawImage(weaponSprite, item.getX() * GameLogic.TILE_SIZE, item.getY() * GameLogic.TILE_SIZE,
                            GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(item.getX() * GameLogic.TILE_SIZE + 10, item.getY() * GameLogic.TILE_SIZE + 10, 10, 10);
                }
            } else {
                g.setColor(Color.YELLOW);
                g.fillOval(item.getX() * GameLogic.TILE_SIZE + 10, item.getY() * GameLogic.TILE_SIZE + 10, 10, 10);
            }
        }

        for (Item key : logic.getKeys()) {
            if (keyImage != null) {
                g.drawImage(keyImage, key.getX() * GameLogic.TILE_SIZE, key.getY() * GameLogic.TILE_SIZE,
                        GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
            } else {
                g.setColor(Color.CYAN);
                g.fillOval(key.getX() * GameLogic.TILE_SIZE + 10, key.getY() * GameLogic.TILE_SIZE + 10, 10, 10);
            }
        }

        for (Trap t : logic.getTraps()) {
            if (t.isActive()) {
                if (t.getType().equals("Pitfall")) {
                    if (pitfallImage != null) {
                        g.drawImage(pitfallImage, t.getX() * GameLogic.TILE_SIZE, t.getY() * GameLogic.TILE_SIZE,
                                GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                    } else {
                        g.setColor(Color.BLACK);
                        g.fillOval(t.getX() * GameLogic.TILE_SIZE + 5, t.getY() * GameLogic.TILE_SIZE + 5, 20, 20);
                    }
                } else if (t.getType().equals("Laser")) {
                    if (laserImage != null) {
                        g.drawImage(laserImage, t.getX() * GameLogic.TILE_SIZE, t.getY() * GameLogic.TILE_SIZE,
                                GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                    } else {
                        g.setColor(Color.RED);
                        g.fillRect(t.getX() * GameLogic.TILE_SIZE + 5, t.getY() * GameLogic.TILE_SIZE + 5, 20, 20);
                    }
                } else if (t.getType().equals("LockedDoor")) {
                    if (lockedDoorImage != null) {
                        g.drawImage(lockedDoorImage, t.getX() * GameLogic.TILE_SIZE, t.getY() * GameLogic.TILE_SIZE,
                                GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                    } else {
                        g.setColor(Color.BLUE);
                        g.fillRect(t.getX() * GameLogic.TILE_SIZE, t.getY() * GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, GameLogic.TILE_SIZE);
                    }
                }
            }
        }

        for (Object obj : logic.getPlayers()) {
            if (obj instanceof PlayerCharacter) {
                PlayerCharacter p = (PlayerCharacter) obj;
                if (p.getHealth() > 0) {
                    Image playerSprite = playerSprites.get(p.getClassType());
                    if (playerSprite != null) {
                        g.drawImage(playerSprite, p.getX() * GameLogic.TILE_SIZE, p.getY() * GameLogic.TILE_SIZE,
                                GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
                    } else {
                        g.setColor(getPlayerColor(p.getClassType()));
                        g.fillOval(p.getX() * GameLogic.TILE_SIZE + 5, p.getY() * GameLogic.TILE_SIZE + 5, 20, 20);
                    }
                    g.setColor(Color.BLACK);
                    g.drawString(p.getName(), p.getX() * GameLogic.TILE_SIZE, p.getY() * GameLogic.TILE_SIZE);
                }
            }
        }

        for (Enemy e : logic.getEnemies()) {
            String enemyType = e.getClass().getSimpleName();
            Image enemySprite = enemySprites.get(enemyType);
            if (enemySprite != null) {
                g.drawImage(enemySprite, e.getX() * GameLogic.TILE_SIZE, e.getY() * GameLogic.TILE_SIZE,
                        GameLogic.TILE_SIZE, GameLogic.TILE_SIZE, this);
            } else {
                g.setColor(getEnemyColor(e));
                g.fillRect(e.getX() * GameLogic.TILE_SIZE + 5, e.getY() * GameLogic.TILE_SIZE + 5, 20, 20);
            }
        }

        int infoX = GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE + 10;
        int infoY = 20;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Nivel: " + logic.getCurrentLevel() + " - " + logic.getCurrentWorld(), infoX, infoY);
        infoY += 20;
        g.drawString("Ronda: " + logic.getCurrentRound(), infoX, infoY);
        infoY += 20;

        if (logic.getTurnOrder().size() > logic.getCurrentTurnIndex()) {
            Object currentEntity = logic.getTurnOrder().get(logic.getCurrentTurnIndex());
            if (currentEntity instanceof PlayerCharacter) {
                PlayerCharacter currentPlayer = (PlayerCharacter) currentEntity;
                g.drawString("Turno: " + currentPlayer.getName() + " (" + currentPlayer.getClassType() + ")", infoX, infoY);
                infoY += 20;
                g.drawString("Movimientos restantes: " + logic.getMovesLeft(), infoX, infoY);
                infoY += 20;
                g.drawString("Vida: " + currentPlayer.getHealth() + "/" + currentPlayer.getMaxHealth(), infoX, infoY);
                infoY += 20;
                g.drawString("Daño: " + currentPlayer.getDamage(), infoX, infoY);
                infoY += 20;
                g.drawString("Rango: " + currentPlayer.getRange(), infoX, infoY);
                infoY += 20;
            } else if (currentEntity instanceof Enemy) {
                g.drawString("Turno: Enemigo", infoX, infoY);
                infoY += 20;
            }
        }

        g.drawString("Inventario Compartido:", infoX, infoY);
        infoY += 20;
        for (int i = 0; i < logic.getSharedInventory().size(); i++) {
            Item item = logic.getSharedInventory().get(i);
            g.drawString(i + ": " + item.getName() + " (" + item.getEffectValue() + ")", infoX + 10, infoY);
            infoY += 20;
        }

        for (GameMessage message : logic.getGameMessages()) {
            g.drawString(message.getText(), infoX, infoY);
            infoY += 20;
        }

        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("Controles:", infoX, infoY);
        infoY += 15;
        g.drawString("Flechas: Mover", infoX + 10, infoY);
        infoY += 15;
        g.drawString("Espacio: Atacar", infoX + 10, infoY);
        infoY += 15;
        g.drawString("V: Habilidad Especial", infoX + 10, infoY);
        infoY += 15;
        g.drawString("C: Habilidad Secundaria", infoX + 10, infoY);
        infoY += 15;
        g.drawString("0-9: Usar Item", infoX + 10, infoY);
        infoY += 15;
        g.drawString("Enter: Terminar Turno", infoX + 10, infoY);
    }

    private String getWorldKey(String world) {
        switch (world) {
            case "Bosques Oscuros":
                return "world1";
            case "Cementerios":
                return "world2";
            case "Catacumbas":
                return "world3";
            case "Castillos Embrujados":
                return "world4";
            default:
                return "world1";
        }
    }

    private Color getPlayerColor(String classType) {
        switch (classType) {
            case "Cazador de Zombies":
                return Color.GREEN;
            case "Médico de Campo":
                return Color.WHITE;
            case "Nigromante":
                return Color.MAGENTA;
            case "Caballero Fantasma":
                return Color.CYAN;
            case "Ladrón de Tumbas":
                return Color.YELLOW;
            case "Bruja Oscura":
                return Color.PINK;
            case "Zombie Amistoso":
                return Color.ORANGE;
            case "Explorador":
                return Color.LIGHT_GRAY;
            case "Sacerdote":
                return Color.BLUE;
            case "Gólem de Piedra":
                return new Color(139, 137, 137);
            case "Artillero":
                return Color.RED;
            case "Alquimista":
                return new Color(255, 215, 0);
            case "Berserker":
                return new Color(178, 34, 34);
            case "Hechicero del Viento":
                return new Color(135, 206, 250);
            case "Cazador de Sombras":
                return new Color(75, 0, 130);
            case "Paladín":
                return new Color(218, 165, 32);
            case "Druida":
                return new Color(34, 139, 34);
            case "Ingeniero":
                return new Color(105, 105, 105);
            case "Monje":
                return new Color(255, 99, 71);
            case "Arcanista":
                return new Color(147, 112, 219);
            case "Esqueleto Aliado":
                return Color.GRAY;
            default:
                return Color.GREEN;
        }
    }

    private Color getEnemyColor(Enemy e) {
        if (e instanceof ZombieComun || e instanceof ZombieCorredor) {
            return new Color(0, 128, 0);
        } else if (e instanceof EsqueletoArmado) {
            return Color.WHITE;
        } else if (e instanceof Fantasma) {
            return Color.BLACK;
        } else if (e instanceof NecromanteEnemigo) {
            return Color.MAGENTA;
        }
        return Color.RED;
    }
}