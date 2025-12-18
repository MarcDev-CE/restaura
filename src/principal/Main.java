/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;

import vistas.FrmMozo;
import javax.swing.UIManager;

public class Main {

public static void main(String[] args) {
    // Aplicamos el Look and Feel de Windows primero
    try { 
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); 
    } catch(Exception e) {}

    // El sistema arranca SIEMPRE con el Login por seguridad
    vistas.FrmLogin login = new vistas.FrmLogin();
    login.setVisible(true);
}
}