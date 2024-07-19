package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextField;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class PeopleMenu extends Menu {

    private TextField searchField;
    private InputListener clickListener;
    private InputListener escapeKeyListener;

    public PeopleMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().fill().size(850 * (Layer.scale / 2f), 478 * (Layer.scale / 2f));
        main.setBackground(new Image(Assets.getSprite("settlement-menu")).getDrawable());

        Table top = new Table();
        main.add(top).expandX().fillX().top().height(22 * (Layer.scale / 2f)).padTop(2).colspan(2);
        main.row();

        Label title = UILabel.create("People Menu");
        top.add(title).expandX().left().padLeft(10).padBottom(4);

        Button closeButton = UIIconButton.create("close-icon", UIIconButton.smallStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        top.add(closeButton).expandX().right().size(22 * (Layer.scale / 2f), 24 * (Layer.scale / 2f)).padRight(1);

        Table left = new Table();
        main.add(left).expand().fillY().left().width(Value.percentWidth(0.787f, main));

        Table search = new Table();
        left.add(search).expandX().fillX().top().height(22 * (Layer.scale / 2f)).padTop(2);
        left.row();

        searchField = UITextField.create("Name . . .");
        search.add(searchField).expand().left().fillY().width(Value.percentWidth(0.36f, search)).padLeft(7 * Layer.scale / 2f);

        Table list = new Table();
        left.add(list).expand().fill();

        Table right = new Table();
        main.add(right).expand().fill().right().width(Value.percentWidth(0.213f, main));

        clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();
                if (!(target instanceof TextField)) {
                    stage.setKeyboardFocus(null);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };

        escapeKeyListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    stage.setKeyboardFocus(null);
                    return false;
                }
                return false;
            }
        };

        //main.setDebug(true);
        //left.setDebug(true);
    }

    @Override
    public void onOpen() {
        stage.addListener(clickListener);
        stage.addListener(escapeKeyListener);
    }

    @Override
    public void onClose() {
        stage.removeListener(clickListener);
        stage.removeListener(escapeKeyListener);
        stage.setKeyboardFocus(null);
    }
}