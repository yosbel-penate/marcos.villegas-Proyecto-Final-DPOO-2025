package presentación;
import businesslogic.GameLogic;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    private Clip menuAudio;
    public Map<String, Image> sprites = new HashMap<>();
    private Image weaponSprite;

    public GameFrame() {
        System.out.println("GameLogic.TILE_SIZE: " + GameLogic.TILE_SIZE);
        System.out.println("GameLogic.BOARD_SIZE: " + GameLogic.BOARD_SIZE);
        setTitle("RPG 2D");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE + 400, GameLogic.TILE_SIZE * GameLogic.BOARD_SIZE + 50);
        setLocationRelativeTo(null);
        setResizable(false);
        initMenu();
    }

    private void loadSprites() {
        try {
            sprites.put("world1", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/arbol.png")).getImage());
            sprites.put("world2", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janiertumba.png")).getImage());
            sprites.put("world3", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/paredcatac.png")).getImage());
            sprites.put("world4", new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/paredcas.png")).getImage());
            System.out.println("Se cargaron los sprites: " + sprites.size());
        } catch (Exception e) {
            System.err.println("Error al cargar sprites: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "No se pudieron cargar los sprites. Verifica las rutas de las imágenes.", 
                                         "Error de Recursos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadWeaponSprite() {
        try {
            weaponSprite = new ImageIcon(getClass().getClassLoader().getResource("Recursos/imagenes/janierarma[1].png")).getImage();
            if (weaponSprite == null) {
                throw new IOException("No se pudo cargar el sprite del arma.");
            }
            System.out.println("Sprite del arma cargado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar el sprite del arma: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "No se pudo cargar el sprite del arma. Verifica la ruta de la imagen.", 
                                         "Error de Recursos", JOptionPane.ERROR_MESSAGE);
            weaponSprite = null;
        }
    }

    public void initMenu() {
        loadSprites();
        loadWeaponSprite();

        BackgroundPanel menuPanel = new BackgroundPanel("Recursos/imagenes/menu.png");
        menuPanel.setLayout(new BorderLayout());

        // Elimina la configuración del sprite del arma en el menú
        // menuPanel.setWeaponSprite(weaponSprite);
        // menuPanel.setWeaponPosition(new Point(100, 100));
        // System.out.println("Sprite del arma configurado en posición (100, 100).");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setOpaque(false);

        JButton startButton = new JButton("Iniciar Juego");
        JButton infoButton = new JButton("Personajes y Enemigos");
        JButton exitButton = new JButton("Salir");

        Font buttonFont = new Font("Arial", Font.PLAIN, 12);
        startButton.setFont(buttonFont);
        infoButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);
        Dimension buttonSize = new Dimension(120, 30);
        startButton.setPreferredSize(buttonSize);
        infoButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    System.out.println("Iniciando el juego...");
                    GameLogic gameLogic = GameLogic.getInstance();
                    if (gameLogic == null) {
                        System.err.println("Error: GameLogic.getInstance() devolvió null.");
                        return;
                    }
                    gameLogic.initPlayersAndStartGame(this, sprites, weaponSprite);
                    stopMenuAudio();
                } catch (Exception ex) {
                    System.err.println("Error al iniciar el juego: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        });
        infoButton.addActionListener(e -> showCharacterAndEnemyInfo());
        exitButton.addActionListener(e -> {
            stopMenuAudio();
            System.exit(0);
        });

        buttonPanel.add(startButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(exitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        menuPanel.add(bottomPanel, BorderLayout.SOUTH);

        playMenuAudio("Recursos/audios/menu_cancion.wav");

        getContentPane().removeAll();
        getContentPane().add(menuPanel);
        revalidate();
        repaint();
    }

    private void playMenuAudio(String audioFilePath) {
        try {
            java.net.URL audioURL = getClass().getClassLoader().getResource(audioFilePath);
            if (audioURL == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el archivo de audio: " + audioFilePath, 
                                             "Error de Audio", JOptionPane.ERROR_MESSAGE);
                System.err.println("No se encontró el archivo de audio: " + audioFilePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioURL);
            menuAudio = AudioSystem.getClip();
            menuAudio.open(audioStream);
            try {
                FloatControl volumeControl = (FloatControl) menuAudio.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(-10.0f);
            } catch (IllegalArgumentException e) {
                System.err.println("No se pudo ajustar el volumen: " + e.getMessage());
            }
            menuAudio.loop(Clip.LOOP_CONTINUOUSLY);
            menuAudio.start();
        } catch (UnsupportedAudioFileException e) {
            JOptionPane.showMessageDialog(this, "Formato de audio no soportado: " + e.getMessage(), 
                                         "Error de Audio", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo de audio: " + e.getMessage(), 
                                         "Error de Audio", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(this, "El dispositivo de audio no está disponible: " + e.getMessage(), 
                                         "Error de Audio", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void stopMenuAudio() {
        if (menuAudio != null && menuAudio.isRunning()) {
            menuAudio.stop();
            menuAudio.close();
        }
        menuAudio = null;
    }

    private void showCharacterAndEnemyInfo() {
        StringBuilder info = new StringBuilder();
    
        info.append("=== PERSONAJES ===\n\n");
        info.append("1. Cazador de Zombies\n");
        info.append("  Salud: 100 | Daño: 30 | Rango: 1 | Movimientos: 3\n");
        info.append("  Ataque: Daña a todos los enemigos en un rango de 2 casillas (-30 HP).\n");
        info.append("  Habilidad Especial: Coloca una trampa 'Pitfall' en la posición actual.\n\n");
    
        info.append("2. Médico de Campo\n");
        info.append("  Salud: 80 | Daño: 10 | Rango: 3 | Movimientos: 4\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-10 HP).\n");
        info.append("  Habilidad Especial: Revive a un jugador muerto con mitad de su salud máxima.\n");
        info.append("  Habilidad Secundaria: Revive a un jugador muerto en una casilla cercana con mitad de su salud máxima.\n\n");
    
        info.append("3. Caballero Fantasma\n");
        info.append("  Salud: 120 | Daño: 25 | Rango: 2 | Movimientos: 2\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-25 HP).\n");
        info.append("  Habilidad Especial: Se vuelve invisible por un turno.\n\n");
    
        info.append("4. Ladrón de Tumbas\n");
        info.append("  Salud: 90 | Daño: 20 | Rango: 2 | Movimientos: 4\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Desactiva una trampa en la posición actual.\n\n");
    
        info.append("5. Zombie Amistoso\n");
        info.append("  Salud: 150 | Daño: 15 | Rango: 1 | Movimientos: 2\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-15 HP).\n");
        info.append("  Habilidad Especial: Recupera 50 HP (hasta el máximo).\n\n");
    
        info.append("6. Explorador\n");
        info.append("  Salud: 100 | Daño: 20 | Rango: 2 | Movimientos: 5\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Detecta trampas activas en un rango de 3 casillas.\n\n");
    
        info.append("7. Sacerdote\n");
        info.append("  Salud: 90 | Daño: 15 | Rango: 3 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-15 HP).\n");
        info.append("  Habilidad Especial: Cura 30 HP a todos los jugadores vivos en un rango de 3 casillas.\n\n");
    
        info.append("8. Gólem de Piedra\n");
        info.append("  Salud: 200 | Daño: 40 | Rango: 1 | Movimientos: 1\n");
        info.append("  Ataque: Daña a un enemigo en rango (-40 HP) y recupera 20 HP.\n");
        info.append("  Habilidad Especial: Aumenta su daño en 10.\n\n");
    
        info.append("9. Artillero\n");
        info.append("  Salud: 90 | Daño: 25 | Rango: 5 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-25 HP).\n");
        info.append("  Habilidad Especial: Dispara a un enemigo en rango, causando 50 de daño.\n\n");
    
        info.append("10. Cazador de Sombras\n");
        info.append("  Salud: 90 | Daño: 25 | Rango: 2 | Movimientos: 4\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-25 HP).\n");
        info.append("  Habilidad Especial: Se vuelve invisible y aumenta su daño en 10.\n\n");
    
        info.append("11. Paladín\n");
        info.append("  Salud: 110 | Daño: 25 | Rango: 2 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-25 HP).\n");
        info.append("  Habilidad Especial: Cura 20 HP a todos los jugadores vivos en un rango de 2 casillas.\n\n");
    
        info.append("12. Monje\n");
        info.append("  Salud: 100 | Daño: 20 | Rango: 1 | Movimientos: 4\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Recupera 40 HP (hasta el máximo).\n\n");
    
        info.append("13. Berserker\n");
        info.append("  Salud: 130 | Daño: 35 | Rango: 1 | Movimientos: 2\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-35 HP).\n");
        info.append("  Habilidad Especial: Entra en frenesí, aumentando daño en 15 pero perdiendo 20 HP.\n\n");
    
        info.append("14. Arcanista\n");
        info.append("  Salud: 10000 | Daño: 5000 | Rango: 50 | Movimientos: 30\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-5000 HP).\n");
        info.append("  Habilidad Especial: Daña a todos los enemigos (-50 HP), elimina los que mueren y genera una llave si no quedan enemigos.\n\n");
    
        info.append("15. Esqueleto Aliado\n");
        info.append("  Salud: 50 | Daño: 15 | Rango: 1 | Movimientos: 2\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-15 HP).\n");
        info.append("  Habilidad Especial: Ninguna.\n\n");
    
        info.append("16. Alquimista\n");
        info.append("  Salud: 80 | Daño: 15 | Rango: 3 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-15 HP).\n");
        info.append("  Habilidad Especial: Crea una Poción de Vida (cura 30 + nivel*5 HP) en el inventario.\n");
        info.append("  Habilidad Secundaria: Crea una Poción de Daño (aumenta daño en 10 + nivel*3) en el inventario.\n\n");
    
        info.append("17. Bruja Oscura\n");
        info.append("  Salud: 80 | Daño: 25 | Rango: 4 | Movimientos: 3\n");
        info.append("  Ataque: Daña a un enemigo (-25 HP) y a enemigos adyacentes al objetivo (-12 HP).\n");
        info.append("  Habilidad Especial: Maldice a todos los enemigos en un rango de 4 casillas (-20 HP).\n\n");
    
        info.append("18. Druida\n");
        info.append("  Salud: 100 | Daño: 20 | Rango: 3 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Cura 25 HP a todos los jugadores vivos en un rango de 3 casillas.\n\n");
    
        info.append("19. Nigromante\n");
        info.append("  Salud: 70 | Daño: 20 | Rango: 5 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Invoca hasta 2 Esqueletos Aliados en casillas adyacentes libres.\n");
        info.append("  Habilidad Secundaria: Controla un Zombie (Común o Corredor) para atacar a otro enemigo.\n\n");
    
        info.append("20. Ingeniero\n");
        info.append("  Salud: 90 | Daño: 15 | Rango: 3 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-15 HP).\n");
        info.append("  Habilidad Especial: Coloca una trampa 'Laser' en la posición actual.\n\n");
    
        info.append("21. Hechicero del Viento\n");
        info.append("  Salud: 80 | Daño: 20 | Rango: 4 | Movimientos: 3\n");
        info.append("  Ataque: Ataque estándar a un enemigo en rango (-20 HP).\n");
        info.append("  Habilidad Especial: Empuja a todos los enemigos en un rango de 4 casillas a una nueva posición aleatoria (1 casilla).\n\n");
    
        info.append("=== ENEMIGOS ===\n\n");
        info.append("1. Zombie Común\n");
        info.append("  Salud: 50+(nivel*10) | Daño: 10+(nivel*2) | Rango: 1\n");
        info.append("  Movimiento: Se mueve hacia el jugador (50% de probabilidad) una casilla.\n");
        info.append("  Ataque: Daña al jugador en rango (-daño HP).\n\n");
    
        info.append("2. Zombie Corredor\n");
        info.append("  Salud: 30+(nivel*10) | Daño: 15+(nivel*2) | Rango: 1\n");
        info.append("  Movimiento: Se mueve hacia el jugador hasta 2 casillas por turno.\n");
        info.append("  Ataque: Daña al jugador en rango (-daño HP).\n\n");
    
        info.append("3. Esqueleto Armado\n");
        info.append("  Salud: 40+(nivel*10) | Daño: 15+(nivel*2) | Rango: 3\n");
        info.append("  Movimiento: Se mueve hacia el jugador una casilla.\n");
        info.append("  Ataque: Daña al jugador en rango (-daño HP).\n\n");
    
        info.append("4. Fantasma\n");
        info.append("  Salud: 40+(nivel*10) | Daño: 5+(nivel*2) | Rango: 2\n");
        info.append("  Movimiento: Se mueve hacia el jugador una casilla (puede atravesar obstáculos y solo lo afectan ataques mágicos).\n");
        info.append("  Ataque: Daña al jugador en rango (-daño HP, 20% de probabilidad de duplicar el daño con 'toque escalofriante').\n\n");
    
        info.append("5. Necromante Enemigo\n");
        info.append("  Salud: 50+(nivel*10) (200 en nivel 10, 300 en nivel 15, 500 en nivel 20) | Daño: 10+(nivel*2) (30 en nivel 10, 35 en nivel 15, 50 en nivel 20) | Rango: 5\n");
        info.append("  Movimiento: Se mueve hacia el jugador una casilla.\n");
        info.append("  Ataque: Daña al jugador en rango (-daño HP).\n");
        info.append("  Habilidad Especial: 50% de probabilidad de invocar un Zombie Común o Esqueleto Armado (máx. 10 enemigos en el tablero).\n\n");
    
        JTextArea textArea = new JTextArea(info.toString(), 20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
    
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
    
        JOptionPane.showMessageDialog(this, scrollPane, "Información de Personajes y Enemigos", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private Image weaponSprite;
        private Point weaponPosition;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(getClass().getClassLoader().getResource(imagePath)).getImage();
                if (backgroundImage == null) {
                    throw new IOException("No se pudo cargar la imagen de fondo.");
                }
                System.out.println("Imagen de fondo cargada correctamente.");
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
            }
        }

        public void setWeaponSprite(Image weaponSprite) {
            this.weaponSprite = weaponSprite;
            System.out.println("Sprite del arma asignado: " + (weaponSprite != null ? "válido" : "null"));
        }

        public void setWeaponPosition(Point position) {
            this.weaponPosition = position;
            System.out.println("Posición del arma asignada: " + position);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                System.out.println("Dibujando imagen de fondo.");
            } else {
                System.err.println("No se pudo dibujar la imagen de fondo: backgroundImage es null.");
            }
            if (weaponSprite != null && weaponPosition != null) {
                g.drawImage(weaponSprite, weaponPosition.x, weaponPosition.y, 50, 50, this);
                System.out.println("Dibujando el arma en posición: " + weaponPosition);
            } else {
                System.err.println("No se pudo dibujar el arma: " +
                                   (weaponSprite == null ? "weaponSprite es null" : "weaponPosition es null"));
            }
        }
    }
}