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

    static {
        reload();
    }

    public static void reload() {
        int border = 5;

        NinePatch buttonUpPatch = new NinePatch(Assets.getSprite("icon"), border, border, border, border);
        NinePatch buttonDownPatch = new NinePatch(Assets.getSprite("icon-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatch = new NinePatch(Assets.getSprite("icon-unavailable"), border, border, border, border);
        defaultStyle.up = new NinePatchDrawable(buttonUpPatch);
        defaultStyle.down = new NinePatchDrawable(buttonDownPatch);
        defaultStyle.disabled = new NinePatchDrawable(buttonDisabledPatch);

        buttonUpPatch.scale(Layer.scale, Layer.scale);
        buttonDownPatch.scale(Layer.scale, Layer.scale);
        buttonDisabledPatch.scale(Layer.scale, Layer.scale);
    }

    private final Sprite icon;

    private UIIconButton(String icon, ButtonStyle style) {
        super(style);
        this.icon = Assets.getSprite(icon);
    }

    public static UIIconButton create(String icon) {
        return new UIIconButton(icon, defaultStyle);
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
