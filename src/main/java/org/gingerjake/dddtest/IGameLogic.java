package org.gingerjake.dddtest;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update();

    void render(Window window);

}
