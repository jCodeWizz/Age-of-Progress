package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;

public class ExtrasMenuLayer extends Layer {

    @Override
    public void open(Stage stage) {
        Image image = new Image(Assets.getSprite("main-menu"));
        image.setPosition(0, 0);
        image.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.add(image).width(Gdx.graphics.getHeight()).height(Gdx.graphics.getHeight()).left().expandY();

        Table right = new Table();
        table.add(right).expand();

        Table back = new Table();
        back.setFillParent(true);
        stage.addActor(back);
        back.right().bottom();

        TextButton backButton = UITextButton.create("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.renderer.changeLayer(new MainMenuLayer());
            }
        });
        back.add(backButton).right().bottom().pad(0, 0, 10, 10).size(140, 60);


    }

    @Override
    public void update(float d) {

    }

    @Override
    public void close(Stage stage) {

    }
}
