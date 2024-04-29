package dev.codewizz.gfx.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.menus.NotificationMenu;
import dev.codewizz.utils.Assets;

public class UINotification extends UIElement {

	private static int NOT_COUNTER = 0;
	
	private UIImage background;
	private UIImage icon; // SHOULD BE SQUARE ONLY
	private UIText title;
	private UIText description;
	
	public float counter = 5f;
	public NotificationMenu menu;
	
	public UINotification(String title, String description, String icon) {
		this(title, description, Assets.getSprite(icon));
	}

	public UINotification(String title, String description, Sprite sprite) {
		super("noti-" + (++NOT_COUNTER), 0, 0, NotificationMenu.notificationWidth, NotificationMenu.notificationHeight);


		this.title = new UIText("noti-" + NOT_COUNTER + "-title", 0, 0, title, 10);
		this.background = new UIImage("noti-" + NOT_COUNTER + "-background", 0, 0, w, h, "notification");
		this.description = new UIText("noti-" + NOT_COUNTER + "-description", 0, 0, description, 6);
		this.icon = new UIImage("noti-" + NOT_COUNTER + "-icon", 0, 0, 8 * UILayer.SCALE, 8 * UILayer.SCALE, sprite);

	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
		
		title.setX(x + 32 * UILayer.SCALE);
		title.setY(this.y + 22 * UILayer.SCALE);
		
		background.setX(x);
		background.setY(y);
		
		icon.setX(x);
		icon.setY(y);
		
		description.setX(x + 30 * UILayer.SCALE);
		description.setY(this.y + 8 * UILayer.SCALE);
	}

	@Override
	public void render(SpriteBatch b) {
		
		counter -= Gdx.graphics.getDeltaTime();
		
		if(counter <= 0) {
			menu.removeNotification(this);
		}
		
		background.render(b);
		icon.render(b);
		title.render(b);
		description.render(b);
	}
}
