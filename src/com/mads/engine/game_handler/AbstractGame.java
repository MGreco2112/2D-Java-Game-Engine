package com.mads.engine.game_handler;

import com.mads.engine.screen.Renderer;

public abstract class AbstractGame {

    public abstract void init(GameContainer gc);
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer renderer);

}
