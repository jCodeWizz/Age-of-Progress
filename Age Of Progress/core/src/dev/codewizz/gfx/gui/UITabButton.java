package dev.codewizz.gfx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import dev.codewizz.utils.Assets;

public class UITabButton extends UIElement {

	private BitmapFont f;
	private String text;
	private Sprite button, buttonPressed, buttonUnavailable;
	
	private int textWidth, textHeight;
	
	public UITabButton(String id, int x, int y, int w, int h, String text) {
		super(id, x, y, w, h);
		this.text = text;
		this.button = Assets.getSprite("tab-button");
		this.buttonPressed = Assets.getSprite("tab-button-pressed");
		this.buttonUnavailable = Assets.getSprite("tab-button-unavailable");
		
		int size = 5;
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size * UILayer.SCALE;
		parameter.characters = UIText.FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f = UIText.generator.generateFont(parameter);
	
		GlyphLayout g = new GlyphLayout();
		g.setText(f, text);
		
		textWidth = (int) g.width;
		textHeight = (int) g.height;
	}

	@Override
	public void render(SpriteBatch b) {
		if(!available) {
			b.draw(buttonUnavailable, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			f.draw(b, text, x + (w*UILayer.SCALE - textWidth)/2, y + textHeight + (h*UILayer.SCALE - textHeight)/2);
		} else if(pressed) {
			b.draw(buttonPressed, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			f.draw(b, text, x + (w*UILayer.SCALE - textWidth)/2, y + textHeight + (h*UILayer.SCALE - textHeight)/2 - ((float)w/24f) * UILayer.SCALE);
		} else {
			b.draw(button, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			f.draw(b, text, x + (w*UILayer.SCALE - textWidth)/2, y + textHeight + (h*UILayer.SCALE - textHeight)/2);
		}
	}
}
