package tp2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;
import javax.imageio.ImageIO;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.Logger;

public class Imagen extends Observable {
    private Color[][] pixeles;
    private Color colorReferencia;
    private int rango;
    private final Logger logger = Logger.getLogger(Imagen.class.getName());

    public Imagen(int width, int height) {
        pixeles = new Color[width][height];
        // Inicialización de los píxeles, por ejemplo, a Color.WHITE
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixeles[x][y] = Color.WHITE;
            }
        }
    }

    public void cargarImagen(String path, int panelWidth, int panelHeight) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));

            // Calcular el factor de escala para ajustar la imagen al panel manteniendo proporciones
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double scaleWidth = (double) panelWidth / originalWidth;
            double scaleHeight = (double) panelHeight / originalHeight;
            double scaleFactor = Math.min(scaleWidth, scaleHeight);  // Mantener proporción

            int newWidth = (int) (originalWidth * scaleFactor);
            int newHeight = (int) (originalHeight * scaleFactor);

            // Crear una nueva imagen escalada
            BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Ajustar la matriz de píxeles al nuevo tamaño
            pixeles = new Color[newWidth][newHeight];
            for (int x = 0; x < newWidth; x++) {
                for (int y = 0; y < newHeight; y++) {
                    int rgb = scaledImage.getRGB(x, y);
                    Color color = new Color(rgb);
                    pixeles[x][y] = color;
                }
            }
            setChanged();
            notifyObservers();
            logger.info("Imagen cargada correctamente desde: " + path);
        } catch (IOException e) {
            logger.severe("Error al cargar la imagen: " + e.getMessage());
        }
    }

    public Color getColor(int x, int y) {
        if (x < 0 || y < 0 || x >= pixeles.length || y >= pixeles[0].length) {
            return null; // Fuera de rango
        }
        return pixeles[x][y];
    }

    public void setColor(int x, int y, Color color) {
        if (x >= 0 && y >= 0 && x < pixeles.length && y < pixeles[0].length) {
            pixeles[x][y] = color;
            logger.info(String.format("Píxel pintado en [%d, %d] con color %s", x, y, color.toString()));
        }
    }

    public void setRango(int rango) {
        this.rango = rango;
        logger.info("Rango establecido a: " + rango);
    }

    public void floodFill(int x, int y, Color color) {
        Color colorActual = getColor(x, y);
        if (colorActual == null || colorActual.equals(color)) {
            return;
        }

        // Inicializar el color de referencia
        if (colorReferencia == null) {
            colorReferencia = colorActual;
        }

        // Inicializar la pila para la implementación iterativa
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int px = p.x;
            int py = p.y;

            if (px < 0 || py < 0 || px >= pixeles.length || py >= pixeles[0].length) {
                continue;
            }

            Color currentColor = getColor(px, py);
            if (currentColor == null || !isInRange(currentColor)) {
                continue;
            }

            setColor(px, py, color);

            // Agregar los puntos vecinos a la pila
            queue.add(new Point(px - 1, py));
            queue.add(new Point(px + 1, py));
            queue.add(new Point(px, py - 1));
            queue.add(new Point(px, py + 1));
        }

        // Notificar cambios después de completar el relleno
        setChanged();
        notifyObservers();
        logger.info("Flood fill completo.");
    }

    private boolean isInRange(Color color) {
        if (color == null || colorReferencia == null) {
            return false;
        }
        return colorDistance(color, colorReferencia) <= rango;
    }

    private double colorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

    public Color[][] getPixeles() {
        return pixeles;
    }
}
