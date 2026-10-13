/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemainscripcion;

import javax.swing.SwingUtilities;

/**
 *
 * @author NorbyDc
 */
public class SistemaInscripcion {

    public static void main(String[] args) {
       // Ejecutar la interfaz gráfica en el hilo adecuado (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                VentanaInscripcion ventana = new VentanaInscripcion();
                ventana.setVisible(true);
            }
        });
    }
}
