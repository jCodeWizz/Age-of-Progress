package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;

public abstract class Menu {

    private boolean open = true;

    protected Stage stage;
    protected Table base;
    protected GameLayer layer;

    public Menu(Stage stage, GameLayer layer) {
        this.stage = stage;
        this.layer = layer;

        base = new Table();
        base.setFillParent(true);
        stage.addActor(base);

        setup();

        close();
    }

    protected abstract void setup();

    public void close() {
        open = false;
        base.setVisible(false);
        onClose();
    }

    public void open() {
        open = true;
        base.setVisible(true);
        onOpen();
    }

    public void toggle() {
        if(open) close();
        else open();
    }

    protected TextureRegionDrawable createBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f));
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public boolean isOpen() {
        return open;
    }

    public void onOpen() {

    }

    public void onClose() {

    }

    public void clickedOn(Cell cell) {

    }

    public void clickedOn(GameObject object) {

    }
}
