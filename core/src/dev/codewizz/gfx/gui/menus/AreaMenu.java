package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIIconButton;
import dev.codewizz.gfx.gui.elements.UIIconMenu;
import dev.codewizz.gfx.gui.elements.UITextTooltip;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.input.AreaSelector;
import dev.codewizz.input.MouseInput;
import dev.codewizz.input.TileSelector;
import dev.codewizz.world.objects.tasks.PlantCropTask;

public class AreaMenu extends UIIconMenu {
    public AreaMenu(Stage stage, GameLayer layer, UIIconButton parent) {
        super(stage, layer, parent);

        UIIconButton farm = UIIconButton.create("tool-icon");
        farm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MouseInput.tileArea = TileSelector.task(PlantCropTask.class);
            }
        });
        farm.addListener(UITextTooltip.create("Farm"));

        addIcon(farm);
    }
}
