/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.drawer;

import domain.entidades.Usuario;
import javax.swing.JOptionPane;
import presentation.view.FrmActaVer;
import presentation.view.FrmActa_registro;
import presentation.view.FrmBandejaActa;
import presentation.view.FrmProducto;
import presentation.view.FrmReporteAsignacion;
import presentation.view.FrmUsuario;
import presentation.view.Login;
import presentation.view.Main;
import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import raven.swing.AvatarIcon;

/**
 *
 * @author Scott.perez
 */
// Agrega este nuevo método a tu clase MyDrawerBuilder
public class MyDrawerBuilder extends SimpleDrawerBuilder {

    // 1. Declarar la variable para guardar la referencia de Main
    private final Main mainFrame;
    private final Usuario usuarioLogueado;
    // 🎯 NUEVO CAMPO: Almacena el resultado final del encabezado

    // 2. Crear un constructor que reciba la referencia
    public MyDrawerBuilder(Main mainFrame, Usuario usuario) {
        this.mainFrame = mainFrame;
        this.usuarioLogueado = usuario;

    }

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
        // 🎯 LÓGICA CLAVE: Chequear this.usuarioLogueado, que será null en la primera llamada.
        // Esto garantiza que siempre devolvemos un objeto SimpleHeaderData NO NULO.

        String nombre = (this.usuarioLogueado != null) ? this.usuarioLogueado.getNombreCompleto() : "Cargando...";
        String rol = (this.usuarioLogueado != null) ? this.usuarioLogueado.getNombreRol() : "Por favor, espere.";

        return new SimpleHeaderData()
                .setIcon(new AvatarIcon(getClass().getResource("/presentation/image/profile.png"), 60, 60, 999))
                .setTitle("")
                .setDescription("");
    }

    @Override
    public SimpleMenuOption getSimpleMenuOption() {

        String menus[][] = {
            // {"~MAIN~"},
            //{"Dashboard"},
            {"~Mantenimiento~"},
            {"Empleado"},
            {"Equipo"},
            //{"Calendar"},
            {"~Acta~"},
            {"Bandeja"},
            {"Registrar"},
            {"~Reportes~"},
             {"Trazabilidad"},
            //{"Icons", "Feather Icons", "Flag Icons", "Mdi Icons"},
            //{"Special Pages", "Blank page", "Faq", "Invoice", "Profile", "Pricing", "Timeline"},
            {"Logout"}};

        String icons[] = {
            // "dashboard.svg",
            "user.svg",
            "pc.svg",
            "bandejaentrada.svg",
            "process.svg",
             "forms.svg",
            // "chart.svg",
            // "icon.svg",
            // "page.svg",
            "logout.svg"};

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIcons(icons)
                .setBaseIconPath("presentation/drawer/icon")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        // 🎯 FORMA DE ACCEDER DENTRO DE MenuEvent:
                        Usuario usuarioParaFormulario = MyDrawerBuilder.this.usuarioLogueado;

                        if (index == 0) {
                        if (usuarioParaFormulario.getIdRol()==2){
                              JOptionPane.showMessageDialog(mainFrame, // Es mejor usar mainFrame como parent
                            "No cuenta con permiso para este módulo.", // Mensaje claro
                            "Acceso Denegado", // Título de la ventana
                            JOptionPane.WARNING_MESSAGE);
                            }else {
                           FrmUsuario panel = new FrmUsuario();
                            mainFrame.showPanel(panel);
                        }
                         
                        } else if (index == 1) {
                            
                                if (usuarioParaFormulario.getIdRol()==2){
                              JOptionPane.showMessageDialog(mainFrame, // Es mejor usar mainFrame como parent
                            "No cuenta con permiso para este módulo.", // Mensaje claro
                            "Acceso Denegado", // Título de la ventana
                            JOptionPane.WARNING_MESSAGE);
                            }else {
                           FrmProducto panel = new FrmProducto();
                            mainFrame.showPanel(panel);
                        }
                           
                        } else if (index == 2) {
                            FrmBandejaActa panel = new FrmBandejaActa(mainFrame,usuarioParaFormulario);

                            mainFrame.showPanel(panel);
                        } else if (index == 3) {

                            
                            FrmActa_registro panel = new FrmActa_registro(mainFrame, usuarioParaFormulario); 
                            //FrmActaVer panel = new FrmActaVer(usuarioParaFormulario, 1);
                            mainFrame.showPanel(panel);
                        }else if (index == 4) {
                                
                            if (usuarioParaFormulario.getIdRol()==2){
                            
                            JOptionPane.showMessageDialog(mainFrame, // Es mejor usar mainFrame como parent
                            "No cuenta con permiso para este módulo.", // Mensaje claro
                            "Acceso Denegado", // Título de la ventana
                            JOptionPane.WARNING_MESSAGE);
                            }else {
                             FrmReporteAsignacion panel = new FrmReporteAsignacion(usuarioParaFormulario); 
                            //FrmActaVer panel = new FrmActaVer(usuarioParaFormulario, 1);
                            mainFrame.showPanel(panel);
                            }
                           
                            
                      
                        }
                        
                        
                        else if (index == 5) {

                            Main mainInstance = new Main();

                            // Opcional: Asignar a la variable estática 'main' en la clase Main
                            // para compatibilidad con otros módulos.
                            Main.main = mainInstance;

                            Login LoginFrame = new Login(mainInstance);
                            LoginFrame.setVisible(true);
                            LoginFrame.pack();
                            LoginFrame.setLocationRelativeTo(null);
                            MyDrawerBuilder.this.mainFrame.dispose();
                        }
                        System.out.println("Menu selected " + index + " " + subIndex + " " + usuarioParaFormulario.getNombreCompleto());
                    }
                })
                .setMenuValidation(new MenuValidation() {
                    @Override
                    public boolean menuValidation(int index, int subIndex) {
//                        if(index==0){
//                            return false;
//                        }else if(index==3){
//                            return false;
//                        }
                        return true;
                    }

                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("Java Swing Drawer")
                .setDescription("Version 1.1.0");
    }

    @Override
    public int getDrawerWidth() {
        return 275;
    }
}
