package mod.testworld.main;

import dev.codewizz.modding.annotations.EventCall;
import dev.codewizz.modding.annotations.Priorities;
import dev.codewizz.modding.annotations.Priority;
import dev.codewizz.modding.events.AddObjectEvent;
import dev.codewizz.modding.events.CreateWorldEvent;
import dev.codewizz.modding.events.EventListener;
import dev.codewizz.world.Chunk;
import dev.codewizz.world.World;
import dev.codewizz.world.tiles.DirtTile;

public class OnCreateWorld implements EventListener {

	@EventCall
	@Priority(priority = Priorities.SUPER_HIGH)
	public void onCreateWorld(CreateWorldEvent e) {
		World w = e.getWorld();
		
		w.showInfoSartMenu = false;
		
		w.chunks.clear();
		w.chunkTree.clear();
		
		for(int i = 0; i < Main.SIZE; i++) {
			for(int j = 0; j < Main.SIZE; j++) {
				Chunk c = w.addChunk(i, j);
				c.init();
				c.markGenerated();
				
				for(int ii = 0; ii < c.getGrid().length; ii++) {
					for(int jj = 0; jj < c.getGrid().length; jj++) {
						if(ii == 0 || jj == 0) {
							c.getGrid()[ii][jj].setTile(new DirtTile());
						}
					}
				}
				
			}
		}
	}
	
	@EventCall
	@Priority(priority = Priorities.SUPER_HIGH)
	public void onAddObject(AddObjectEvent e) {
		if(e.getGameObject().getId().equals("aop:cow")) {
			e.cancel();
		}
	}
}
