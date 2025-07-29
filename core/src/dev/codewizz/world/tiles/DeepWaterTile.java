package dev.codewizz.world.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.utils.Assets;
import dev.codewizz.world.Tile;

public class DeepWaterTile extends Tile {

	public DeepWaterTile() {
		this.id = "aop:deep-water-tile";
		this.texture = Assets.getSprite("deep-water-tile");
		this.name = "Deep Water";
		this.cost = -1;
	}

	@Override
	public float getShaderId() {
		return 0.1f;
	}
}