package org.gingerjake.dddtest;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void tick();

    void render(Window window);

}
