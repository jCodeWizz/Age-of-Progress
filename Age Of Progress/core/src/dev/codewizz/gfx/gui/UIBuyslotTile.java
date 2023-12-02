package dev.codewizz.gfx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import dev.codewizz.input.MouseInput;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class UIBuyslotTile extends UIElement {

	private BitmapFont f;
	private Sprite background;
	private Sprite backgroundPressed;
	private Tile tile;
	
	private int tileScaleX, tileScaleY;
	
	public UIBuyslotTile(String id, int x, int y, int w, int h, Tile tile) {
		super(id, x, y, w, h);
		this.tile = tile;
		
		
		
		background = Assets.getSprite("buyslot");
		backgroundPressed = Assets.getSprite("buyslot-pressed");
		
		int size = 12;
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size * UILayer.SCALE;
		parameter.characters = UIText.FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f = UIText.generator.generateFont(parameter);
	
		tileScaleY = (h-8) * UILayer.SCALE;
		tileScaleX = (int)(((float)(tileScaleY) / 48f) * 64f);
	}
	
	@Override
	protected void onClick() {
		MouseInput.object = false;
		MouseInput.currentlyDrawingId = tile.getId();
	}
	
	@Override
	protected void onDeClick() {
	}

	@Override
	public void render(SpriteBatch b) {
		if(available) {
			if(pressed) {
				b.draw(backgroundPressed, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			} else {
				b.draw(background, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			}
			f.draw(b, tile.getName(), x + (4) * UILayer.SCALE, y + (h-8) * UILayer.SCALE);
			b.draw(tile.getCurrentSprite(), x+(w) * UILayer.SCALE - tileScaleX, y + (4 * UILayer.SCALE), tileScaleX, tileScaleY);

		} else {
			b.draw(backgroundPressed, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			f.draw(b, tile.getName(), x + (4) * UILayer.SCALE, y + (h-8) * UILayer.SCALE);
			b.draw(tile.getCurrentSprite(), x+(w) * UILayer.SCALE - tileScaleX, y + (4 * UILayer.SCALE), tileScaleX, tileScaleY);
		}
	}
}
