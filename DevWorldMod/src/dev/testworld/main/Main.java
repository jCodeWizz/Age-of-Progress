package dev.testworld.main;

import dev.codewizz.modding.JavaMod;
import dev.codewizz.modding.Registers;

public class Main extends JavaMod {

	public static int SIZE = 1;
	
	@Override
	public void onRegister() {
		Registers.registerEvent(info, new OnCreateWorld());
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
