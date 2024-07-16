package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.Flag;

import java.net.MalformedURLException;
import java.net.URL;

public class MainMenuLayer extends Layer {

    @Override
    public void open(Stage stage) {
        Image image = new Image(Assets.getSprite("main-menu"));
        image.setPosition(0, 0);
        image.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton startButton = UITextButton.create("Start");
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new GameLayer());
                Main.inst.openWorld(new World());
                Main.inst.world.setup();

                MouseInput.currentlyDrawingObject = new Flag(0, 0);
            }
        });

        TextButton loadButton = UITextButton.create("Load");
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new LoadMenuLayer());
            }
        });

        TextButton settingButton = UITextButton.create("Settings");
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new SettingsMenuLayer());
            }
        });

        TextButton extrasButton = UITextButton.create("Extras");
        extrasButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new ExtrasMenuLayer());
            }
        });

        TextButton quitButton = UITextButton.create("Quit!");
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.exit();
            }
        });

        table.add(image).width(Gdx.graphics.getHeight()).height(Gdx.graphics.getHeight()).left().expandY();

        Table buttonTable = new Table();
        table.add(buttonTable).expand().fill();

        addButton(startButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(loadButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(settingButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(extrasButton, buttonTable);
        buttonTable.row().padTop(20);
        addButton(quitButton, buttonTable);

        // Separate table for the link button to be positioned at the bottom right corner
        Table linkButtonTable = new Table();
        linkButtonTable.setFillParent(true);
        linkButtonTable.bottom().right();
        stage.addActor(linkButtonTable);
        Button linkButton = UIIconButton.create("discord-icon");
        linkButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    Utils.openWebpage(new URL("https://discord.com/invite/UFEEWqP98w"));
                } catch (MalformedURLException e) {
                    Logger.error("Couldn't open link to Discord: 'https://discord.com/invite/UFEEWqP98w'");
                }
            }
        });

        linkButtonTable.add(linkButton).bottom().right().pad(10).width(22 * scale).height(24 * scale);
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