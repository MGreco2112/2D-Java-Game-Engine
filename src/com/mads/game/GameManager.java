package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;
import com.mads.engine.gfx.ImageTile;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    private ImageTile image;

    public GameManager() {
        image = new ImageTile("/tile_test.png", 16, 16);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            System.out.println("A was pressed");
        }

        temp += dt * 20;

        if (temp > 3) {
            temp = 0;
        }
    }

    float temp = 0;

    @Override
    public void render(GameContainer gc, Renderer r) {


        r.drawImageTile(
                image,
                gc.getInput().getMouseX() - 32,
                gc.getInput().getMouseY() - 32,
                (int) temp,
                0
        );
    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
