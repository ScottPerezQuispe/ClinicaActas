package presentation.view;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Font;
import javax.swing.UIManager;
import presentation.drawer.MyDrawerBuilder;
import raven.drawer.Drawer;
import raven.popup.GlassPanePopup;
import raven.tabbed.WindowsTabbed;
import raven.toast.Notifications;
//import raven.drawer.Drawer;
//import raven.drawer.MyDrawerBuilder;
//import raven.login.Login;
//import raven.popup.GlassPanePopup;
//import raven.tabbed.WindowsTabbed;
//import raven.toast.Notifications;

/**
 *
 * @author RAVEN
 */
public class Main extends javax.swing.JFrame {

   public static Main main;
  //  private LoginPanel loginForm;

    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        
        
      

        init();
    }

    private void init() {
        GlassPanePopup.install(this);
        Notifications.getInstance().setJFrame(this);
        MyDrawerBuilder myDrawerBuilder = new MyDrawerBuilder(this);
        Drawer.getInstance().setDrawerBuilder(myDrawerBuilder);
        WindowsTabbed.getInstance().install(this, body);
     //  login();
    }

    public void login() {
       // if (loginForm == null) {
      //      loginForm = new LoginPanel();
      //  }
      WindowsTabbed.getInstance().showTabbed(false);
       // loginForm.applyComponentOrientation(getComponentOrientation());
     //  setContentPane(loginForm);
        revalidate();
        repaint();
    }

    public void showMainForm() {
     WindowsTabbed.getInstance().showTabbed(true);
        WindowsTabbed.getInstance().removeAllTabbed();
        setContentPane(body);
        revalidate();
        repaint();
    }
    public void showPanel(javax.swing.JPanel panel) {
    // 1. Ocultar el sistema de pestañas si está activo
    WindowsTabbed.getInstance().showTabbed(true); 
        raven.drawer.Drawer.getInstance().closeDrawer();
    // 2. Limpiar el contenedor central 'body'
    body.removeAll();
    
    // 3. Establecer el layout (GridBagLayout o BorderLayout son comunes para un solo panel)
    // Usaremos un BorderLayout para que el panel ocupe todo el espacio.
    body.setLayout(new java.awt.BorderLayout());
    
    // 4. Añadir el nuevo panel al centro
    body.add(panel, java.awt.BorderLayout.CENTER);
    
    // 5. Refrescar la ventana para que los cambios se vean
    body.revalidate();
    body.repaint();
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        body = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1193, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(body, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 173, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("raven.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        //FlatMacDarkLaf.setup();
        com.formdev.flatlaf.themes.FlatMacLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
           // main = new Main();
           // main.setVisible(true);
           
           
           // 1. Inicializa la instancia principal (Main), y la guarda en 'main'.
        Main mainInstance = new Main(); 
        
        // Asignar a la variable estática (opcional, pero mantiene compatibilidad)
        main = mainInstance; 
        
        // La ventana principal NO debe ser visible al inicio.
        
        // 2. Crea y MUESTRA el Login, pasándole la referencia de Main.
        new Login(mainInstance).setVisible(true); // <-- ¡Referencia pasada aquí!
        
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    // End of variables declaration//GEN-END:variables
}
