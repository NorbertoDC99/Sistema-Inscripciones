
package com.mycompany.sistemainscripcion;

/**
 *
 * @author NorbyDc
 */
public class Curso {
    private String nombre;
    private double costo;

    public Curso(String nombre, double costo) {
        this.nombre = nombre;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCosto() {
        return costo;
    }

    // El método toString es lo que se mostrará visualmente en las listas de la interfaz
    @Override
    public String toString() {
        return nombre + " - $" + String.format("%.2f", costo);
    }
}
