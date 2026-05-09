package com.mads.game;

import com.mads.engine.game_handler.AbstractGame;
import com.mads.engine.game_handler.GameContainer;
import com.mads.engine.gfx.Image;
import com.mads.engine.screen.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

    private int[] collision;
    private int levelW, levelH;
    private ArrayList<GameObject> objects = new ArrayList<>();


    public GameManager() {
        objects.add(new Player(2, 2));
        loadLevel("/level.png");
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
        for (int y = 0; y < levelH; y++) {
            for (int x = 0; x < levelW; x++) {

                int color = 0xfff9f9f9;

                if (collision[x + y * levelW] == 1) {
                    color = 0xff0f0f0f;
                }

                r.drawFillRect(x * 16, y * 16, 16, 16, color);
            }
        }

        for (GameObject obj : objects) {
            obj.render(gc, r);
        }
    }

    public void loadLevel(String path) {
        Image levelImage = new Image(path);

        levelW = levelImage.getW();
        levelH = levelImage.getH();
        collision = new int[levelW * levelH];

        for (int y = 0; y < levelImage.getH(); y++) {
            for (int x = 0; x < levelImage.getW(); x++) {
                if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000) {
                    collision[x + y * levelImage.getW()] = 1;
                } else {
                    collision[x + y * levelImage.getW()] = 0;
                }
            }
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
