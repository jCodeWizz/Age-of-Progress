package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

public class TileMenu extends Menu {

    public TileMenu(Stage uiStage, Layer layer) {
        super(uiStage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().fill().size(384, 780).left().padLeft(10);
        main.setBackground(new Image(Assets.getSprite("path-menu")).getDrawable());

        //Table closeButtonTable = new Table();
        //main.add(closeButtonTable).expand().fill();

        Label title = UILabel.create("Tile Menu");
        main.add(title).top().left().padLeft(10).padBottom(4);

        Button closeButton = UIIconButton.create("close-icon", UIIconButton.smallStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        main.add(closeButton).top().right().size(33, 36).pad(1);

        main.row();
        Table tabTable = new Table();

        TextButton tab1Button = UITextButton.create("Nature", UITextButton.smallStyle);
        tabTable.add(tab1Button).height(24).padBottom(8).spaceRight(5);

        TextButton tab2Button = UITextButton.create("Tiled", UITextButton.smallStyle);
        tabTable.add(tab2Button).height(24).padBottom(8).spaceRight(5);

        main.add(tabTable).expand().fill().top().width(384).height(32).colspan(2);

        main.row();
        Table scrollTable = new Table();
        main.add(scrollTable).expand().fill().top().width(384).height(378).padTop(5).colspan(2);

        main.row();
        Table showTable = new Table();
        main.add(showTable).expand().fill().bottom().width(384).height(324).colspan(2);

        showTable.add(new Image(Assets.getSprite("grass-tile"))).expand().left();

        //main.setDebug(true);
    }
}
