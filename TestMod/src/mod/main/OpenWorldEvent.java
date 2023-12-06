package mod.main;

import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.modding.annotations.Priorities;
import dev.codewizz.modding.annotations.Priority;
import dev.codewizz.modding.events.CreateWorldEvent;
import dev.codewizz.modding.events.EventListener;
import dev.codewizz.modding.events.LoadWorldEvent;
import dev.codewizz.utils.Logger;

public class OpenWorldEvent implements EventListener {
	
	@Priority(priority = Priorities.SUPER_LOW)
	@EventCall
	public void onWorldCreate(CreateWorldEvent event) {
		openWorld();
	}
	
	@Priority(priority = Priorities.HIGH)
	@EventCall
	public void onWorldLoad(LoadWorldEvent event) {
		openWorld();
	}
	
	private void openWorld() {
		Logger.log("Opened or created world!");
	}
	
}
