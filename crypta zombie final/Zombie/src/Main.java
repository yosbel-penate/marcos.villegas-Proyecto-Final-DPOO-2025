import businesslogic.GameLogic;
import javax.swing.SwingUtilities;
import presentación.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Inicializando lógica del juego...");
                GameLogic.getInstance(); // Ensure GameLogic singleton is initialized
                System.out.println("Creando instancia de GameFrame...");
                GameFrame gameFrame = new GameFrame();
                gameFrame.setVisible(true);
                System.out.println("GameFrame visible.");
            } catch (Exception e) {
                System.err.println("Error al crear GameFrame: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}﻿
