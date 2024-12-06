package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.items.ItemType;

import java.util.HashMap;
import java.util.Map;

public class ResourceMenu extends Menu implements IUpdateDataMenu {

    private HashMap<ItemType, UILabel> labels;

    public ResourceMenu(Stage stage, GameLayer layer) {
        super(stage, layer);

        this.shouldClose = false;
    }

    @Override
    protected void setup() {
        labels = new HashMap<>();

        Table main = new Table();
        base.add(main).expand().size(150 * Layer.scale, 50 * Layer.scale).bottom().right().padRight(Gdx.graphics.getHeight() * 0.05f);
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

    private void  addResource(Table right, ItemType type) {
        Table resource = new Table();

        Image image = new Image(type.getSprite());
        UILabel label = UILabel.create("0", UILabel.defaultStyle);

        Table input = new Table();

        resource.add(image).size(12 * Layer.scale).left().top().pad(Layer.scale);
        resource.add(label).left().top();
        resource.add(input).expand().left().top();

        right.add(resource).expand().left().top();
        right.row();

        labels.put(type, label);
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