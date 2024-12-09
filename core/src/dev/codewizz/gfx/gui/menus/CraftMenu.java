package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIImageButton;
import dev.codewizz.gfx.gui.elements.UITextTooltip;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Recipe;
import dev.codewizz.world.objects.tasks.CraftTask;
import java.util.ArrayList;
import java.util.List;

public class CraftMenu extends Menu {

    public static List<Recipe> recipes = new ArrayList<>();

    private GameObject focus;

    private Table main;

    public CraftMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        recipes.add(Registers.recipes.get("aop:crude_axe"));
        recipes.add(Registers.recipes.get("aop:planks"));

        main = new Table();

        for (Recipe recipe : recipes) {
            UIImageButton button = UIImageButton.create(UIImageButton.buySlotStyle,
                                                        recipe.getResult()[0].item.getSprite());
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Main.inst.world.settlement.addTask(new CraftTask(recipe), true);
                }
            });
            main.add(button).size(64, 64);
            button.addListener(UITextTooltip.create(
                    "Craft " + recipe.getResult()[0].getSize() + "x " + recipe.getResult()[0].getType()
                            .getName()));
        }

        base.add(main).expand().size(64, 64);
    }

    public void open(GameObject object) {
        super.open();

        focus = object;
        Vector3 coords = Main.inst.camera.cam.project(
                new Vector3(focus.getX() + 50, focus.getY() + 20, 0));
        main.setPosition((int) coords.x, (int) coords.y);
    }

    @Override
    public void render(SpriteBatch b) {
        super.render(b);

        Vector3 coords = Main.inst.camera.cam.project(
                new Vector3(focus.getX() + 50, focus.getY() + 20, 0));
        main.setPosition((int) coords.x, (int) coords.y);
    }
}

