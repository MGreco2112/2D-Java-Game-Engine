package com.mads.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable {

    private Thread thread;
    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractGame game;

    private boolean running = false;
    private final double UPDATE_CAP = 1.0/60.0; //60 updates per second
    private int width = 320, height = 240; //px
    private float scale = 4.0f;
    private String title = "MajEngine v1.0";

    public GameContainer(AbstractGame game) {
        this.game = game;
    }

    public void start() {
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);

        //at container start assign a new thread with runnable target GameContainer passed as 'this'
        thread = new Thread(this);
        thread.run(); //main thread, .start() for side thread
    }

    public void stop() {

    }

    public void run() {
        running = true;

        boolean render = false;
        double firstTime = 0.0;
        double lastTime = System.nanoTime() / 1_000_000_000.0;
        double passedTime = 0.0;
        double unprocessedTime = 0.0;
        
        double frameTime = 0.0;
        int frames = 0, fps = 0;

        while (running) {

            render = false;

            firstTime = System.nanoTime() / 1_000_000_000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime >= UPDATE_CAP) {
                unprocessedTime -= UPDATE_CAP;
                render = true;

                game.update(this, (float) UPDATE_CAP);

                input.update();

                if (frameTime >= 1.0) {

                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }
            }

            if (render) {
                renderer.clear();
                game.render(this, renderer);
                renderer.process();
                renderer.drawText("FPS: " + fps, 0, 0, 0xff00ffff);
                window.update();
                frames++;

            } else {
                try {
                    thread.sleep(1);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        dispose();
    }

    private void dispose() {

    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public double getUPDATE_CAP() {
        return UPDATE_CAP;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }
}
