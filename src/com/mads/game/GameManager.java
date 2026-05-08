package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

    private ArrayList<GameObject> objects = new ArrayList<>();


    public GameManager() {
        objects.add(new PlayerObject(2,2));
    }

    @Override
    public void init(GameContainer gc) {
        gc.getRenderer().setAmbientColor(-1);
    }

    @Override
    public void update(GameContainer gc, float dt) {

        for (int i = 0; i < objects.size(); i++) {

            GameObject obj = objects.get(i);
            obj.update(gc, dt);

            if (obj.isDead()) {
                objects.remove(obj);
                i--;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (GameObject obj : objects) {
            obj.render(gc, r);
        }
    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.start();
    }
}
