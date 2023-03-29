package org.gingerjake.dddtest;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.tinylog.Logger;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long windowHandle;
    private final String title;
    private final int width;
    private final int height;
    private final boolean vSync;

    public Window(String windowTitle, int width, int height, boolean vSync) {
        this.title = windowTitle;
        this.width = width;
        this.height = height;
        this.vSync = vSync;
    }

    public void start() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);

        if(getWindowHandle() == NULL) {
            throw new RuntimeException("Failed to create GLFW Window");
        }

        glfwSetErrorCallback((int errorCode, long msgPtr) ->
                Logger.error("Error code [{}], msg [{}]", errorCode, MemoryUtil.memUTF8(msgPtr))
        );

        glfwSetKeyCallback(getWindowHandle(), (window, key, scancode, action, mods) -> keyCallBack(key, action));


        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(getWindowHandle(), pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            assert vidmode != null;
            glfwSetWindowPos(getWindowHandle(), (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(getWindowHandle());

        if (vSync) {
            glfwSwapInterval(1);
        }

        glfwShowWindow(getWindowHandle());
    }

    public void cleanup() {
        glfwFreeCallbacks(getWindowHandle());
        glfwDestroyWindow(getWindowHandle());
        glfwTerminate();
        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public void keyCallBack(int key, int action) {
//        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
//            glfwSetWindowShouldClose(getWindowHandle(), true); // We will detect this in the rendering loop
//        }
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void update() {
        glfwSwapBuffers(getWindowHandle());
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public long getWindowHandle() {
        return windowHandle;
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
    }

    public void setClearColor(float r, float g, float b, float a) {
        glClearColor(r,g,b,a);
    }
}
