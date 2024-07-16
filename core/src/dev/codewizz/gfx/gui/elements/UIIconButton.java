package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class UIIconButton extends Button {

    public static ButtonStyle defaultStyle = new ButtonStyle();
    public static ButtonStyle smallStyle = new ButtonStyle();

    static {
        reload();
    }

    public static void reload() {
        int border = 5;

        NinePatch buttonUpPatchDefault = new NinePatch(Assets.getSprite("icon"), border, border, border, border);
        NinePatch buttonDownPatchDefault = new NinePatch(Assets.getSprite("icon-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatchDefault = new NinePatch(Assets.getSprite("icon-unavailable"), border, border, border, border);
        defaultStyle.up = new NinePatchDrawable(buttonUpPatchDefault);
        defaultStyle.down = new NinePatchDrawable(buttonDownPatchDefault);
        defaultStyle.disabled = new NinePatchDrawable(buttonDisabledPatchDefault);
        buttonUpPatchDefault.scale(Layer.scale, Layer.scale);
        buttonDownPatchDefault.scale(Layer.scale, Layer.scale);
        buttonDisabledPatchDefault.scale(Layer.scale, Layer.scale);

        NinePatch buttonUpPatchSmall = new NinePatch(Assets.getSprite("icon"), border, border, border, border);
        NinePatch buttonDownPatchSmall = new NinePatch(Assets.getSprite("icon-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatchSmall = new NinePatch(Assets.getSprite("icon-unavailable"), border, border, border, border);
        smallStyle.up = new NinePatchDrawable(buttonUpPatchSmall);
        smallStyle.down = new NinePatchDrawable(buttonDownPatchSmall);
        smallStyle.disabled = new NinePatchDrawable(buttonDisabledPatchSmall);


    }

    private final Sprite icon;

    private UIIconButton(String icon, ButtonStyle style) {
        super(style);
        this.icon = Assets.getSprite(icon);
    }

    public static UIIconButton create(String icon) {
        return new UIIconButton(icon, defaultStyle);
    }

    public static UIIconButton create(String icon, ButtonStyle style) {
        return new UIIconButton(icon, style);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (this.isPressed()) {
            batch.draw(icon, this.getX(), this.getY() - Layer.scale * 2, getWidth(), getHeight()); //TODO: move scale var
        } else {
            batch.draw(icon, this.getX(), this.getY(), getWidth(), getHeight());
        }
    }
}
