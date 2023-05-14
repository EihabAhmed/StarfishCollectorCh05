package com.mygdx.starfishcollectorch05;

public class StarfishGame extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new MenuScreen());
    }
}
