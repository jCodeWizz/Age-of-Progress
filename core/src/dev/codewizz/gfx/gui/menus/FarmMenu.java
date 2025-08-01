package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UIImageButton;
import dev.codewizz.gfx.gui.elements.UITextTooltip;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.Animal;
import dev.codewizz.world.objects.Cow;
import dev.codewizz.world.objects.tasks.CaptureAnimalTask;
import dev.codewizz.world.objects.tasks.KillCowTask;
import dev.codewizz.world.settlement.FarmArea;

public class FarmMenu extends Menu {

    private GameObject focus;

    private Table main;

    public FarmMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    @Override
    protected void setup() {
        main = new Table();
        UIImageButton capture = UIImageButton.create(UIImageButton.buySlotStyle, Assets.getSprite("cow-idle"));
        capture.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Animal closest = null;
                double dist = Double.MAX_VALUE;
                Vector2 home = Main.inst.world.settlement.getLocation();

                for (Animal a : Main.inst.world.nature.animals) {
                    if (!a.isTasked() && !a.isCaptured()) {
                        double newDist = home.dst2(a.getCenter());
                        if (newDist < dist) {
                            dist = newDist;
                            closest = a;
                        }
                    }
                }

                if (closest != null) {
                    Main.inst.world.settlement.addTask(new CaptureAnimalTask(closest, FarmArea.findArea(closest)), true);
                }
            }

        });
        main.add(capture).size(64, 64);
        capture.addListener(UITextTooltip.create("Try to capture Cow"));

        UIImageButton kill = UIImageButton.create(UIImageButton.buySlotStyle, Assets.getSprite("tool-icon"));
        kill.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (FarmArea area : Main.inst.world.settlement.areas) {
                    for (Animal a : area.getAnimals()) {
                        if (a instanceof Cow) {
                            KillCowTask t = new KillCowTask( (Cow) a);
                            Main.inst.world.settlement.addTask(t, true);
                            break;
                        }
                    }
                }
            }

        });
        main.add(kill).size(64, 64);
        kill.addListener(UITextTooltip.create("Kill a cow"));

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

