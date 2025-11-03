/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Scott.perez
 */
public class CustomModal extends JDialog {

    public CustomModal(JFrame parent, JPanel content, String title) {
        super(parent, title, true); // 🔒 true = modal real

        // Quitamos la barra del sistema
        setUndecorated(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Fondo translúcido
        JPanel overlay = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 120)); // sombra gris translúcida
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };

        // Contenedor del contenido (simula el panel Raven)
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        container.add(content, BorderLayout.CENTER);

        overlay.add(container, new GridBagConstraints());
        setContentPane(overlay);

        // Ajustes de tamaño y posición
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }
}