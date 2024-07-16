package dev.codewizz.gfx.gui.layers;

import com.badlogic.gdx.scenes.scene2d.Stage;
import dev.codewizz.gfx.gui.menus.TileMenu;
import dev.codewizz.main.Main;

public class GameLayer extends Layer {

    public TileMenu tileMenu;

    @Override
    public void open(Stage stage) {
        tileMenu = new TileMenu(Main.inst.renderer.uiStage, this);
    }

    @Override
    public void update(float d) {

    }

    @Override
    public void close(Stage stage) {

    }
}
