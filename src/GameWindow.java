import controller.CarPlayerController;
import controller.CarPlayerDirection;
import controller.CollisionPool;
import controller.EnemyCarControllerManager;
import model.GameConfig;
import model.GameObject;
import view.ImageDrawer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by MyComputer on 5/11/2016.
 */
public class GameWindow extends Frame implements Runnable {

    Image backgroundImage;
    Thread thread;
    Image backbufferImage;
    CarPlayerController carPlayerController;
    GameConfig gameConfig;

    GameWindow() {
        this.gameConfig = GameConfig.getInst();

        this.setVisible(true);
        this.setSize(GameConfig.DEFAULT_SCREEN_WIDTH, GameConfig.DEFAULT_SCREEN_HEIGHT);
        this.carPlayerController = new CarPlayerController(new GameObject(150, 350, 50, 100), new ImageDrawer("resources/green_car.png"));

        try {
            this.backgroundImage = ImageIO.read(new File("resources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("keyTyped");
            }

            @Override
            public void keyPressed(KeyEvent e) {

                CarPlayerDirection carPlayerDirection = CarPlayerDirection.NONE;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        carPlayerDirection = CarPlayerDirection.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        carPlayerDirection = CarPlayerDirection.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        carPlayerDirection = CarPlayerDirection.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        carPlayerDirection = CarPlayerDirection.RIGHT;
                        break;
                }

                carPlayerController.move(carPlayerDirection);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                CarPlayerDirection carPlayerDirection = CarPlayerDirection.NONE;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                        carPlayerDirection = CarPlayerDirection.STOP_Y;
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                        carPlayerDirection = CarPlayerDirection.STOP_X;
                        break;
                }
                carPlayerController.move(carPlayerDirection);
            }
        });

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void update(Graphics g) {
        if(backbufferImage == null){
            backbufferImage =  new BufferedImage(gameConfig.getScreenWidth(), gameConfig.getScreenHeight(), 1);
        }
        Graphics backbufferGraphics = backbufferImage.getGraphics();
        backbufferGraphics.drawImage(backgroundImage, 0, 0, gameConfig.getScreenWidth(), gameConfig.getScreenHeight(), null);
        carPlayerController.paint(backbufferGraphics);
        EnemyCarControllerManager.getInst().paint(backbufferGraphics);
        g.drawImage(backbufferImage, 0, 0, null);
    }

    @Override
    public void run() {
        while (true) {
            try {
                CollisionPool.getInst().run();
                carPlayerController.run();
                EnemyCarControllerManager.getInst().run();
                repaint();
                Thread.sleep(gameConfig.getThreadDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
