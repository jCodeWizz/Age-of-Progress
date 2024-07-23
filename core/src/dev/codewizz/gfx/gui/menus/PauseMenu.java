package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.world.World;

public class PauseMenu extends Menu {

    public PauseMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        base.setBackground(new TextureRegionDrawable(createBackground(0.7f)));

        TextButton main = UITextButton.create("Main");
        TextButton back = UITextButton.create("Pause");
        TextButton quit = UITextButton.create("Quit");

        main.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.inst.closeWorld();
            }
        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });

        quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.exit();
            }
        });

        base.add(main).size(160, 80).padBottom(15);
        base.row();
        base.add(back).size(160, 80).pad(15, 0, 15, 0);
        base.row();
        base.add(quit).size(160, 80).padTop(15);

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
