package dev.codewizz.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import dev.codewizz.gfx.Renderable;
import dev.codewizz.gfx.Renderer;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.main.Main;
import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.utils.serialization.SerializableObject;
import java.awt.*;
import java.util.UUID;

public abstract class GameObject extends Renderable implements SerializableObject {

    protected String id = "unid-object";
    protected UUID uuid;

    protected float x, y, sortHeight;
    protected int w, h;
    protected boolean flip = false;
    protected Cell cell;

    protected boolean selected = false;
    protected String name = "Object";

    public GameObject() {

    }

    public GameObject(float x, float y) {
        uuid = UUID.randomUUID();

        this.x = x;
        this.y = y;
    }

    public abstract void update(float d);

    public abstract void render(SpriteBatch b);


    @Override
    public GameObjectData save(GameObjectData object) {

        object.addByte(ByteUtils.toByte((byte) 0, flip, 0));

        object.addFloat(x);
        object.addFloat(y);
        object.addInteger(w);
        object.addInteger(h);

        object.addString(name);
        object.end();
        return object;
    }

    @Override
    public boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success) {
        byte[] data = object.take();

        flip = ByteUtils.toBoolean(data[0], 0);

        x = ByteUtils.toFloat(data, 1);
        y = ByteUtils.toFloat(data, 5);
        w = ByteUtils.toInteger(data, 9);
        h = ByteUtils.toInteger(data, 13);

        name = ByteUtils.toString(data, 17);

        return success;
    }

    public void renderDebug() {
        Polygon g = this.getHitBox();

        for (int i = 0; i < g.npoints; i++) {
            Renderer.drawDebugLine(new Vector2(g.xpoints[i % g.npoints], g.ypoints[i % g.npoints]),
                                   new Vector2(g.xpoints[(i + 1) % g.npoints],
                                               g.ypoints[(i + 1) % g.npoints]));
        }
    }

    public void onClick() {}

    public void onDestroy() { }

    public void destroy() {
        if (cell != null) { cell.setObject(null); }

        if (this.isSelected()) {
            if (((GameLayer) Main.inst.renderer.uiLayer).selectMenu.getSelected() == this) {
                deselect();
            }
        }

        onDestroy();
        Main.inst.world.removeObject(this);
        cell = null;
    }

    public void select() {
        selected = true;
        ((GameLayer) Main.inst.renderer.uiLayer).openMenu(
                ((GameLayer) Main.inst.renderer.uiLayer).selectMenu);
        ((GameLayer) Main.inst.renderer.uiLayer).selectMenu.setSelected(this);
        ((GameLayer) Main.inst.renderer.uiLayer).selectMenu.updateData();
    }

    public void deselect() {
        ((GameLayer) Main.inst.renderer.uiLayer).closeMenus();
        ((GameLayer) Main.inst.renderer.uiLayer).selectMenu.setSelected(null);
        selected = false;
    }

    protected UILabel nameLabel;

    public void setupSelectMenu(Table top, Table bottom) {
        nameLabel = UILabel.create(name);

        top.add(nameLabel).expand().fill().center().left().padLeft(6 * Layer.scale);
        top.row();
    }

    public void updateSelectMenu() {
        nameLabel.setText(name);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, w, h);
    }

    public Polygon getHitBox() {
        return new Polygon(new int[]{(int) x + w / 2, (int) x, (int) x + w / 2, (int) x + w},
                           new int[]{(int) y, (int) y + h / 2, (int) y + h, (int) y + h / 2}, 4);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float getSortingHeight() {
        return sortHeight;
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public GameObject flip() {
        this.flip = !this.flip;

        return this;
    }

    public Vector2 getFootPoint() {
        return new Vector2(this.x + this.w / 2f, this.y);
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return this.cell;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Vector2 getCenter() {
        return new Vector2(x + (w / 2f), y);
    }

    @Override
    public String toString() {
        return "GameObject{" + "id='" + id + '\'' + ", uuid=" + uuid + ", x=" + x + ", y=" + y + ", sortHeight=" + sortHeight + ", w=" + w + ", h=" + h + ", flip=" + flip + ", cell=" + cell + ", selected=" + selected + ", name='" + name + '\'' + '}';
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }
}
