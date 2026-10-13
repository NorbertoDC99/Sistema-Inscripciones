
package com.mycompany.sistemainscripcion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInscripcion extends JFrame {
    
    private DefaultListModel<Curso> modeloDisponibles;
    private DefaultListModel<Curso> modeloInscritos;
    private JList<Curso> listaDisponibles;
    private JList<Curso> listaInscritos;
    private JLabel labelTotal;
    private double totalPagar = 0.0;
    
    // Nuevos componentes para los datos del estudiante
    private JTextField txtNombre;
    private JTextField txtMatricula;

    public VentanaInscripcion() {
        setTitle("Sistema de Inscripción de Clases");
        setSize(750, 550); // Hice la ventana un poco más grande
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- PANEL SUPERIOR: DATOS DEL ESTUDIANTE ---
        JPanel panelEstudiante = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelEstudiante.setBorder(BorderFactory.createTitledBorder("Datos del Estudiante"));
        
        panelEstudiante.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        panelEstudiante.add(txtNombre);
        
        panelEstudiante.add(new JLabel("Matrícula:"));
        txtMatricula = new JTextField(10);
        panelEstudiante.add(txtMatricula);
        
        add(panelEstudiante, BorderLayout.NORTH);

        // --- MODELOS DE DATOS ---
        modeloDisponibles = new DefaultListModel<>();
        modeloInscritos = new DefaultListModel<>();

        modeloDisponibles.addElement(new Curso("Matemáticas Avanzadas", 1500.00));
        modeloDisponibles.addElement(new Curso("Programación en Java", 2000.50));
        modeloDisponibles.addElement(new Curso("Bases de Datos", 1800.00));
        modeloDisponibles.addElement(new Curso("Diseño de Interfaces", 1200.00));
        modeloDisponibles.addElement(new Curso("Redes de Computadoras", 1750.00));

        // --- LISTAS VISUALES ---
        listaDisponibles = new JList<>(modeloDisponibles);
        listaInscritos = new JList<>(modeloInscritos);

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Cursos Disponibles"));
        panelIzquierdo.add(new JScrollPane(listaDisponibles), BorderLayout.CENTER);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Cursos Inscritos"));
        panelDerecho.add(new JScrollPane(listaInscritos), BorderLayout.CENTER);
        
        labelTotal = new JLabel("Total a pagar: $0.00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotal.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelDerecho.add(labelTotal, BorderLayout.SOUTH);

        // --- PANEL CENTRAL: BOTONES AGREGAR Y QUITAR ---
        JPanel panelBotones = new JPanel(new GridLayout(2, 1, 0, 10)); // 2 filas, 1 columna, separación de 10px
        JButton btnAgregar = new JButton("Inscribir ->");
        JButton btnQuitar = new JButton("<- Quitar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnQuitar);
        
        JPanel panelCentro = new JPanel(new GridBagLayout()); // GridBagLayout centra los botones
        panelCentro.add(panelBotones);

        // Acción del botón agregar
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Curso cursoSeleccionado = listaDisponibles.getSelectedValue();
                if (cursoSeleccionado != null) {
                    modeloDisponibles.removeElement(cursoSeleccionado);
                    modeloInscritos.addElement(cursoSeleccionado);
                    totalPagar += cursoSeleccionado.getCosto();
                    actualizarTotal();
                } else {
                    JOptionPane.showMessageDialog(VentanaInscripcion.this, 
                            "Por favor, selecciona un curso disponible para inscribir.", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Acción del botón quitar (NUEVO)
        btnQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Curso cursoSeleccionado = listaInscritos.getSelectedValue();
                if (cursoSeleccionado != null) {
                    modeloInscritos.removeElement(cursoSeleccionado);
                    modeloDisponibles.addElement(cursoSeleccionado);
                    totalPagar -= cursoSeleccionado.getCosto();
                    actualizarTotal();
                } else {
                    JOptionPane.showMessageDialog(VentanaInscripcion.this, 
                            "Por favor, selecciona un curso inscrito para quitar.", "Atención", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // --- PANEL INFERIOR: FINALIZAR ---
        JPanel panelInferior = new JPanel();
        JButton btnFinalizar = new JButton("Finalizar Inscripción");
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));
        panelInferior.add(btnFinalizar);

        btnFinalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFichaDePago();
            }
        });

        // --- ENSAMBLAJE DE PANELES ---
        JPanel panelListas = new JPanel(new GridLayout(1, 3, 10, 0));
        panelListas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelListas.add(panelIzquierdo);
        panelListas.add(panelCentro);
        panelListas.add(panelDerecho);

        add(panelListas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void actualizarTotal() {
        // Usamos Math.abs para evitar que por errores de precisión de punto flotante salga un "-0.00"
        if (totalPagar < 0.01) totalPagar = 0.0;
        labelTotal.setText("Total a pagar: $" + String.format("%.2f", totalPagar));
    }

    private void generarFichaDePago() {
        // Obtener los datos de texto (trim() elimina espacios en blanco al inicio y final)
        String nombre = txtNombre.getText().trim();
        String matricula = txtMatricula.getText().trim();

        // Validaciones antes de generar la ficha
        if (nombre.isEmpty() || matricula.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor ingresa el nombre y la matrícula del estudiante.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (modeloInscritos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "No has inscrito ningún curso aún.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Construir la ficha incluyendo nombre y matrícula
        StringBuilder ficha = new StringBuilder();
        ficha.append("----- FICHA DE PAGO -----\n\n");
        ficha.append("Alumno: ").append(nombre).append("\n");
        ficha.append("Matrícula: ").append(matricula).append("\n\n");
        ficha.append("Cursos inscritos:\n");
        
        for (int i = 0; i < modeloInscritos.getSize(); i++) {
            Curso c = modeloInscritos.getElementAt(i);
            ficha.append("- ").append(c.getNombre())
                 .append(" : $").append(String.format("%.2f", c.getCosto())).append("\n");
        }
        
        ficha.append("\n-------------------------\n");
        ficha.append("COSTO TOTAL: $").append(String.format("%.2f", totalPagar));

        JOptionPane.showMessageDialog(this, ficha.toString(), 
                "Inscripción Finalizada", JOptionPane.INFORMATION_MESSAGE);
    }
}