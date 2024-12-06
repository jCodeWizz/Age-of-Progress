package dev.codewizz.world.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Inventory {

	public HashMap<String, Item> items;
	public int size;

	public Inventory(int size) {
		this.size = size;

		items = new HashMap<>();
	}

	public boolean addItem(Item i) {
		if (items.containsKey(i.getType().getId())) {
			items.get(i.getType().getId()).size(items.get(i.getType().getId()).getSize() + i.getSize());
			return true;
		}

		if(size == -1 || items.size() < size) {
			items.put(i.getType().getId(), i);
			return true;
		}
		
		return false;
	}
	
	public boolean containsItem(Item i) {
		return containsItem(i, i.getSize());
	}

	public boolean containsItem(Item i, int count) {

		if (items.containsKey(i.getType().getId())) {
            return items.get(i.getType().getId()).getSize() >= count;
        }

		return false;
	}
	
	public boolean removeItem(Item i) {
		if (containsItem(i, i.getSize())) {
			Item item = items.get(i.getType().getId());

			if (item.getSize() > i.getSize()) {
				item.size(item.getSize() - i.getSize());
				return true;
			} else if (item.getSize() == i.getSize()) {
				this.items.remove(item.getType().getId());
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public int getSizeOf(ItemType type) {

		if (items.containsKey(type.getId())) {
			return items.get(type.getId()).getSize();
		}

		return 0;
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

	public List<Item> itemList() {
		return new ArrayList<>(items.values());
	}
	
	public HashMap<String, Item> getItems() {
		return this.items;
	}

	@Override
	public String toString() {
		return "Inventory{" +
				"items=" + items +
				", size=" + size +
				'}';
	}
}
