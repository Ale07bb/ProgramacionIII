package tp1;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    //private static Logger logger = LogManager.getRootLogger();
    public static void main(String[] args) {
        //logger.info("Hello world");
        //System.out.println("Hi");
        GameModel model = new GameModel();
        GameView view = new GameView(model);
        new GameController(model, view);
    }
}