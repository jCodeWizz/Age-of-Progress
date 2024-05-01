package dev.codewizz.world.tiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import dev.codewizz.gfx.Shaders;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.Tile;

public class WaterTile extends Tile {

	public WaterTile() {
		this.id = "aop:water-tile";
		this.texture = Assets.getSprite("water-tile");
		this.name = "Water";
		this.cost = -1;
	}

	@Override
	public void render(SpriteBatch b) {
		//b.setShader(Shaders.waterShader);

		Shaders.waterShader.setUniformf("time", cell.world.timer);
		Shaders.waterShader.setUniformf("index", new Vector2(cell.getWorldIndexX(), cell.getWorldIndexY()));

		super.render(b);

		b.setShader(Shaders.defaultShader);
	}
}
