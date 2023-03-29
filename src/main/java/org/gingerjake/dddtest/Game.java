package org.gingerjake.dddtest;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements Runnable {
    private final IGameLogic gameLogic;
    private final Window window;
    private final Thread mainLoop;
    private boolean isRunning;
    private final int frameCap = 60;

    public Game(String windowTitle, int width, int height, boolean vsSync ,IGameLogic gameLogic) {
        mainLoop = new Thread(this);
        window = new Window(windowTitle, width, height, vsSync);
        this.gameLogic = gameLogic;
        mainLoop.start();
    }

    private void init() throws Exception {
        isRunning = true;
        gameLogic.init();
        window.start();
    }

    private void input() {
        gameLogic.input(window);
    }

    private void update() {
        gameLogic.update();
    }

    private void render() {
        gameLogic.render(window);
//        window.update();
    }

    private void loop() {
        final double updateTime = (double) 1000000000 / frameCap;
        double lastUpdateTime = glfwGetTime();
        double lastRenderTime;

        GL.createCapabilities();

        while(isRunning) {
            double now = System.nanoTime();
            int updateCount = 0;

            while (now - lastUpdateTime > updateTime && updateCount < frameCap) {
                input();
                update();
                render();

                if(glfwWindowShouldClose(window.getWindowHandle())) {
                    System.err.println("Closing application");
                    System.exit(0); //TODO: In the future, actually clean up
                }

                glfwSwapBuffers(window.getWindowHandle());
                glfwPollEvents();

                window.clear();

                lastUpdateTime += updateTime;
                updateCount++;
            }

            if (now - lastUpdateTime > updateTime) {
                lastUpdateTime = now - updateTime;
            }

            //float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / updateTime))
            // TODO: Maybe find out what this is, may help stuttering on 120hz

            lastRenderTime = now;

            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while (now - lastRenderTime < updateTime && now - lastUpdateTime < updateTime) {
                Thread.yield();

                try {
                    //noinspection BusyWait
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                now = System.nanoTime();
            }
        }
        System.out.println("Time to die!");
    }

    @Override
    public void run() {
        try {
            init();
            loop();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
