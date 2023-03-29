package org.gingerjake.dddtest;

import org.gingerjake.dddtest.logics.Dummy;

public class Main {
    public static void main(String[] args) throws Exception {
        IGameLogic logic = new Dummy();

        try {
            Game game = new Game("GAME", 600, 480, false, logic);
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}