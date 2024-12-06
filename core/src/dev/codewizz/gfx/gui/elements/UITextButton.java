package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class UITextButton extends TextButton {

    public final static TextButtonStyle defaultStyle = new TextButtonStyle();
    public final static TextButtonStyle smallStyle = new TextButtonStyle();
    public final static TextButtonStyle resourceStyle = new TextButtonStyle();

    private float moveText = Layer.scale * 2;

    static {
        reload();
    }

    public static void reload() {
        int border = 5;

        NinePatch buttonUpPatch = new NinePatch(Assets.getSprite("button"), border, border, border, border);
        NinePatch buttonDownPatch = new NinePatch(Assets.getSprite("button-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatch = new NinePatch(Assets.getSprite("button-unavailable"), border, border, border, border);
        defaultStyle.up = new NinePatchDrawable(buttonUpPatch);
        defaultStyle.down = new NinePatchDrawable(buttonDownPatch);
        defaultStyle.disabled = new NinePatchDrawable(buttonDisabledPatch);
        defaultStyle.font = UILabel.buttonFont;
        defaultStyle.fontColor = Color.WHITE;

        buttonUpPatch.scale(Layer.scale, Layer.scale);
        buttonDownPatch.scale(Layer.scale, Layer.scale);
        buttonDisabledPatch.scale(Layer.scale, Layer.scale);

        NinePatch buttonUpPatchSmall = new NinePatch(Assets.getSprite("button"), border, border, border, border);
        NinePatch buttonDownPatchSmall = new NinePatch(Assets.getSprite("button-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatchSmall = new NinePatch(Assets.getSprite("button-unavailable"), border, border, border, border);
        smallStyle.up = new NinePatchDrawable(buttonUpPatchSmall);
        smallStyle.down = new NinePatchDrawable(buttonDownPatchSmall);
        smallStyle.disabled = new NinePatchDrawable(buttonDisabledPatchSmall);
        smallStyle.font = UILabel.smallFont;
        smallStyle.fontColor = Color.WHITE;

        resourceStyle.font = UILabel.mediumFont;
        resourceStyle.fontColor = Color.WHITE;
    }

    private UITextButton(String text) {
        super(text, defaultStyle);
    }

    private UITextButton(String text, TextButtonStyle style) {
        super(text, style);

        this.moveText = 2f;
    }

    public static UITextButton create(String text) {
        return new UITextButton(text);
    }

    public static UITextButton create(String text, TextButtonStyle style) {
        return new UITextButton(text, style);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float adjustment = this.isPressed() ? -moveText : moveText;
        this.getLabel().setY(this.getLabel().getY() + adjustment);

        super.draw(batch, parentAlpha);

        this.getLabel().setY(this.getLabel().getY() - adjustment);
    }
}
