package dev.codewizz.world;

import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;

public interface Serializable {
	
	public GameObjectData save(GameObjectData object);
	public void load(GameObjectData object);
	public boolean loadCheck(GameObjectDataLoader loader, boolean ready);
}
