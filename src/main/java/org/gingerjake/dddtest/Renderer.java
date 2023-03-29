package org.gingerjake.dddtest;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public void init() throws Exception {
    }

    public void clear(Window window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
