package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import dev.codewizz.utils.Assets;

public class UISlider extends Slider {

    public final static SliderStyle defaultStyle = new SliderStyle();

    static {
        reload();
    }

    public static void reload() {
        defaultStyle.background = new SpriteDrawable(Assets.getSprite("slider"));
        defaultStyle.knobDown = new SpriteDrawable(Assets.getSprite("icon-pressed"));
        defaultStyle.knob = new SpriteDrawable(Assets.getSprite("icon"));
    }

    private UISlider(float min, float max, float stepSize, boolean vertical, SliderStyle style) {
        super(min, max, stepSize, vertical, style);
    }

    public static UISlider create(float min, float max, float stepSize, boolean vertical) {
        return new UISlider(min, max, stepSize, vertical, defaultStyle);
    }
}
