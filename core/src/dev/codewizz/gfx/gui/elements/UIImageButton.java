package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class UIImageButton extends ImageButton {

    public final static ImageButtonStyle defaultStyle = new ImageButtonStyle();
    public final static ImageButtonStyle buySlotStyle = new ImageButtonStyle();

    static {
        reload();
    }

    public static void reload() {
        int border = 4;


        NinePatch buttonUpPatchDefault = new NinePatch(Assets.getSprite("slot-background"), border, border, border, border);
        NinePatch buttonDownPatchDefault = new NinePatch(Assets.getSprite("slot-background-pressed"), border, border, border, border);
        NinePatch buttonHoverPatchDefault = new NinePatch(Assets.getSprite("slot-background-hovering"), border, border, border, border);
        buySlotStyle.up = new NinePatchDrawable(buttonUpPatchDefault);
        buySlotStyle.down = new NinePatchDrawable(buttonDownPatchDefault);
        buySlotStyle.over = new NinePatchDrawable(buttonHoverPatchDefault);
        //buttonUpPatchDefault.scale(Layer.scale, Layer.scale);
        //buttonDownPatchDefault.scale(Layer.scale, Layer.scale);
        //buttonHoverPatchDefault.scale(Layer.scale, Layer.scale);
    }

    private Sprite sprite;

    private UIImageButton(ImageButtonStyle style, Sprite sprite) {
        super(style);

        this.sprite = sprite;
    }

    public static UIImageButton create(Sprite sprite) {
        return create(defaultStyle, sprite);
    }

    public static UIImageButton create(ImageButtonStyle style, Sprite sprite) {
        return new UIImageButton(style, sprite);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float x = this.getX() + 2;
        float y = this.getY() + 2;
        float w = this.getWidth() - 4;
        float h = this.getHeight() - 4;

        if(sprite.getWidth() >= sprite.getHeight()) {
            float r = sprite.getWidth() / w;
            h = h * r;
        } else {
            float r = sprite.getHeight() / h;
            w = w * r;
        }

        batch.draw(sprite, x, y, w, h);
    }
}
