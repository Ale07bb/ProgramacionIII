package tp2;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.logging.Logger;

public class Controlador extends MouseAdapter {
    private Imagen imagen;
    private PanelImagen panelImagen;
    private Color colorActual;
    private final JColorChooser colorChooser;
    private final Logger logger = Logger.getLogger(Controlador.class.getName());

    public Controlador(Imagen imagen, PanelImagen panelImagen) {
        this.imagen = imagen;
        this.panelImagen = panelImagen;
        this.colorActual = Color.BLACK;// Color inicial por defecto
        this.colorChooser = new JColorChooser();

    }

    public void setColorActual(Color color) {
        this.colorActual = color;
        panelImagen.setFillColor(color);

    }

    public JColorChooser getColorChooser() {
        return colorChooser;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int pixelWidth = panelImagen.getWidth() / imagen.getPixeles().length;
        int pixelHeight = panelImagen.getHeight() / imagen.getPixeles()[0].length;

        int x = e.getX() / pixelWidth;
        int y = e.getY() / pixelHeight;


        logger.info("Click en coordenada: " + x + ", " + y);
        imagen.floodFill(x, y, colorActual);
        panelImagen.repaint();
    }
    public void mostrarSelectorColor(){
        Color nuevoColor = JColorChooser.showDialog(null,"Selecciona un color",colorActual);
        if (nuevoColor != null){
            setColorActual(nuevoColor);
        }
    }
}
