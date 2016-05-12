package controller;

import model.CarPlayer;
import model.GameConfig;
import model.GameObject;
import view.GameDrawer;
import view.ImageDrawer;

import java.awt.*;

/**
 * Created by MyComputer on 5/11/2016.
 */
public class CarPlayerController extends SingleController implements Colliable {
    public final int SPEED = 5;

    public CarPlayerController(GameObject gameObject, GameDrawer gameDrawer) {
        super(gameObject, gameDrawer);
        CollisionPool.getInst().add(this);
    }

    public void move(CarPlayerDirection carPlayerDirection) {
        switch (carPlayerDirection) {
            case NONE:
                break;
            case UP:
                this.gameVector.dy = -SPEED;
                break;
            case DOWN:
                this.gameVector.dy = SPEED;
                break;
            case LEFT:
                this.gameVector.dx = -SPEED;
                break;
            case RIGHT:
                this.gameVector.dx = SPEED;
                break;
            case STOP_X:
                this.gameVector.dx = 0;
                break;
            case STOP_Y:
                this.gameVector.dy = 0;
                break;
        }

    }

    private static CarPlayerController carPlayerController;
    public static CarPlayerController getCarPlayerController() {
        if (carPlayerController == null) {
            CarPlayer carPlayer = new CarPlayer(100, 500, 70, 60);
            ImageDrawer carPlayerDraw = new ImageDrawer("resources/plane4.png");
            carPlayerController = new CarPlayerController(carPlayer, carPlayerDraw);
        }
        return carPlayerController;
    }

    @Override
    public void run() {
        if (this.gameObject.isAlive()) {
            Rectangle rectangle=this.gameObject.getNextRect(this.gameVector);
            if(GameConfig.getInst().isInScreen(rectangle)) {
                super.run();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (this.gameObject.isAlive()) {
            super.paint(g);
        }
    }

    @Override
    public void onCollide(Colliable c) {

    }
}
