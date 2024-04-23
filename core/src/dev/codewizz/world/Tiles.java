package dev.codewizz.world;

import dev.codewizz.modding.Registers;
import dev.codewizz.world.tiles.ClayTile;
import dev.codewizz.world.tiles.DeepWaterTile;
import dev.codewizz.world.tiles.DirtPathTile;
import dev.codewizz.world.tiles.DirtTile;
import dev.codewizz.world.tiles.EmptyTile;
import dev.codewizz.world.tiles.FarmTile;
import dev.codewizz.world.tiles.FlowerTile;
import dev.codewizz.world.tiles.GrassTile;
import dev.codewizz.world.tiles.SandTile;
import dev.codewizz.world.tiles.TiledTile;
import dev.codewizz.world.tiles.TiledTile2;
import dev.codewizz.world.tiles.TiledTile3;
import dev.codewizz.world.tiles.TiledTile4;
import dev.codewizz.world.tiles.TiledTile5;
import dev.codewizz.world.tiles.TiledTile6;
import dev.codewizz.world.tiles.TiledTile7;
import dev.codewizz.world.tiles.TiledTile8;
import dev.codewizz.world.tiles.WaterTile;

public class Tiles {

	public static void register() {
		
		Registers.registerTile("aop:grass-tile", GrassTile.class);
		Registers.registerTile("aop:dirt-tile", DirtTile.class);
		Registers.registerTile("aop:dirt-path-tile", DirtPathTile.class);
		Registers.registerTile("aop:sand-tile", SandTile.class);
		Registers.registerTile("aop:clay-tile", ClayTile.class);
		Registers.registerTile("aop:empty-tile", EmptyTile.class);
		Registers.registerTile("aop:water-tile", WaterTile.class);
		Registers.registerTile("aop:deep-water-tile", DeepWaterTile.class);
		Registers.registerTile("aop:farm-tile", FarmTile.class);
		Registers.registerTile("aop:flower-tile", FlowerTile.class);
		
		Registers.registerTile("aop:tiled-tile-1", TiledTile.class);
		Registers.registerTile("aop:tiled-tile-2", TiledTile2.class);
		Registers.registerTile("aop:tiled-tile-3", TiledTile3.class);
		Registers.registerTile("aop:tiled-tile-4", TiledTile4.class);
		Registers.registerTile("aop:tiled-tile-5", TiledTile5.class);
		Registers.registerTile("aop:tiled-tile-6", TiledTile6.class);
		Registers.registerTile("aop:tiled-tile-7", TiledTile7.class);
		Registers.registerTile("aop:tiled-tile-8", TiledTile8.class);
	}
}
