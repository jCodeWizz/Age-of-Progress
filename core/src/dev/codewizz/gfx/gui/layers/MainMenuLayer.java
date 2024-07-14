package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.World;

public class MainMenuLayer extends Layer {

    @Override
    public void open(Stage stage) {
        Image image = new Image(Assets.getSprite("main-menu"));
        image.setPosition(0, 0);
        image.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton startButton = UITextButton.createTextButton("Start");
        startButton.addListener((Event e) -> {
            if(!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
                return false;
            }

            Main.inst.renderer.changeLayer(new GameLayer());
            Main.inst.openWorld(new World());
            Main.inst.world.setup();

            return false;
        });

        TextButton loadButton = UITextButton.createTextButton("Load");
        loadButton.addListener((Event e) -> {
            if(!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
                return false;
            }

            //TODO: load idk

            return false;
        });

        TextButton settingButton = UITextButton.createTextButton("Settings");
        settingButton.addListener((Event e) -> {
            if(!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
                return false;
            }

           Main.inst.renderer.changeLayer(new SettingsMenuLayer());

            return false;
        });

        TextButton quitButton = UITextButton.createTextButton("Quit!");
        quitButton.addListener((Event e) -> {
            if(!(e instanceof InputEvent) || !(((InputEvent) e).getType().equals(InputEvent.Type.touchDown))) {
                return false;
            }

            Main.exit();

            return false;
        });

        table.add(image).width(Gdx.graphics.getHeight()).height(Gdx.graphics.getHeight()).left().expandY(); // Image on the left

        Table buttonTable = new Table();
        table.add(buttonTable).expand().fill();

        addButton(startButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(loadButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(settingButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(quitButton, buttonTable);

    }

    private void addButton(TextButton button, Table buttonTable) {
        buttonTable.add().expandX().fillX().width(Value.percentWidth(0.3f, buttonTable)); // 15% empty space
        buttonTable.add(button).expandX().fillX().width(Value.percentWidth(0.4f, buttonTable)).height(80f); // 70% button space
        buttonTable.add().expandX().fillX().width(Value.percentWidth(0.3f, buttonTable)); // 15% empty space
    }

    @Override
    public void update(float d) {

    }

    @Override
    public void close(Stage stage) {

    }
}