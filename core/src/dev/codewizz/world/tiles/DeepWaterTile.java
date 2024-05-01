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

	float time = 0;

	@Override
	public void render(SpriteBatch b) {
		//b.setShader(Shaders.waterShader);

		Shaders.waterShader.setUniformf("time", cell.world.timer);
		Shaders.waterShader.setUniformf("index", new Vector2(cell.getWorldIndexX(), cell.getWorldIndexY()));

		super.render(b);

		b.setShader(Shaders.defaultShader);
	}
}
