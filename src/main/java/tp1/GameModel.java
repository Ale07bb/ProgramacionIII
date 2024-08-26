package tp1;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;



public class GameModel extends Observable {

    private static final Logger logger = LogManager.getLogger(GameModel.class);
    private long startTime;
    private long elapsedTime;
    private List<Long> scores;
    private Square blueSquare;
    private Square redSquare;
    private boolean gameInProgress;



    public GameModel() {
        logger.info("El modelo del juego ha sido inicializado.");
        scores = new ArrayList<>();
        blueSquare = new Square(50, 50, Color.BLUE);
        redSquare = new Square(50, 150, Color.RED);
        gameInProgress= false;
        resetSquares();

    }

    public void startGame() {
        logger.info("El juego ha comenzado.");
        resetSquares();
        startTime = System.currentTimeMillis();
        gameInProgress = true;
        notifyObserversWithChange();
    }

    public void stopGame() {
        logger.info("El juego ha terminado.");
        elapsedTime = System.currentTimeMillis() - startTime;
        scores.add(elapsedTime);
        scores.sort(Long::compareTo);
        gameInProgress = false;
        notifyObserversWithChange();
    }

    public List<Long> getScores() {
        return scores;
    }

    public Square getBlueSquare() {
        return blueSquare;
    }

    public Square getRedSquare() {
        return redSquare;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void notifyObserversWithChange(){
        setChanged();
        notifyObservers();
    }
    private void resetSquares(){
        blueSquare = new Square(50, 50, Color.BLUE);
        redSquare = new Square(50, 150, Color.RED);
    }
}