package dev.codewizz.gfx.gui.elements;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import dev.codewizz.utils.Assets;

public class UITextField extends TextField {

    public static TextFieldStyle defaultStyle = new TextFieldStyle();

    static {
        reload();
    }

    public static void reload() {
        defaultStyle.font = UILabel.smallFont;
        defaultStyle.fontColor = Color.WHITE;
        defaultStyle.messageFont = UILabel.smallFont;
        defaultStyle.messageFontColor = Color.LIGHT_GRAY;
        defaultStyle.cursor = new SpriteDrawable(Assets.getSprite("slider"));
        defaultStyle.selection = new SpriteDrawable(Assets.getSprite("slider"));
    }

    private UITextField(String text, TextFieldStyle style) {
        super(text, style);
    }

    public static UITextField create(String text) {
        UITextField textField = new UITextField("", defaultStyle);
        textField.setMessageText(text);
        return textField;
    }
}
