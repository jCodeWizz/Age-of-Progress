package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextButton;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.items.ItemType;
import dev.codewizz.world.objects.hermits.Craftsman;

import java.util.HashMap;
import java.util.Map;

public class ResourceMenu extends Menu implements IUpdateDataMenu {

    private HashMap<ItemType, UILabel> labels;
    public Table main;

    public ResourceMenu(Stage stage, GameLayer layer) {
        super(stage, layer);

        this.shouldClose = false;
    }

    @Override
    protected void setup() {
        labels = new HashMap<>();

        main = new Table();
        base.add(main).expand().size(150 * Layer.scale, 50 * Layer.scale).bottom().right().padRight(Gdx.graphics.getWidth() * 0.01f);
        main.setBackground(new Image(Assets.getSprite("resource-menu-background")).getDrawable());

        Table left = new Table();
        main.add(left).expand().size(71 * Layer.scale, 0).fillY().pad(3 * Layer.scale, 3 * Layer.scale, 0, 0);

        addRawResource(left, ItemType.WOOD);
        addRawResource(left, ItemType.STONE);

        Table right = new Table();
        main.add(right).expand().size(72 * Layer.scale, 0).fillY().pad(3 * Layer.scale, Layer.scale, 0, 3 * Layer.scale);

        addResource(right, ItemType.PLANKS);
    }

    private void addRawResource(Table left, ItemType type) {
        Table resource = new Table();

        Image image = new Image(type.getSprite());
        UILabel label = UILabel.create("0", UILabel.defaultStyle);

        resource.add(image).size(12 * Layer.scale).left().top().pad(Layer.scale);
        resource.add(label).left().top();

        left.add(resource).expand().left().top();
        left.row();

        labels.put(type, label);
    }

    private void addResource(Table right, ItemType type) {
        Table resource = new Table();

        Table info = new Table();
        resource.add(info).expand().size(37 * Layer.scale, 47 * Layer.scale);

        Table limit = new Table();
        resource.add(limit).expand().size(37 * Layer.scale, 47 * Layer.scale).padLeft(Layer.scale);

        Image image = new Image(type.getSprite());
        UILabel label = UILabel.create("0", UILabel.defaultStyle);

        info.add(image).size(12 * Layer.scale).left().top().pad(Layer.scale);
        info.add(label).left().top();

        UITextButton less = UITextButton.create("<", UITextButton.resourceStyle);
        UITextButton more = UITextButton.create(">", UITextButton.resourceStyle);
        UILabel lim = UILabel.create(Craftsman.PLANKS_LIMIT + "", UILabel.mediumStyle);

        limit.add(less).expandX().left();
        limit.add(lim).expandX().center();
        limit.add(more).expandX().right();

        right.add(resource).expand().left().top();
        right.row();

        labels.put(type, label);

        less.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Craftsman.PLANKS_LIMIT -= 5;
                lim.setText(Craftsman.PLANKS_LIMIT + "");
            }
        });

        more.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Craftsman.PLANKS_LIMIT += 5;
                lim.setText(Craftsman.PLANKS_LIMIT + "");
            }
        });
    }

    @Override
    public void updateData() {
        if (Main.inst.world.settlement != null) {
            for (Map.Entry<ItemType, UILabel> type : labels.entrySet()) {
                type.getValue().setText("" + Main.inst.world.settlement.inventory.getSizeOf(type.getKey()));
            }
        }
    }
}