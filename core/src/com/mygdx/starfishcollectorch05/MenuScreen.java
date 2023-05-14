package com.mygdx.starfishcollectorch05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MenuScreen extends BaseScreen {
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("starfish-collector.png");
        title.centerAtPosition(400, 300);
        title.moveBy(0, 100);

        TextButton startButton = new TextButton("Start", BaseGame.textButtonStyle);
        startButton.setPosition(150, 150);
        uiStage.addActor(startButton);

        startButton.addListener(
                (Event e) -> {
                    if (!(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
        );

        TextButton quitButton = new TextButton("Quit", BaseGame.textButtonStyle);
        quitButton.setPosition(500, 150);
        uiStage.addActor(quitButton);

        quitButton.addListener(
                (Event e) -> {
                    if (!(e instanceof InputEvent) ||
                            !((InputEvent)e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    Gdx.app.exit();
                    return false;
                }
        );
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Keys.S))
            StarfishGame.setActiveScreen(new LevelScreen());
    }

    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Keys.ENTER))
            StarfishGame.setActiveScreen(new LevelScreen());
        if (Gdx.input.isKeyPressed(Keys.ESCAPE))
            Gdx.app.exit();
        return false;
    }
}
