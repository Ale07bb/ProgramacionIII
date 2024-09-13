package tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AplicacionImagen extends JFrame {
    private Imagen imagen;
    private PanelImagen panelImagen;
    private Controlador controlador;
    private JSlider sliderRango;
    private JButton btnColor;

    public AplicacionImagen() {
        setTitle("Aplicación de Relleno por Inundación");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        imagen = new Imagen(20, 20);
        panelImagen = new PanelImagen(imagen);
        controlador = new Controlador(imagen, panelImagen);

        panelImagen.addMouseListener(controlador);
        add(panelImagen, BorderLayout.CENTER);

        // Menú
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArchivo = new JMenu("Archivo");

        JMenuItem abrirItem = new JMenuItem("Abrir");
        abrirItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = fileChooser.getSelectedFile().getPath();
                    imagen.cargarImagen(path, panelImagen.getWidth(),panelImagen.getHeight());
                }
            }
        });
        menuArchivo.add(abrirItem);

        JMenuItem salirItem = new JMenuItem("Salir");
        salirItem.addActionListener(e -> System.exit(0));
        menuArchivo.add(salirItem);

        menuBar.add(menuArchivo);
        setJMenuBar(menuBar);

        // Panel lateral con selector de color y rango
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));

        JLabel lblColor = new JLabel("Selector de Color:");
        panelControl.add(lblColor);

        btnColor = new JButton();
        btnColor.setBackground(Color.RED);
        btnColor.addActionListener(e -> {
            Color nuevoColor = JColorChooser.showDialog(null, "Elige un color", Color.RED);
            if (nuevoColor != null) {
                controlador.setColorActual(nuevoColor);
                btnColor.setBackground(nuevoColor);
            }
        });
        panelControl.add(btnColor);

        JLabel lblRango = new JLabel("Rango:");
        panelControl.add(lblRango);

        sliderRango = new JSlider(0, 255, 50);
        sliderRango.setMajorTickSpacing(50);
        sliderRango.setPaintTicks(true);
        sliderRango.setPaintLabels(true);
        sliderRango.addChangeListener(e -> imagen.setRango(sliderRango.getValue()));
        panelControl.add(sliderRango);

        add(panelControl, BorderLayout.EAST);
    }
}

