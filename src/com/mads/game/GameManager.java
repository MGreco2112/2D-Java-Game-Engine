package com.mads.game;

import com.mads.engine.game_handler.AbstractGame;
import com.mads.engine.game_handler.GameContainer;
import com.mads.engine.gfx.Image;
import com.mads.engine.screen.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

    public static final int TS = 16;

    private boolean[] collision;
    private int levelW, levelH;
    private ArrayList<GameObject> objects = new ArrayList<>();


    public GameManager() {
        objects.add(new Player(6, 4));
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
            obj.update(gc, this, dt);

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

                if (collision[x + y * levelW]) {
                    color = 0xff0f0f0f;
                }

                r.drawFillRect(x * TS, y * TS, TS, TS, color);
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
        collision = new boolean[levelW * levelH];

        for (int y = 0; y < levelImage.getH(); y++) {
            for (int x = 0; x < levelImage.getW(); x++) {
                if (levelImage.getP()[x + y * levelImage.getW()] == 0xff000000) {
                    collision[x + y * levelImage.getW()] = true;
                } else {
                    collision[x + y * levelImage.getW()] = false;
                }
            }
        }
    }

    public boolean getCollision(int x, int y) {
        if (x < 0 || x >= levelW || y < 0 || y >= levelH) {
            return true;
        }

        return collision[x + y * levelW];
    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.start();
    }
}
