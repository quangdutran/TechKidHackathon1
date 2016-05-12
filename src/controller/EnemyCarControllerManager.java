package controller;

import model.GameObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by MyComputer on 5/12/2016.
 */
public class EnemyCarControllerManager extends ControllerManager implements Colliable{

    private static EnemyCarControllerManager inst;
    public static EnemyCarControllerManager getInst() {
        if(inst == null) {
            inst = new EnemyCarControllerManager();
        }
        return inst;
    }

    @Override
    public void run() {
        super.run();

        //DuTQ: tạo 3 ô tô địch ở 3 lane random, thừa một lane để tránh
        if (this.singleControllerVector.size() < 3) {
            List<LanePosition> shuffledLanes = Arrays.asList(LanePosition.values());
            Collections.shuffle(shuffledLanes);
            singleControllerVector.add(
                    EnemyCarController.create(CarType.GREEN,shuffledLanes.get(0))
            );
            singleControllerVector.add(
                    EnemyCarController.create(CarType.ORANGE,shuffledLanes.get(1))
            );
            singleControllerVector.add(
                    EnemyCarController.create(CarType.RED,shuffledLanes.get(2))
            );
        }
    }

    @Override
    public void onCollide(Colliable c) {
        if (c instanceof CarPlayerController) {
            this.getGameObject().setAlive(false);
        }
    }

    @Override
    public GameObject getGameObject() {
        return null;
    }
}
