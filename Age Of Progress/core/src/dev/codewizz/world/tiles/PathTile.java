package dev.codewizz.world.tiles;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public abstract class PathTile extends Tile {

	private boolean[] neighbours = new boolean[] { false, false, false, false };
	
	private static Texture tp = Assets.procuderal.get("t");
	private static Texture tTLp = Assets.procuderal.get("tTL");
	private static Texture tTRp = Assets.procuderal.get("tTR");
	private static Texture tBRp = Assets.procuderal.get("tBR");
	private static Texture tBLp = Assets.procuderal.get("tBL");

	protected String template;
	protected String templateGround;
	
	public PathTile() {
		this.cost = 1;
	}
	
	@Override
	public void onPlace() {
		this.neighbours = this.checkNeighbours();
		
		redrawTexture();
	}
	
	private void redrawTexture() {
		Pixmap r = new Pixmap( 64, 48, Format.RGBA8888 );
		Pixmap bp = new Pixmap(tp.getWidth(), tp.getHeight(), Format.RGBA8888);
		
		Texture t1 = Assets.procuderal.get(templateGround.toString());
		if (!t1.getTextureData().isPrepared()) {
		    t1.getTextureData().prepare();
		}
		Pixmap grass = t1.getTextureData().consumePixmap();
		grass.setBlending(Blending.None);
		
		Texture t2 = Assets.procuderal.get(template.toString());
		if (!t2.getTextureData().isPrepared()) {
		    t2.getTextureData().prepare();
		}
		Pixmap tiled = t2.getTextureData().consumePixmap();

		if(!tp.getTextureData().isPrepared()) {
			tp.getTextureData().prepare();
		}
		bp.drawPixmap(tp.getTextureData().consumePixmap(), 0, 0);
		
		if (neighbours[0]) {
			if(!tTLp.getTextureData().isPrepared()) {
				tTLp.getTextureData().prepare();
			}
			bp.drawPixmap(tTLp.getTextureData().consumePixmap(), 0, 0);
		}	
		if (neighbours[1]) {
			if(!tTRp.getTextureData().isPrepared()) {
				tTRp.getTextureData().prepare();
			}
			bp.drawPixmap(tTRp.getTextureData().consumePixmap(), 0, 0);
		}
		if (neighbours[2]) {
			if(!tBRp.getTextureData().isPrepared()) {
				tBRp.getTextureData().prepare();
			}
			bp.drawPixmap(tBRp.getTextureData().consumePixmap(), 0, 0);
		}
		if (neighbours[3]) {
			if(!tBLp.getTextureData().isPrepared()) {
				tBLp.getTextureData().prepare();
			}
			bp.drawPixmap(tBLp.getTextureData().consumePixmap(), 0, 0);
		}
		for(int yy = 0; yy < bp.getHeight(); yy++) {
			for(int xx = 0; xx < bp.getWidth(); xx++) {
				int color = bp.getPixel(xx, yy);
				int red = color >>> 24;
				int green = (color & 0xFF0000) >>> 16;
				int blue = (color & 0xFF00) >>> 8;

				if(red == 255 && green == 255 && blue == 0) {
					r.drawPixel(xx, yy, tiled.getPixel(xx, yy));
				} else {
					r.drawPixel(xx, yy, grass.getPixel(xx, yy));
				}
			}
		}
		Sprite s = new Sprite(new Texture(r));
		Assets.atlasses.get("paths").addRegion(getSavedName(), s);
		
		texture = Assets.atlasses.get("paths").createSprite(getSavedName());
	}
	
	@Override
	public void update() {
		this.neighbours = this.checkNeighbours();
		redrawTexture();
	}
	
	private String getSavedName() {
		return id + "-" + neighbours[0] + neighbours[1] + neighbours[2] + neighbours[3];
	}
}
