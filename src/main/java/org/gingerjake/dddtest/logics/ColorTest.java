package org.gingerjake.dddtest.logics;

import org.gingerjake.dddtest.IGameLogic;
import org.gingerjake.dddtest.Renderer;
import org.gingerjake.dddtest.Window;

public class ColorTest implements IGameLogic {
    private float r = 0.0f;
    private float g = 0.0f;
    private float b = 0.0f;
    private boolean red = true;
    private boolean green;
    private boolean blue;
    private Renderer renderer;

    @Override
    public void init() {
        renderer = new Renderer();
    }

    @Override
    public void input(Window window) {
    }

    @Override
    public void tick() {
        float speed = 0.01f;
        if(red) {
            r += speed;
            g -= speed;
            b -= speed;
        }

        if(green) {
            r -= speed;
            g += speed;
            b -= speed;
        }

        if(blue) {
            r -= speed;
            g -= speed;
            b += speed;
        }

        if(r >= 1.0f) {
            red = false;
            green = true;
        }

        if(g >= 1.0f) {
            green = false;
            blue = true;
        }

        if(b >= 1.0f) {
            blue = false;
            red = true;
        }

        if(r <= 0.0f) {
            r = 0.0f;
        }

        if(g <= 0.0f) {
            g = 0.0f;
        }

        if(b <= 0.0f) {
            b = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(r, g, b, 1.0f);
        System.out.println("Actual: " + r + " " + g + " " + b);
        renderer.clear(window);
    }
}
