package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;
import com.mads.engine.audio.SoundClip;
import com.mads.engine.gfx.Image;
import com.mads.engine.gfx.ImageTile;


import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    Image image;
    Image image2;
    private SoundClip clip;

    public GameManager() {
        image  = new Image("/test.png");
        image.setAlpha(true);
        image2 = new Image("/pattern_test2.png");
        image2.setAlpha(true);
        clip = new SoundClip("/audio/test.wav");
//        clip.setVolume(-50f);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            clip.play();
        }

        temp += dt * 20;

        if (temp > 3) {
            temp = 0;
        }
    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (int x = 0; x < image.getW(); x++) {
            for (int y = 0; y < image.getH(); y++) {
                r.setLightMap(x,y,image.getP()[x + y * image.getW()]);
            }
        }

        r.setzDepth(1);
        r.drawImage(image2, gc.getInput().getMouseX(), gc.getInput().getMouseY());

    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.start();
    }
}
