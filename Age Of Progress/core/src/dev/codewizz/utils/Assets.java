package dev.codewizz.utils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;


public class Assets {

	public static HashMap<String, Sprite> sprites = new HashMap<>();
	public static HashMap<String, TextureAtlas> atlasses = new HashMap<>();
	public static HashMap<String, BufferedImage> images = new HashMap<>();
	public static HashMap<String, Texture> procuderal = new HashMap<>();

	public static void create() {
		atlasses.put("tiles", new TextureAtlas(Gdx.files.internal("packs/tiles.atlas")));
		atlasses.put("entities", new TextureAtlas(Gdx.files.internal("packs/entities.atlas")));
		atlasses.put("particles", new TextureAtlas(Gdx.files.internal("packs/particles.atlas")));
		atlasses.put("items", new TextureAtlas(Gdx.files.internal("packs/items.atlas")));
		atlasses.put("objects", new TextureAtlas(Gdx.files.internal("packs/objects.atlas")));
		atlasses.put("ui", new TextureAtlas(Gdx.files.internal("packs/ui.atlas")));
		atlasses.put("paths", new TextureAtlas());
		
		for(TextureAtlas t : atlasses.values()) {
			for (AtlasRegion s : t.getRegions()) {
				sprites.put(s.name, t.createSprite(s.name));
			}
		}

		/*
		sprites.put("cow-move-1", atlasses.get("entities").createSprite("cow-move-1"));
		sprites.put("cow-move-2", atlasses.get("entities").createSprite("cow-move-2"));
		sprites.put("cow-move-3", atlasses.get("entities").createSprite("cow-move-3"));
		sprites.put("cow-idle", atlasses.get("entities").createSprite("cow-idle"));
		sprites.put("wolf-idle", atlasses.get("entities").createSprite("wolf-idle"));
		sprites.put("wolf-move-1", atlasses.get("entities").createSprite("wolf-move-1"));
		sprites.put("wolf-move-2", atlasses.get("entities").createSprite("wolf-move-2"));
		sprites.put("wolf-move-3", atlasses.get("entities").createSprite("wolf-move-3"));
		sprites.put("wolf-move-4", atlasses.get("entities").createSprite("wolf-move-4"));
		sprites.put("wolf-move-5", atlasses.get("entities").createSprite("wolf-move-5"));
		sprites.put("wolf-move-6", atlasses.get("entities").createSprite("wolf-move-6"));
		sprites.put("down-hermit-1", atlasses.get("entities").createSprite("down-hermit-1"));
		sprites.put("down-hermit-2", atlasses.get("entities").createSprite("down-hermit-2"));
		sprites.put("down-hermit-3", atlasses.get("entities").createSprite("down-hermit-3"));
		sprites.put("down-left-hermit-1", atlasses.get("entities").createSprite("down-left-hermit-1"));
		sprites.put("down-left-hermit-2", atlasses.get("entities").createSprite("down-left-hermit-2"));
		sprites.put("down-left-hermit-3", atlasses.get("entities").createSprite("down-left-hermit-3"));
		sprites.put("left-hermit-1", atlasses.get("entities").createSprite("left-hermit-1"));
		sprites.put("left-hermit-2", atlasses.get("entities").createSprite("left-hermit-2"));
		sprites.put("left-hermit-3", atlasses.get("entities").createSprite("left-hermit-3"));
		sprites.put("left-up-hermit-1", atlasses.get("entities").createSprite("left-up-hermit-1"));
		sprites.put("left-up-hermit-2", atlasses.get("entities").createSprite("left-up-hermit-2"));
		sprites.put("left-up-hermit-3", atlasses.get("entities").createSprite("left-up-hermit-3"));
		sprites.put("up-hermit-1", atlasses.get("entities").createSprite("up-hermit-1"));
		sprites.put("up-hermit-2", atlasses.get("entities").createSprite("up-hermit-2"));
		sprites.put("up-hermit-3", atlasses.get("entities").createSprite("up-hermit-3"));
		sprites.put("up-right-hermit-1", atlasses.get("entities").createSprite("up-right-hermit-1"));
		sprites.put("up-right-hermit-2", atlasses.get("entities").createSprite("up-right-hermit-2"));
		sprites.put("up-right-hermit-3", atlasses.get("entities").createSprite("up-right-hermit-3"));
		sprites.put("right-hermit-1", atlasses.get("entities").createSprite("right-hermit-1"));
		sprites.put("right-hermit-2", atlasses.get("entities").createSprite("right-hermit-2"));
		sprites.put("right-hermit-3", atlasses.get("entities").createSprite("right-hermit-3"));
		sprites.put("right-down-hermit-1", atlasses.get("entities").createSprite("right-down-hermit-1"));
		sprites.put("right-down-hermit-2", atlasses.get("entities").createSprite("right-down-hermit-2"));
		sprites.put("right-down-hermit-3", atlasses.get("entities").createSprite("right-down-hermit-3"));
		sprites.put("hermit-axe-1", atlasses.get("entities").createSprite("hermit-axe-1"));
		sprites.put("hermit-axe-2", atlasses.get("entities").createSprite("hermit-axe-2"));
		sprites.put("farmer-hermit", atlasses.get("entities").createSprite("farmer-hermit"));
		
		sprites.put("tree", atlasses.get("objects").createSprite("tree"));
		sprites.put("mushrooms", atlasses.get("objects").createSprite("mushrooms"));
		sprites.put("flag", atlasses.get("objects").createSprite("flag"));
		sprites.put("rock", atlasses.get("objects").createSprite("rock"));
		sprites.put("bush", atlasses.get("objects").createSprite("bush"));
		sprites.put("bush-berries", atlasses.get("objects").createSprite("bush-berries"));
		sprites.put("rock-broken", atlasses.get("objects").createSprite("rock-broken"));
		sprites.put("tent", atlasses.get("objects").createSprite("tent"));

		sprites.put("particle-default", atlasses.get("particles").createSprite("particle-default"));
		sprites.put("particle-leave", atlasses.get("particles").createSprite("particle-leave"));
		
		sprites.put("icon", atlasses.get("ui").createSprite("icon"));
		sprites.put("icon-pressed", atlasses.get("ui").createSprite("icon-pressed"));
		sprites.put("icon-unavailable", atlasses.get("ui").createSprite("icon-unavailable"));
		sprites.put("button", atlasses.get("ui").createSprite("button"));
		sprites.put("button-pressed", atlasses.get("ui").createSprite("button-pressed"));
		sprites.put("button-unavailable", atlasses.get("ui").createSprite("button-unavailable"));
		sprites.put("build-icon", atlasses.get("ui").createSprite("build-icon"));
		sprites.put("close-icon", atlasses.get("ui").createSprite("close-icon"));
		sprites.put("icon-board", atlasses.get("ui").createSprite("icon-board"));
		sprites.put("manage-icon", atlasses.get("ui").createSprite("manage-icon"));
		sprites.put("path-icon", atlasses.get("ui").createSprite("path-icon"));
		sprites.put("people-icon", atlasses.get("ui").createSprite("people-icon"));
		sprites.put("tool-icon", atlasses.get("ui").createSprite("tool-icon"));
		sprites.put("tile-highlight", atlasses.get("ui").createSprite("tile-highlight"));
		sprites.put("tile-highlight2", atlasses.get("ui").createSprite("tile-highlight2"));
		sprites.put("path-menu", atlasses.get("ui").createSprite("path-menu"));
		sprites.put("info-menu", atlasses.get("ui").createSprite("info-menu"));
		sprites.put("buyslot", atlasses.get("ui").createSprite("tile-background-buyslot"));
		sprites.put("buyslot-pressed", atlasses.get("ui").createSprite("tile-background-buyslot-pressed"));
		sprites.put("fade", atlasses.get("ui").createSprite("fade"));
		sprites.put("main-menu", new Sprite(new Texture(Gdx.files.internal("textures/ui/icons/main-menu.png"))));
		sprites.put("selected-background", new Sprite(new Texture(Gdx.files.internal("textures/ui/icons/selected-background.png"))));
		sprites.put("tab-button", atlasses.get("ui").createSprite("tab-button"));
		sprites.put("tab-button-pressed", atlasses.get("ui").createSprite("tab-button-pressed"));
		sprites.put("tab-button-unavailable", atlasses.get("ui").createSprite("tab-button-unavailable"));
		sprites.put("notification", atlasses.get("ui").createSprite("notification"));
		sprites.put("settlement-menu", atlasses.get("ui").createSprite("settlement-menu"));
		sprites.put("work-icon", atlasses.get("ui").createSprite("work-icon"));
		sprites.put("worker-icon", atlasses.get("ui").createSprite("worker-icon"));
		sprites.put("farmer-icon", atlasses.get("ui").createSprite("farmer-icon"));

		
		sprites.put("item-wood", atlasses.get("items").createSprite("item-wood"));
		sprites.put("item-stone", atlasses.get("items").createSprite("item-stone"));
		sprites.put("item-carrot", atlasses.get("items").createSprite("item-carrot"));
		
		*/
		addImage("textures/procuderal/path-tile.png", "t");
		addImage("textures/procuderal/path-tile-TL.png", "tTL");
		addImage("textures/procuderal/path-tile-TR.png", "tTR");
		addImage("textures/procuderal/path-tile-BR.png", "tBR");
		addImage("textures/procuderal/path-tile-BL.png", "tBL");
		addImage("textures/tiles/grass-tile.png", "grass");
		addImage("textures/tiles/tiled-tile-1.png", "tiled-tile-1");
		addImage("textures/tiles/tiled-tile-2.png", "tiled-tile-2");
		addImage("textures/tiles/tiled-tile-3.png", "tiled-tile-3");
		addImage("textures/tiles/tiled-tile-4.png", "tiled-tile-4");
		addImage("textures/tiles/tiled-tile-5.png", "tiled-tile-5");
		addImage("textures/tiles/tiled-tile-6.png", "tiled-tile-6");
		addImage("textures/tiles/tiled-tile-7.png", "tiled-tile-7");
		addImage("textures/tiles/tiled-tile-8.png", "tiled-tile-8");

		procuderal.put("aop:grass-tile", new Texture(Gdx.files.internal("textures/tiles/grass-tile.png")));
		procuderal.put("aop:dirt-tile", new Texture(Gdx.files.internal("textures/tiles/dirt-tile.png")));
		procuderal.put("aop:tiled-tile-1", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-1.png")));
		procuderal.put("aop:tiled-tile-2", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-2.png")));
		procuderal.put("aop:tiled-tile-3", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-3.png")));
		procuderal.put("aop:tiled-tile-4", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-4.png")));
		procuderal.put("aop:tiled-tile-5", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-5.png")));
		procuderal.put("aop:tiled-tile-6", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-6.png")));
		procuderal.put("aop:tiled-tile-7", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-7.png")));
		procuderal.put("aop:tiled-tile-8", new Texture(Gdx.files.internal("textures/tiles/tiled-tile-8.png")));

		
	}
	
	private static void addImage(String path, String name) {
		BufferedImage image = null;
		FileHandle s = Gdx.files.internal(path);

		try {
			image = ImageIO.read(s.file());
			
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		images.put(name, image);
	}
	
	public static BufferedImage getImage(String name) {
		return images.get(name);
	}
	
	
	
	
	public static TextureAtlas getAtlas(String s) {
		if(atlasses.containsKey(s)) {
			return atlasses.get(s);
		} else {
			return null;
		}
	}
	
	public static void addSprite(String s, Sprite sprite) {
		sprites.put(s, sprite);
	}
	
	public static Sprite getSprite(String s) {
		if(sprites.containsKey(s)) {
			return sprites.get(s);
		} else {
			Logger.error("Couldn't find requested texture in Assets Handler:"); Logger.error(s);
			return sprites.get("cow-idle");
		}
	}
	
	public static boolean containsSprite(String s) {
		return sprites.containsKey(s);
	}
	
	public static void dispose() {
		for(TextureAtlas ta : atlasses.values()) {
			ta.dispose();
		}
	}
}
