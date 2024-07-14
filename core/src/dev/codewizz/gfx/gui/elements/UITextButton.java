package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;

public class UITextButton extends TextButton {

    public final static float scale = 3f;
    public final static float textMoveDownAmount = scale * 2;


    public final static TextButtonStyle defaultStyle = new TextButtonStyle();

    static {

        int border = 5;

        NinePatch buttonUpPatch = new NinePatch(Assets.getSprite("button"), border, border, border, border);
        NinePatch buttonDownPatch = new NinePatch(Assets.getSprite("button-pressed"), border, border, border, border);
        NinePatch buttonDisabledPatch = new NinePatch(Assets.getSprite("button-unavailable"), border, border, border, border);
        defaultStyle.up = new NinePatchDrawable(buttonUpPatch);
        defaultStyle.down = new NinePatchDrawable(buttonDownPatch);
        defaultStyle.disabled = new NinePatchDrawable(buttonDisabledPatch);
        defaultStyle.font = new BitmapFont();
        defaultStyle.fontColor = Color.WHITE;


        buttonUpPatch.scale(scale, scale);
        buttonDownPatch.scale(scale, scale);
        buttonDisabledPatch.scale(scale, scale);
    }

    public UITextButton(String text, TextButtonStyle style) {
        super(text, style);
    }

    public static UITextButton createTextButton(String text) {
        return new UITextButton(text, defaultStyle);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float adjustment = this.isPressed() ? -textMoveDownAmount : textMoveDownAmount;
        this.getLabel().setY(this.getLabel().getY() + adjustment);

        super.draw(batch, parentAlpha);

        this.getLabel().setY(this.getLabel().getY() - adjustment);
    }

}
