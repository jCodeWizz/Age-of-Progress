package mod.testworld.main;

import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.modding.annotations.Priorities;
import dev.codewizz.modding.annotations.Priority;
import dev.codewizz.modding.events.CreateWorldEvent;
import dev.codewizz.modding.events.EventListener;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.World;

public class OnCreateWorld implements EventListener {

	@EventCall
	@Priority(priority = Priorities.SUPER_HIGH)
	public void onCreateWorld(CreateWorldEvent e) {
		World w = e.getWorld();
		
		w.chunks.clear();
		w.chunkTree.clear();
		
		for(int i = 0; i < Main.SIZE; i++) {
			for(int j = 0; j < Main.SIZE; j++) {
				Chunk c = w.addChunk(i, j);
				c.markGenerated();
			}
		}
	}
}
