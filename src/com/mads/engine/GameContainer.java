package com.mads.engine;

public class GameContainer implements Runnable {

    private Thread thread;

    private boolean running = false;
    private final double UPDATE_CAP = 1.0/60.0; //60 updates per second

    public GameContainer() {

    }

    public void start() {
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
                //TODO: update game
                
                if (frameTime >= 1.0) {

                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                    System.out.println("FPS: " + fps);
                }
            }

            if (render) {
                //TODO: render game
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

    public static void main(String[] args) {
        GameContainer gc = new GameContainer();
        gc.start();
    }
}
