package dev.codewizz.world.items;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Inventory {

	public List<Item> items;
	public int size;

	public Inventory(int size) {
		this.size = size;

		items = new CopyOnWriteArrayList<>();
	}

	public boolean addItem(Item i) {
		for (Item items : items) {
			if (items.getType() == i.getType()) {
				items.setSize(items.getSize() + i.getSize());
				return true;
			}
		}

		if(size == -1 || items.size() < size) {
			items.add(i);
			return true;
		}
		
		return false;
	}
	
	public boolean containsItem(Item i) {
		return containsItem(i, i.getSize());
	}

	public boolean containsItem(Item i, int count) {

		for (Item items : items) {
			if (items.getType() == i.getType()) {
				if (items.getSize() >= count) {
					return true;
				}
			}
		}

		return false;
	}
	
	public boolean removeItem(Item i) {

		if (containsItem(i, i.getSize())) {

			for (Item items : items) {

				if (items.getType() == i.getType()) {

					if (items.getSize() > i.getSize()) {
						items.setSize(items.getSize() - i.getSize());
						return true;
					} else if (items.getSize() == i.getSize()) {
						this.items.remove(items);
						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}
	
	public boolean roomFor(Item i) {
		return (getSizeAvailable() > 0 || containsItem(i, 1));
	}
	
	public boolean isEmpty() {
		return this.items.isEmpty();
	}
	
	public int getSizeAvailable() {
		return size - this.items.size();
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public List<Item> getItems() {
		return this.items;
	}
}
