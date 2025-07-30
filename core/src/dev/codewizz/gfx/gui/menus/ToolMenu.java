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

public class ToolMenu extends UIIconMenu {
    public ToolMenu(Stage stage, GameLayer layer, UIIconButton parent) {
        super(stage, layer, parent);

        UIIconButton harvest = UIIconButton.create("tool-icon");
        harvest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MouseInput.area = AreaSelector.harvest();
            }
        });
        harvest.addListener(UITextTooltip.create("Gather (H)"));

        addIcon(harvest);
    }
}
