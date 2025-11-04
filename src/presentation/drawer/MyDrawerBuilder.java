/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.drawer;


import presentation.view.FrmActa_registro;
import presentation.view.FrmProducto;
import presentation.view.FrmUsuario;
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

    // 2. Crear un constructor que reciba la referencia
    public MyDrawerBuilder(Main mainFrame) {
        this.mainFrame = mainFrame;
    }
    
@Override
    public SimpleHeaderData getSimpleHeaderData() {
        return new SimpleHeaderData()
              .setIcon(new AvatarIcon(getClass().getResource("/presentation/image/profile.png"), 60, 60, 999))
                .setTitle("Miguel")
                .setDescription("Soporte");
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
            //{"Bandeja"},
          {"Registrar"},
            //{"~OTHER~"},
           // {"Charts", "Apex", "Flot", "Sparkline"},
            //{"Icons", "Feather Icons", "Flag Icons", "Mdi Icons"},
            //{"Special Pages", "Blank page", "Faq", "Invoice", "Profile", "Pricing", "Timeline"},
            {"Logout"}};

        String icons[] = {
           // "dashboard.svg",
            "user.svg",
            "pc.svg",
            //"calendar.svg",
            "process.svg",
           // "forms.svg",
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
                        if (index == 0){
                            
                            FrmUsuario panel = new FrmUsuario(); 
                            mainFrame.showPanel(panel);
                        }
                        else if (index == 1) {
                            FrmProducto panel = new FrmProducto(); 
                            mainFrame.showPanel(panel);
                        } 
                        else if (index == 2) {
                            FrmActa_registro panel = new FrmActa_registro(); 
                            mainFrame.showPanel(panel);
                        } 
                        else if (index == 9) {
                            Main.main.login();
                        }
                        System.out.println("Menu selected " + index + " " + subIndex);
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
