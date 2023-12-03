package dev.codewizz.modding;

import dev.codewizz.main.Main;

public abstract class JavaMod {

	private Main main;
	protected ModInfo info;
	
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
	
	public void register(ModInfo info) {
		this.info = info;
		onRegister();
	}
	
	public abstract void onRegister();
	public abstract void onStart();
	public abstract void update(float dt);
	public abstract void onStop();

	public ModInfo getInfo() {
		return info;
	}
}
