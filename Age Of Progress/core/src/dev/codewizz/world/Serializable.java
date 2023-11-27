package dev.codewizz.world;

import dev.codewizz.utils.serialization.RCObject;

public interface Serializable {
	
	public RCObject save(RCObject object);
	public void load(RCObject object);
	
}
