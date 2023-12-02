package dev.codewizz.modding;

import dev.codewizz.main.Main;

public abstract class JavaMod {

	private Main main;
	
	public void start() {
		main = Main.inst;
		
		onStart();
	}
	
	public void stop() {
		onStop();
	}
	
	public Main getMain() {
		return main;
	}
	
	public abstract void onStart();
	public abstract void onStop();
	public abstract void register();
	public abstract void update(float dt);
}
