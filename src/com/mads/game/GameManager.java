package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;
import com.mads.engine.audio.SoundClip;
import com.mads.engine.gfx.Image;
import com.mads.engine.gfx.ImageTile;
import com.mads.engine.gfx.Light;


import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private final Light light;

    private Image image;
    private Image image2;

    private SoundClip clip;

    public GameManager() {
        image  = new Image("/Test2.png");
        image.setAlpha(false);
        image.setLightBlock(Light.FULL);
        image2 = new Image("/pattern_test2.png");
        image2.setAlpha(false);
        clip = new SoundClip("/audio/test.wav");
//        clip.setVolume(-50f);
        light = new Light(100, 0xff00ffff);
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

        r.setzDepth(0);
        r.drawImage(image2, 0, 0);
        r.drawImage(image, 100, 100);

        r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());

    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.start();
    }
}
