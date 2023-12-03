package mod.main;

import dev.codewizz.modding.JavaMod;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;

public class Main extends JavaMod {
	
	@Override
	public void onRegister() {
		
	}

	@Override
	public void onStart() {
		Logger.log("registering");
		Registers.registerEvent(info, new OpenWorldEvent());
	}
	
	@Override
	public void onStop() {
	}

	@Override
	public void update(float dt) {
	}

	
}
