
package presentation.view;

import java.awt.Font;
import javax.swing.UIManager;

public class LoginMain {


    public static void main(String[] args) {
        com.formdev.flatlaf.fonts.roboto.FlatRobotoFont.install();
        com.formdev.flatlaf.FlatLaf.registerCustomDefaultsSource("raven.themes"); // Si tienes un archivo de temas
        UIManager.put("defaultFont", new Font(com.formdev.flatlaf.fonts.roboto.FlatRobotoFont.FAMILY, Font.PLAIN, 13));

        try {
            // Puedes usar el tema que prefieras
            com.formdev.flatlaf.themes.FlatMacLightLaf.setup();
            // O UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
       java.awt.EventQueue.invokeLater(() -> {
            // 1. Crear la instancia de la Ventana Principal (Main)
            Main mainInstance = new Main();
            
            // Opcional: Asignar a la variable estática 'main' en la clase Main
            // para compatibilidad con otros módulos.
            Main.main = mainInstance; 
            
            // 2. Crear la instancia de Login, pasándole la referencia de Main
            Login LoginFrame = new Login(mainInstance); // 👈 ¡CLAVE: Constructor con argumento!
            
            // 3. Mostrar el Login
            LoginFrame.setVisible(true);
            LoginFrame.pack();
            LoginFrame.setLocationRelativeTo(null);
            
            // 4. Asegurarse de que el MainFrame NO es visible
            // La visibilidad se maneja en Login.jButton1ActionPerformed()
        });
    }
    
}
