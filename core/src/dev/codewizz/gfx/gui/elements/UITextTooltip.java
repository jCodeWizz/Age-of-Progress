package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

public class UITextTooltip extends TextTooltip {

    public final static TextTooltipStyle defaultStyle = new TextTooltipStyle(UILabel.smallStyle, null);
    public final static TextTooltipStyle mediumStyle = new TextTooltipStyle(UILabel.mediumStyle, null);

    private UITextTooltip(String text, TextTooltipStyle style) {
        super(text, style);
    }

    public static UITextTooltip create(String text, TextTooltipStyle style) {
        UITextTooltip tip = new UITextTooltip(text, style);
        tip.setInstant(true);
        return tip;
    }

    public static UITextTooltip create(String text) {
        return create(text, defaultStyle);
    }
}
