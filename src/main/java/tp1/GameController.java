package tp1;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class GameController {
    private GameModel model;
    private GameView view;
    private Square selectedSquare;
    private int offsetX,offsetY;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;

        view.getGamePanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e){
                handleMouseRelease(e.getX(), e.getY());
            }
        });

        view.getGamePanel().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDrag(e.getX(),e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    private void handleMousePress(int x, int y) {
        if (model.isGameInProgress()) {
            if (model.getBlueSquare().isInside(x, y)) {
                selectedSquare = model.getBlueSquare();
            } else if (model.getRedSquare().isInside(x, y)) {
                selectedSquare = model.getRedSquare();
            }
            if (selectedSquare != null) {
                offsetX = x - selectedSquare.getX();
                offsetY = y - selectedSquare.getY();
            }
        }
    }

    private void handleMouseDrag(int x, int y) {
        if (selectedSquare != null) {
            selectedSquare.setX(x - offsetX);
            selectedSquare.setY(y - offsetY);
            model.notifyObserversWithChange();
        }
    }

    private void handleMouseRelease(int x, int y) {
        if (selectedSquare != null) {
            if (selectedSquare == model.getBlueSquare() && selectedSquare.isInsideTarget(x, y, 300, 50)) {
                selectedSquare.setX(300);
                selectedSquare.setY(50);
            } else if (selectedSquare == model.getRedSquare() && selectedSquare.isInsideTarget(x, y, 300, 150)) {
                selectedSquare.setX(300);
                selectedSquare.setY(150);
            }
            model.notifyObserversWithChange();
            selectedSquare = null;

            if (checkCompletion()) {
                model.stopGame();
            }

        }
    }

    private boolean checkCompletion() {
        return model.getBlueSquare().getX() == 300 && model.getBlueSquare().getY() == 50 &&
                model.getRedSquare().getX() == 300 && model.getRedSquare().getY() == 150;
    }

}
