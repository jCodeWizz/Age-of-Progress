package dev.codewizz.utils.serialization;

import dev.codewizz.utils.saving.GameObjectData;
import dev.codewizz.utils.saving.GameObjectDataLoader;

public interface Serializable {
	
	GameObjectData save(GameObjectData object);
	boolean load(GameObjectDataLoader loader, GameObjectData object, boolean success);
}
