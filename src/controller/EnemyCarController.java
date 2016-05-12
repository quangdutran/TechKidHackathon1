package controller;

import model.CarEnemy;
import model.GameConfig;
import model.GameObject;
import view.GameDrawer;
import view.ImageDrawer;

import java.awt.*;

/**
 * Created by MyComputer on 5/12/2016.
 */
public class EnemyCarController extends SingleController implements Colliable {
    public final int SPEED = 3;

    public EnemyCarController(CarEnemy gameObject, GameDrawer gameDrawer) {
        super(gameObject, gameDrawer);
        this.gameVector.dy = SPEED;
        CollisionPool.getInst().add(this);
    }

    @Override
    public void run() {
        super.run();
        if (!GameConfig.getInst().isInScreen(this.gameObject) &&
                !GameConfig.getInst().isInStartPosition(this.gameObject)) {
            this.gameObject.setAlive(false);
        }
    }

    @Override
    public void onCollide(Colliable c) {

    }

    //DuTQ: tạo ô tô địch theo loại ô tô và làn
    public static EnemyCarController create(CarType carType, LanePosition lanePosition) {
        CarEnemy carEnemy = null;
        switch (lanePosition) {
            case LANE1:
                carEnemy = new CarEnemy(GameConfig.LANE_1.x,
                                        GameConfig.LANE_2.y,
                                        CarEnemy.WIDTH,
                                        CarEnemy.HEIGHT);
                break;
            case LANE2:
                carEnemy = new CarEnemy(GameConfig.LANE_2.x,
                                        GameConfig.LANE_2.y,
                                        CarEnemy.WIDTH,
                                        CarEnemy.HEIGHT);
                break;
            case LANE3:
                carEnemy = new CarEnemy(GameConfig.LANE_3.x,
                                        GameConfig.LANE_3.y,
                                        CarEnemy.WIDTH,
                                        CarEnemy.HEIGHT);
                break;
            case LANE4:
                carEnemy = new CarEnemy(GameConfig.LANE_4.x,
                                        GameConfig.LANE_4.y,
                                        CarEnemy.WIDTH,
                                        CarEnemy.HEIGHT);
                break;
        }
        ImageDrawer imageDrawer = null;
        switch (carType) {
            case BLUE:
                imageDrawer = new ImageDrawer("resources/blue_car.png");
                break;
            case RED:
                imageDrawer = new ImageDrawer("resources/red_car.png");
                break;
            case GREEN:
                imageDrawer = new ImageDrawer("resources/green_car.png");
                break;
            case ORANGE:
                imageDrawer = new ImageDrawer("resources/orange_car.png");
                break;
        }
        EnemyCarController enemyCarController = new EnemyCarController(carEnemy,imageDrawer);
        return enemyCarController;
    }
}
