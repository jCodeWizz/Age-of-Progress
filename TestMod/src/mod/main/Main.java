package mod.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.modding.JavaMod;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Assets;

public class Main extends JavaMod {
	
	@Override
	public void onRegister() {
		Registers.registerEvent(info, new OpenWorldEvent());
	}

	@Override
	public void onStart() {
	
		
		
	}
	
	@Override
	public void onStop() {
	}

	@Override
	public void update(float dt) {
	}
	
	@Override
	public void render(SpriteBatch b) {
		b.draw(Assets.getSprite("test-mod:pfp"), 0, 0);
	}

	
}
