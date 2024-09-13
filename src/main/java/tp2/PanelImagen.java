package tp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class PanelImagen extends JPanel implements Observer {
    private Imagen imagen;
    private final Logger logger = Logger.getLogger(PanelImagen.class.getName());
    private Color fillColor = Color.RED; // Color por defecto para el relleno

    public PanelImagen(Imagen imagen) {
        this.imagen = imagen;
        imagen.addObserver(this);
        setPreferredSize(new Dimension(400, 400));


        // //Este metodo se encarga de la interracion del usuario.
        //// Captura el clic y realiza acciones, y luego actualiza la visualización.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int pixelWidth = getWidth() / imagen.getPixeles().length;
                int pixelHeight = getHeight() / imagen.getPixeles()[0].length;

                int x = e.getX() / pixelWidth;
                int y = e.getY() / pixelHeight;

                imagen.floodFill(x, y, fillColor);
                repaint();
            }
        });
    }
// responsable de la representacion visual de la imagen.
// para asegurar que nuestro panel muestre correctamente la imagen actualizada.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int pixelWidth = getWidth() / imagen.getPixeles().length;
        int pixelHeight = getHeight() / imagen.getPixeles()[0].length;

        for (int x = 0; x < imagen.getPixeles().length; x++) {
            for (int y = 0; y < imagen.getPixeles()[0].length; y++) {
                g.setColor(imagen.getColor(x, y));
                g.fillRect(x * pixelWidth, y * pixelHeight, pixelWidth, pixelHeight);

            }
        }
        logger.info("Imagen redibujada.");
    }
   //cualquier cambio de nuestra imagen se reflejara en nuestro panel grafico
    @Override
    public void update(Observable o, Object arg) {
        repaint();

    }
    //controla que color se usara al momento de hacer clic en la imagen
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;

    }
}
