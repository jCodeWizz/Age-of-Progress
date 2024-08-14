package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;

public class SelectMenu extends Menu implements IUpdateDataMenu {

    private GameObject selected;

    private UILabel name;

    public SelectMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().size(150 * Layer.scale, 50 * Layer.scale).bottom().left().padLeft(Gdx.graphics.getHeight() * 0.05f);
        main.setBackground(new Image(Assets.getSprite("select-menu-background")).getDrawable());

        Table top = new Table();
        main.add(top).expand().fillX().top().left().height(Value.percentHeight(0.35f, main));

        main.row();

        Table bottom = new Table();
        main.add(bottom).expand().fillX().bottom().left().height(Value.percentHeight(0.65f, main));

        name = UILabel.create("");
        top.add(name).expand().fill().center().left().padLeft(6 * Layer.scale);
    }

    public void setSelected(GameObject selected) {
        this.selected = selected;
    }

    public GameObject getSelected() {
        return selected;
    }

    @Override
    public void updateData() {
        if (selected != null) {
           name.setText(selected.getName());
        }
    }
}
