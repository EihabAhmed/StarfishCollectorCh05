package com.mygdx.starfishcollectorch05;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LevelScreen extends BaseScreen {
    private Turtle turtle;

    private boolean win;

    private Label starfishLabel;

    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water-border.jpg");
        ocean.setSize(1200, 900);

        BaseActor.setWorldBounds(ocean);

        new Starfish(400, 400, mainStage);
        new Starfish(500, 100, mainStage);
        new Starfish(100, 450, mainStage);
        new Starfish(200, 250, mainStage);
        new Starfish(500, 500, mainStage);
        new Starfish(700, 700, mainStage);

        new Rock(200, 150, mainStage);
        new Rock(100, 300, mainStage);
        new Rock(300, 350, mainStage);
        new Rock(450, 200, mainStage);
        new Rock(100, 700, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        win = false;

        starfishLabel = new Label("Starfish Left: ", BaseGame.labelStyle);
        starfishLabel.setColor(Color.CYAN);
        starfishLabel.setPosition(20, 520);
        uiStage.addActor(starfishLabel);

        ButtonStyle buttonStyle = new ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("undo.png"));
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable(buttonRegion);

        Button restartButton = new Button(buttonStyle);
        restartButton.setColor(Color.CYAN);
        restartButton.setPosition(720, 520);
        uiStage.addActor(restartButton);

        restartButton.addListener(
                (Event e) -> {
                    if (!(e instanceof InputEvent) ||
                            !((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    StarfishGame.setActiveScreen(new LevelScreen());
                    return false;
                }
        );
    }

    public void update(float dt) {

        for (BaseActor rockActor : BaseActor.getList(mainStage, "com.mygdx.starfishcollectorch05.Rock"))
            turtle.preventOverlap(rockActor);

        for (BaseActor starfishActor : BaseActor.getList(mainStage, "com.mygdx.starfishcollectorch05.Starfish")) {
            Starfish starfish = (Starfish) starfishActor;
            if (turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                Whirlpool whirl = new Whirlpool(0, 0, mainStage);
                whirl.centerAtActor(starfish);
                whirl.setOpacity(0.25f);
            }
        }

        if (BaseActor.getList(mainStage, "com.mygdx.starfishcollectorch05.Starfish").size() == 0 && !win) {
            win = true;
            turtle.stop();

            BaseActor levelCompletedMessage = new BaseActor(0, 0, uiStage);
            levelCompletedMessage.loadTexture("message-continue.png");
            levelCompletedMessage.centerAtPosition(400, 300);
            levelCompletedMessage.setOpacity(0);
            levelCompletedMessage.addAction(Actions.delay(1));
            levelCompletedMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C) && win)
            StarfishGame.setActiveScreen(new LevelScreen2());

        starfishLabel.setText("Starfish Left: " + BaseActor.getList(mainStage, "com.mygdx.starfishcollectorch05.Starfish").size());
    }
}
