package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import dev.codewizz.gfx.gui.layers.Layer;
import dev.codewizz.utils.Assets;

public class UIToggle extends CheckBox {

    public final static CheckBox.CheckBoxStyle speed3Style = new CheckBox.CheckBoxStyle();
    public final static CheckBox.CheckBoxStyle speed2Style = new CheckBox.CheckBoxStyle();
    public final static CheckBox.CheckBoxStyle speed1Style = new CheckBox.CheckBoxStyle();
    public final static CheckBox.CheckBoxStyle speed0Style = new CheckBox.CheckBoxStyle();

    static {
        reload();
    }

    public static void reload() {
        Drawable speed3Button = new SpriteDrawable(Assets.getSprite("speed-3"));
        Drawable speed2Button = new SpriteDrawable(Assets.getSprite("speed-2"));
        Drawable speed1Button = new SpriteDrawable(Assets.getSprite("speed-1"));
        Drawable speed0Button = new SpriteDrawable(Assets.getSprite("speed-0"));
        Drawable speed3ButtonPressed = new SpriteDrawable(Assets.getSprite("speed-3-pressed"));
        Drawable speed2ButtonPressed = new SpriteDrawable(Assets.getSprite("speed-2-pressed"));
        Drawable speed1ButtonPressed = new SpriteDrawable(Assets.getSprite("speed-1-pressed"));
        Drawable speed0ButtonPressed = new SpriteDrawable(Assets.getSprite("speed-0-pressed"));

        speed3Style.checkboxOff = speed3Button;
        speed2Style.checkboxOff = speed2Button;
        speed1Style.checkboxOff = speed1Button;
        speed0Style.checkboxOff = speed0Button;

        speed3Style.checkboxOn = speed3ButtonPressed;
        speed2Style.checkboxOn = speed2ButtonPressed;
        speed1Style.checkboxOn = speed1ButtonPressed;
        speed0Style.checkboxOn = speed0ButtonPressed;

        speed3Style.font = new BitmapFont();
        speed2Style.font = new BitmapFont();
        speed1Style.font = new BitmapFont();
        speed0Style.font = new BitmapFont();

        speed3Style.checkboxOff.setMinWidth(21 * Layer.scale);
        speed3Style.checkboxOff.setMinHeight(10 * Layer.scale);
        speed3Style.checkboxOn.setMinWidth(21 * Layer.scale);
        speed3Style.checkboxOn.setMinHeight(10 * Layer.scale);

        speed2Style.checkboxOff.setMinWidth(15 * Layer.scale);
        speed2Style.checkboxOff.setMinHeight(10 * Layer.scale);
        speed2Style.checkboxOn.setMinWidth(15 * Layer.scale);
        speed2Style.checkboxOn.setMinHeight(10 * Layer.scale);

        speed1Style.checkboxOff.setMinWidth(9 * Layer.scale);
        speed1Style.checkboxOff.setMinHeight(10 * Layer.scale);
        speed1Style.checkboxOn.setMinWidth(9 * Layer.scale);
        speed1Style.checkboxOn.setMinHeight(10 * Layer.scale);

        speed0Style.checkboxOff.setMinWidth(9 * Layer.scale);
        speed0Style.checkboxOff.setMinHeight(10 * Layer.scale);
        speed0Style.checkboxOn.setMinWidth(9 * Layer.scale);
        speed0Style.checkboxOn.setMinHeight(10 * Layer.scale);
    }

    public static UIToggle create(CheckBoxStyle style) {
        UIToggle toggle = new UIToggle(null, style);
        toggle.getLabelCell().size(0);
        return toggle;
    }

    private UIToggle(String text, CheckBoxStyle style) {
        super(text, style);
    }
}
