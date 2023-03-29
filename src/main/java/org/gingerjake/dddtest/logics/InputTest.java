package org.gingerjake.dddtest.logics;

import org.gingerjake.dddtest.IGameLogic;
import org.gingerjake.dddtest.Renderer;
import org.gingerjake.dddtest.Window;

import static org.lwjgl.glfw.GLFW.*;

public class InputTest implements IGameLogic {
    private int direction = 0;
    private float color = 0.0f;
    private Renderer renderer;

    @Override
    public void init() {
        renderer = new Renderer();
    }

    @Override
    public void input(Window window) {
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            direction = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            direction = -1;
        } else {
            direction = 0;
        }
    }

    @Override
    public void tick() {
        color += direction * 0.01;
        if (color > 1) {
            color = 1.0f;
        } else if ( color < 0 ) {
            color = 0.0f;
        }
    }

    @Override
    public void render(Window window) {
        window.setClearColor(color, color, color, 0.0f);
        renderer.clear(window);
    }
}
