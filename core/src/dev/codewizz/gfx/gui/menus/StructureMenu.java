package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class StructureMenu extends Menu {

    public StructureMenu(Stage stage, Layer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        Table main = new Table();
        base.add(main).expand().left();
        main.setBackground(createBackground());

        Button finishIcon = UIIconButton.create("done-icon");
        Button addIcon = UIIconButton.create("plus-icon");
        Button doorIcon = UIIconButton.create("door-icon");
        Button removeIcon = UIIconButton.create("close-icon");

        main.add(removeIcon).expand().left().size(66, 72).pad(10, 10, 5, 10);
        main.row();
        main.add(doorIcon).expand().left().size(66, 72).pad(5, 10, 5, 10);
        main.row();
        main.add(addIcon).expand().left().size(66, 72).pad(5, 10, 5, 10);
        main.row();
        main.add(finishIcon).expand().left().size(66, 72).pad(5, 10, 10, 10);

    }

    private TextureRegionDrawable createBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(0, 0, 0, 0.7f));
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        return new TextureRegionDrawable(new TextureRegion(texture));
    }
}
