package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;
import com.mads.engine.audio.SoundClip;
import com.mads.engine.gfx.Image;
import com.mads.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private Image image;
    private ImageTile image2;
    private SoundClip clip;

    public GameManager() {
        image = new Image("/pattern_test.png");
        image2 = new ImageTile("/pattern_test2.png", 16, 16);
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
        r.setzDepth(Integer.MAX_VALUE);
        r.drawImageTile(image2, gc.getInput().getMouseX(), gc.getInput().getMouseY(), 1, 1);
        r.setzDepth(0);
        r.drawImage(image, 10, 10);
//        r.drawFillRect(-10, 10, 32, 32, 0xffffccff);
    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
