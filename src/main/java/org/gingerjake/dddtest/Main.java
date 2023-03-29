package org.gingerjake.dddtest;

import org.gingerjake.dddtest.logics.ColorTest;

public class Main {
    public static void main(String[] args) {
        IGameLogic logic = new ColorTest();

        try {
            Game game = new Game("GAME", 800, 800, false, logic);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}