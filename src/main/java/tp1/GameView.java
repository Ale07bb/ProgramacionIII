package tp1;


import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GameView extends JFrame implements Observer {
    private GameModel model;
    private JPanel gamePanel;

    public GameView(GameModel model) {
        this.model = model;
        this.model.addObserver(this);

        setTitle("Juego de Cuadrados");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupMenu();
        setupGamePanel();

        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");

        JMenuItem newGameItem = new JMenuItem("Nuevo");
        newGameItem.addActionListener(e -> model.startGame());

        JMenuItem scoresItem = new JMenuItem("Scores");
        scoresItem.addActionListener(e -> showScores());

        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(scoresItem);
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void setupGamePanel() {
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(model.getBlueSquare().getX(), model.getBlueSquare().getY(), 50, 50);
                g.setColor(Color.RED);
                g.fillRect(model.getRedSquare().getX(), model.getRedSquare().getY(), 50, 50);

                g.setColor(Color.BLACK);
                g.drawRect(300,50,50,50);
                g.drawRect(300,150,50,50);

            }
        };
        gamePanel.setPreferredSize(new Dimension(400, 300));
        add(gamePanel, BorderLayout.CENTER);

    }

    private void showScores() {
        StringBuilder scoreText = new StringBuilder("Scores:\n");
        for (Long score : model.getScores()) {
            scoreText.append(score).append(" ms\n");
        }
        JOptionPane.showMessageDialog(this, scoreText.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        gamePanel.repaint();
    }

    public JPanel getGamePanel(){
        return gamePanel;
    }
}