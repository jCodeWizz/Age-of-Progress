package mod.main;

import dev.codewizz.modding.JavaMod;
import dev.codewizz.modding.Registers;

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

	
}
