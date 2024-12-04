package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;

public class SelectMenu extends Menu implements IUpdateDataMenu {

    private GameObject selected;

    private Table top;
    private Table bottom;

    public SelectMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().size(150 * Layer.scale, 50 * Layer.scale).bottom().left().padLeft(Gdx.graphics.getHeight() * 0.05f);
        main.setBackground(new Image(Assets.getSprite("select-menu-background")).getDrawable());

        top = new Table();
        main.add(top).expand().fillX().top().left().height(Value.percentHeight(0.35f, main));

        main.row();

        bottom = new Table();
        main.add(bottom).expand().fillX().bottom().left().height(Value.percentHeight(0.65f, main));
    }

    public void setSelected(GameObject selected) {
        this.selected = selected;

        if(selected != null) {
            top.clear();
            bottom.clear();
            selected.setupSelectMenu(top, bottom);
        } else {
            top.clear();
            bottom.clear();
        }
    }

    public GameObject getSelected() {
        return selected;
    }

    @Override
    public void updateData() {
        if (selected != null) {
           selected.updateSelectMenu();
        }
    }

    @Override
    public void onClose() {
        selected = null;
    }
}
