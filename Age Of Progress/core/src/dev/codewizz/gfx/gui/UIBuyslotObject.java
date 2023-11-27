package dev.codewizz.gfx.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import dev.codewizz.input.MouseInput;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.objects.IBuy;

public class UIBuyslotObject extends UIElement {

	private BitmapFont f;
	private BitmapFont f2;
	private Sprite background;
	private Sprite backgroundPressed;
	private GameObject object;
	private IBuy objectInfo;
	
	private int tileScaleX, tileScaleY;
	
	public UIBuyslotObject(String id, int x, int y, int w, int h, GameObject object) {
		super(id, x, y, w, h);
		this.object = object;
		this.objectInfo = (IBuy) object;
		
		background = Assets.getSprite("buyslot");
		backgroundPressed = Assets.getSprite("buyslot-pressed");
		
		int size = 12;
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size * UILayer.SCALE;
		parameter.characters = UIText.FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f = UIText.generator.generateFont(parameter);
		
		int size2 = 8;
		FreeTypeFontParameter parameter2 = new FreeTypeFontParameter();
		parameter.size = size2 * UILayer.SCALE;
		parameter.characters = UIText.FONT_CHARACTERS;
		parameter.magFilter = Texture.TextureFilter.Nearest;
		parameter.minFilter = Texture.TextureFilter.Nearest;
		f2 = UIText.generator.generateFont(parameter2);
		
		tileScaleY = UILayer.SCALE * 48;
		float r = objectInfo.getMenuSprite().getWidth()/objectInfo.getMenuSprite().getHeight();
		
		tileScaleX = (int)((float)UILayer.SCALE * 48f * r);
		
	}
	
	@Override
	protected void onClick() {
		MouseInput.object = true;
		MouseInput.currentlyDrawingObject = object;
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
			f.draw(b, objectInfo.getMenuName(), x + (4) * UILayer.SCALE, y + (h-8) * UILayer.SCALE);
			f2.draw(b, objectInfo.getMenuDescription(), x + (4) * UILayer.SCALE, y + (h - 20) * UILayer.SCALE);
			b.draw(objectInfo.getMenuSprite(), x+(w) * UILayer.SCALE - tileScaleX, y + (3 * UILayer.SCALE), tileScaleX, tileScaleY);
		} else {
			b.draw(backgroundPressed, x, y, w * UILayer.SCALE, h * UILayer.SCALE);
			f.draw(b, objectInfo.getMenuName(), x + (4) * UILayer.SCALE, y + (h-8) * UILayer.SCALE);
			f2.draw(b, objectInfo.getMenuDescription(), x + (4) * UILayer.SCALE, y + (h - 20) * UILayer.SCALE);
			b.draw(objectInfo.getMenuSprite(), x+(w) * UILayer.SCALE - tileScaleX, y + (3 * UILayer.SCALE), tileScaleX, tileScaleY);
		}
	}
}
