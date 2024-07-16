package dev.codewizz.gfx.gui.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UILabel extends Label {

    public final static LabelStyle defaultStyle = new LabelStyle();
    public final static LabelStyle smallStyle = new LabelStyle();

    public static BitmapFont buttonFont;
    public static BitmapFont normalFont;

    static {
        reload();
    }

    public static void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/basic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        buttonFont = generator.generateFont(parameter);
        parameter.size = 14;
        normalFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public static void reload() {
        generateFonts();

        defaultStyle.font = buttonFont;
        defaultStyle.fontColor = Color.WHITE;

        smallStyle.font = normalFont;
        smallStyle.fontColor = Color.WHITE;
    }

    private UILabel(CharSequence text, LabelStyle style) {
        super(text, style);
    }

    public static UILabel create(String text) {
        return new UILabel(text, defaultStyle);
    }

    public static UILabel create(String text, LabelStyle style) {
        return new UILabel(text, style);
    }
}
