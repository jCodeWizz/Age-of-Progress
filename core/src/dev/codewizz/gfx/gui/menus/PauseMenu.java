package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.World;

public class PauseMenu extends Menu {

    public PauseMenu(Stage stage, Layer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        base.setBackground(new TextureRegionDrawable(createBackground()));

    }

    private TextureRegionDrawable createBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f));
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    @Override
    public void onOpen() {
        World.gameSpeed = 0;
    }

    @Override
    public void onClose() {
        World.gameSpeed = 1;
    }
}
