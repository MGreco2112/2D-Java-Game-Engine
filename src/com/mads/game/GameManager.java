package com.mads.game;

import com.mads.engine.AbstractGame;
import com.mads.engine.GameContainer;
import com.mads.engine.Renderer;
import com.mads.engine.gfx.Image;

import com.mads.engine.gfx.Light;

public class GameManager extends AbstractGame {

    private Image image;
    private Image image2;
    private Light light;


    public GameManager() {

    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }

    //entry point for game
    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.setWidth(320);
        gc.setHeight(240);
        gc.start();
    }
}
