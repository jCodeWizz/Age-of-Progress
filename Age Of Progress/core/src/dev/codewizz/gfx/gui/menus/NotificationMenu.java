package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;

import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UINotification;

public class NotificationMenu extends UIMenu {

	private int padding = 5;
	
	public static int notificationHeight = 9 * UILayer.SCALE;
	public static int notificationWidth = 64 * UILayer.SCALE;
	
	public NotificationMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
		
		this.w = notificationWidth;

	}
	
	public void addNotification(UINotification notification) {
		notification.menu = this;
		
		if(elements.size() > 2) {
			elements.remove(0);
		} 

		elements.add(notification);
		layer.elements.add(notification);
		
		updateNotifications();
	}
	
	public void removeNotification(UIElement notification) {
		
		layer.elements.remove(notification);
		elements.remove(notification);
		updateNotifications();
		
	}
	
	public void updateNotifications() {
		h = elements.size() * notificationHeight;
		if(elements.size() > 1) {
			h += (elements.size()-1) * padding;
		}
		
		int yy = 0;
		
		for(int i = 0; i < elements.size(); i++) {
			
			UINotification n = (UINotification) elements.get(i);
			
			n.setPosition(x, y - yy);
			
			yy += padding * UILayer.SCALE + notificationHeight * UILayer.SCALE;
		}
	}

	@Override
	public void setup() {
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, w, h);
	}

	@Override
	public void onOpen() {
		
	}

	@Override
	public void onClose() {

	}
}
