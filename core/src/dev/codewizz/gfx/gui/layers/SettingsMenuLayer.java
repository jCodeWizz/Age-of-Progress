package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.*;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;

public class SettingsMenuLayer extends Layer{

    private UILabel uiScaleText;

    @Override
    public void open(Stage stage) {
        Image image = new Image(Assets.getSprite("main-menu"));
        image.setPosition(0, 0);
        image.setSize(Gdx.graphics.getHeight(), Gdx.graphics.getHeight());

        Table table = new Table();
        table.setFillParent(true); // Table fills the entire stage
        stage.addActor(table);

        table.add(image).width(Gdx.graphics.getHeight()).height(Gdx.graphics.getHeight()).left().expandY();

        Table right = new Table();
        table.add(right).expand().fill();

        uiScaleText = UILabel.create("UI Size: " + Layer.scale);
        right.add(uiScaleText).expandX().left().top();

        right.row();

        Slider uiSize = UISlider.create(1, 4, 0.5f, false);
        right.add(uiSize).expandX().width(200).height(20).left().top();
        uiSize.setValue(Layer.scale);
        right.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeUIScale(uiSize.getValue());
            }
        });

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

    private void changeUIScale(float scale) {
        Layer.scale = scale;
        uiScaleText.setText("UI Scale: " + Layer.scale);

        UIIconButton.reload();
        UIIconToggle.reload();
        UIImageButton.reload();
        UISlider.reload();
        UITextButton.reload();
    }



    @Override
    public void update(float d) {

    }

    @Override
    public void close(Stage stage) {

    }
}
