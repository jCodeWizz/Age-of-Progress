package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;

public class CraftMenu extends Menu {

    private GameObject focus;

    private Table main;

    public CraftMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        main = new Table();
        main.setBackground(new Image(Assets.getSprite("settlement-menu")).getDrawable());
        base.add(main).expand().fill().size(100f, 100f);
    }

    public void open(GameObject object) {
        super.open();

        focus = object;
        Vector3 coords = Main.inst.camera.cam.project(new Vector3(focus.getX() + 40, focus.getY() + 20, 0));
        main.setPosition((int) coords.x, (int) coords.y);
    }

    @Override
    public void render(SpriteBatch b) {
        super.render(b);

        Vector3 coords = Main.inst.camera.cam.project(new Vector3(focus.getX() + 40, focus.getY() + 20, 0));
        main.setPosition((int) coords.x, (int) coords.y);
    }
}

