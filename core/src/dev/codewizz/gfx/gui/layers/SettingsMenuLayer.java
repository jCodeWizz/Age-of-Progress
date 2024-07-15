package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;

public class SettingsMenuLayer extends Layer{

    @Override
    public void open(Stage stage) {
        Image image = new Image(Assets.getSprite("main-menu"));
        image.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight()); // Set size of image to be square

        Table table = new Table();
        table.setFillParent(true); // Table fills the entire stage
        stage.addActor(table);

        table.add(image).width(Gdx.graphics.getHeight()).height(Gdx.graphics.getHeight()).left().expandY(); // Image on the left
        Table buttonTableLeft = new Table();
        table.add(buttonTableLeft).expand().bottom().left();
        Table buttonTableRight = new Table();
        table.add(buttonTableRight).expand().bottom().right();



        TextButton back = UITextButton.create("BackR");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new MainMenuLayer());
            }
        });
        TextButton back2 = UITextButton.create("BackL");
        back2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new MainMenuLayer());
            }
        });
        buttonTableLeft.add(back2).bottom().left().padBottom(10).padLeft(10);
        buttonTableRight.add(back).bottom().right().padBottom(10).padRight(10);
    }



    @Override
    public void update(float d) {

    }

    @Override
    public void close(Stage stage) {

    }
}
