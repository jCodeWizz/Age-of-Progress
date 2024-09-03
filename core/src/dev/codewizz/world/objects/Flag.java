package dev.codewizz.world.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dev.codewizz.input.MouseInput;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.SerializableObject;
import dev.codewizz.world.Cell;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.objects.tasks.MoveTask;
import dev.codewizz.world.settlement.Settlement;
import java.util.List;

public class Flag extends GameObject implements IBuy, SerializableObject {

    private static final Sprite texture = Assets.getSprite("flag");

    public Flag() {
        super();

        this.id = "aop:flag";

        this.sortHeight = 26;
    }

    public Flag(float x, float y) {
        super(x, y);

        this.id = "aop:flag";

        this.sortHeight = 26;
    }

    @Override
    public void update(float d) {
    }

    @Override
    public void render(SpriteBatch b) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {

            Cell cell = MouseInput.hoveringOverCell;
            if (cell != null) {
                Main.inst.world.settlement.addTask(new MoveTask(cell), false);
            }
        }

        b.draw(texture, x + 15, y + 26);
    }

    @Override
    public Sprite getMenuSprite() {
        return texture;
    }

    @Override
    public String getMenuName() {
        return "Flag";
    }

    @Override
    public String getId() {
        return id;
    }

	@Override
	public boolean continues() {
		return false;
	}

    @Override
    public String getMenuDescription() {
        return "The beacon of your Settlement";
    }

    @Override
    public void onPlace(Cell cell) {
        Settlement s = new Settlement(cell);
        Main.inst.world.start(s);

        //TODO: reimplement
        //Main.inst.renderer.ui.getElement("manage-icon").setAvailable(true);
        //Main.inst.renderer.ui.getElement("people-icon").setAvailable(true);
        //Main.inst.renderer.ui.getElement("construction-icon").setAvailable(true);
        //Main.inst.renderer.ui.getElement("tool-icon").setAvailable(true);
        //Main.inst.renderer.ui.getElement("path-icon").setAvailable(true);

    }

    @Override
    public GameObjectData save(GameObjectData object) {
        return super.save(object);
    }

    @Override
    public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
        return super.load(loader, object, success);
    }

    @Override
    public boolean available() {
        return false;
    }

    @Override
    public List<Item> costs() {
        return null;
    }
}