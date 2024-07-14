package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import dev.codewizz.utils.Assets;

public class UITextButton {

    public final static TextButtonStyle defaultStyle = new TextButtonStyle();

    static {
        NinePatch buttonUpPatch = new NinePatch(Assets.getSprite("button"), 4, 4, 4, 4);
        NinePatch buttonDownPatch = new NinePatch(Assets.getSprite("button-pressed"), 4, 4, 4, 4);
        NinePatch buttonDisabledPatch = new NinePatch(Assets.getSprite("button-unavailable"), 4, 4, 4, 4);
        defaultStyle.up = new NinePatchDrawable(buttonUpPatch);
        defaultStyle.down = new NinePatchDrawable(buttonDownPatch);
        defaultStyle.disabled = new NinePatchDrawable(buttonDisabledPatch);
        defaultStyle.font = new BitmapFont();
        defaultStyle.fontColor = Color.WHITE;
    }

    public static TextButton createTextButton(String text) {
        return new TextButton(text, defaultStyle);
    }
}
