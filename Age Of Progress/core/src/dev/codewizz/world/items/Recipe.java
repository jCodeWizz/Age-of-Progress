package dev.codewizz.world.items;

public enum Recipe {

	Planks(new Item(ItemType.Planks, 5), new Item(ItemType.Wood, 1));
	
	Item result;
	Item[] costs;
	
	Recipe(Item result, Item... costs) {
		this.result = result;
		this.costs = costs;
	}
	
	public Item getResult() {
		return result;
	}
	
	public Item[] getCosts() {
		return costs;
	}
}
