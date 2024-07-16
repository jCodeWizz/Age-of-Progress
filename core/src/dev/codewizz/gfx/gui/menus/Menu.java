package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Logger;

public abstract class Menu extends Table {

    private boolean open = true;

    protected Stage stage;
    protected Table base;
    protected Layer layer;

    public Menu(Stage stage, Layer layer) {
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

    public boolean isOpen() {
        return open;
    }

    public void onOpen() {

    }

    public void onClose() {

    }


}
