package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;

import java.awt.event.KeyEvent;

public class GameManager extends AbstractGame {

    public GameManager() {

    }

    @Override
    public void update(GameContainer gc, float dt) {
        if (gc.getInput().isKeyDown(KeyEvent.VK_A)) {
            System.out.println("A was pressed");
        }
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {

    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
