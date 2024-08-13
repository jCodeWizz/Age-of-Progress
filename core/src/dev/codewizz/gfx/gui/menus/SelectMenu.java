package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;

public class SelectMenu extends Menu {

    private GameObject selected;

    public SelectMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).size(150 * Layer.scale, 50 * Layer.scale);
        main.setBackground(new Image(Assets.getSprite("select-menu-background")).getDrawable());
    }

    public void setSelected(GameObject selected) {
        this.selected = selected;
    }
}
