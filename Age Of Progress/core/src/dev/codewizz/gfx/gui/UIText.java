package dev.codewizz.gfx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class UIText extends UIElement {
	
	public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"�`'<>";
	public static final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/basic.ttf"));
	private String[] text; 
	private BitmapFont f;
	private float offset = 0f;
	
	public UIText(String id, int x, int y, String text, int size) {
		super(id, x, y, 0, 0);
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size * UILayer.SCALE;
		parameter.characters = FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f = generator.generateFont(parameter);
		
		this.text = new String[1];
		this.text[0] = text;
	}
	
	public UIText(String id, int x, int y, float w, String text, int size, float offset) {
		super(id, x, y, 0, 0);
		
		this.offset = offset;
		
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size * UILayer.SCALE;
		parameter.characters = FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f = generator.generateFont(parameter);
		
		ArrayList<String> lines = new ArrayList<>();
		
		GlyphLayout g = new GlyphLayout();
		g.setText(f, text);
		
		this.offset = g.height + offset;
		
		String currentLine = "";
		int currentWidth = 0;
		
		while(text.indexOf(' ') != -1) {
			String word = text.substring(0, text.indexOf(' '));
			text = text.substring(text.indexOf(' ') + 1, text.length());
			
			
			word+=" ";
			g.setText(f, word);
			int ww = (int) g.width;
			
			if(currentWidth + ww < w && !word.equalsIgnoreCase("(n) ")) {
				currentLine += word;
				currentWidth += ww;
			} else {
				lines.add(currentLine);
				currentWidth = 0;
				if(!word.equalsIgnoreCase("(n) ")) {
					currentLine = word;
					currentWidth = ww;
				} else {
					currentWidth = 0;
					currentLine = "";
				}
			}
			
			
			
			
		}		
		
		currentLine += text;
		lines.add(currentLine);
		
		this.text = new String[lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			this.text[i] = lines.get(i);
		}
		
		
	}
	
	public void setColor(Color c) {
		f.setColor(c);
	}
	
	public void setText(String text) {
		this.text[0] = text;
	}

	@Override
	public void render(SpriteBatch b) {
		for(int i = 0; i < text.length; i++) {
			f.draw(b, text[i], x, y - (float)i * offset);
		}
	}
}
