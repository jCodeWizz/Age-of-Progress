package dev.codewizz.world.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public abstract class PathTile extends Tile {

	private boolean[] neighbours = new boolean[] { false, false, false, false };

	private static BufferedImage t = Assets.getImage("t");
	private static BufferedImage tTL = Assets.getImage("tTL");
	private static BufferedImage tTR = Assets.getImage("tTR");
	private static BufferedImage tBR = Assets.getImage("tBR");
	private static BufferedImage tBL = Assets.getImage("tBL");

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
		
		BufferedImage b = new BufferedImage(t.getWidth(), t.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
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
		
		Graphics g = b.createGraphics();

		g.drawImage(t, 0, 0, null);
		
		if (neighbours[0])
			g.drawImage(tTL, 0, 0, null);
		if (neighbours[1])
			g.drawImage(tTR, 0, 0, null);
		if (neighbours[2])
			g.drawImage(tBR, 0, 0, null);
		if (neighbours[3])
			g.drawImage(tBL, 0, 0, null);
		
		for(int yy = 0; yy < b.getHeight(); yy++) {
			for(int xx = 0; xx < b.getWidth(); xx++) {
				int color = b.getRGB(xx, yy);
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
