package dev.codewizz.gfx.gui.menus;

import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.gui.UIButton;
import dev.codewizz.gfx.gui.UIElement;
import dev.codewizz.gfx.gui.UIIcon;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIMenu;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.main.Main;
import dev.codewizz.world.objects.hermits.Craftsman;
import dev.codewizz.world.objects.hermits.Farmer;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Job;
import dev.codewizz.world.objects.hermits.Worker;

public class PeopleMenu extends UIMenu {

	public final static int x_padding = 6 * UILayer.SCALE;
	public final static int y_padding = 6 * UILayer.SCALE;
	public final static int height = 16 * UILayer.SCALE;
	public final static int width = 90 * UILayer.SCALE;
	
	private UIImage image;
	
	private List<Card> cards = new CopyOnWriteArrayList<>();
	private List<UIButton> buttons;
	
	public Card selected;
	
	private UIText name;
	private UIText job;
	private UIText task;
	private UIIcon promote;
	private UIImage picture;
	
	private UIButton farmer;
	private UIButton worker;
	private UIButton craftsman;
	
	private boolean promoting = false;
	
	public PeopleMenu(String id, int x, int y, int w, int h, UILayer layer) {
		super(id, x, y, w, h, layer);
		
		this.wantsClick = false;
	}
	
	@Override
	public void render(SpriteBatch b) {
		
		if(selected != null) {
			picture.render(b);
			
			name.setText(selected.hermit.getName() + " (" + (int)selected.hermit.getHealth() + "/" + (int)selected.hermit.getMaxHealth() + ") ");
			name.render(b);
			
			job.setText(selected.hermit.getJob().getJob().toString());
			job.render(b);
			
			if(selected.hermit.getCurrentTask() == null) {
				task.setText("Idle");
			} else {
				task.setText(selected.hermit.getCurrentTask().getName());
			}
			task.render(b);
			
		}
	}

	@Override
	public void setup() {
		buttons = new CopyOnWriteArrayList<>();
		
		elements.add(0, new UIIcon("close-button", x + w*UILayer.SCALE - 2 - 14*UILayer.SCALE,
				y + h*UILayer.SCALE - 15*UILayer.SCALE - 2, 14, 15, "close-icon") {
			@Override
			protected void onDeClick() {
				close();
			}
		});
		
		int startX = x + (w-113) * UILayer.SCALE;
		int endX = x + w * UILayer.SCALE;
		
		int startY = UILayer.HEIGHT/2 + (h * UILayer.SCALE)/2 - 10 * UILayer.SCALE - (endX - startX) - 6*UILayer.SCALE;
		int endY = UILayer.HEIGHT/2 - (h * UILayer.SCALE)/2 + 6 * UILayer.SCALE;
		
		promote = new UIIcon("promote-icon", (startX + endX) / 2 - 22*UILayer.SCALE/2, endY + 6 * UILayer.SCALE, 22, 24, "icon", "icon-pressed", "icon-unavailable", "work-icon") {
			@Override
			protected void onDeClick() {
				if(!promoting) {
					promote();
				} else {
					nopromote();
				}
			}
		};
		elements.add(promote);
		
		picture = new UIImage("picture-image", startX + 2 * UILayer.SCALE, startY, endX - startX - 6*UILayer.SCALE, endX - startX - 6 * UILayer.SCALE, "worker-icon", 1);
	
		name = new UIText("name", startX + 3 * UILayer.SCALE, startY - 3 * UILayer.SCALE, "", UILayer.SCALE * 3);
		job = new UIText("job", startX + 3 * UILayer.SCALE, startY - 13 * UILayer.SCALE, "", UILayer.SCALE * 3);
		task = new UIText("task", startX + 3 * UILayer.SCALE, startY - 26 * UILayer.SCALE, "", UILayer.SCALE * 2);
		
		elements.add(new UIText("settlementMenu-title", x + 4 * UILayer.SCALE, y + h*UILayer.SCALE - 4 * UILayer.SCALE, "Settlement Menu", 10));
		image = new UIImage("settlement-menu", x, y, w, h, "settlement-menu");
		image.setWantsClick(false);
		elements.add(image);
		
		farmer = new UIButton("farmer-button", UILayer.WIDTH/2 - (99/2) * UILayer.SCALE, Gdx.graphics.getHeight()/2 - 18 * UILayer.SCALE, 99, 36, "Farmer") {
			@Override
			protected void onDeClick() {
				changeJob(new Farmer());
			}
		};
		buttons.add(farmer);
		
		worker = new UIButton("worker-button", UILayer.WIDTH/2 - (99/2) * UILayer.SCALE, Gdx.graphics.getHeight()/2 - 18 * UILayer.SCALE + 40 * UILayer.SCALE, 99, 36, "Worker") {
			@Override
			protected void onDeClick() {
				changeJob(new Worker());
			}
		};
		buttons.add(worker);
		
		craftsman = new UIButton("craftsman-button", UILayer.WIDTH/2 - (99/2) * UILayer.SCALE, Gdx.graphics.getHeight()/2 - 18 * UILayer.SCALE + 80 * UILayer.SCALE, 99, 36, "Craftsman") {
			@Override
			protected void onDeClick() {
				changeJob(new Craftsman());
			}
		};
		buttons.add(craftsman);
	}
	
	public void changeJob(Job job) {
		selected.hermit.setJob(job);
		picture.setSprite(selected.hermit.getJob().getIcon());
		selected.icon.setSprite(selected.hermit.getJob().getIcon());
	}
	
	public void promote() {
		promoting = true;
		
		layer.elements.addAll(buttons);
		layer.elements.removeAll(cards);
	}
	
	public void nopromote() {
		promoting = false;
		
		layer.elements.removeAll(buttons);
		layer.elements.addAll(cards);
	}
	
	public void selectHermit() {
		picture.setSprite(selected.hermit.getJob().getIcon());
		
		promote.enable();
	}

	@Override
	public java.awt.Rectangle getBounds() {
		return new java.awt.Rectangle(6 * UILayer.SCALE, Gdx.graphics.getHeight() - (331 * UILayer.SCALE),
				160 * UILayer.SCALE, 325 * UILayer.SCALE);
	}

	@Override
	public void onOpen() {
		cards.clear();
		
		for(int i = 0; i < Main.inst.world.settlement.members.size(); i++) {
			Card card = new Card(Main.inst.world.settlement.members.get(i), x + x_padding + ((i % 4) * (width + x_padding)), y + h*UILayer.SCALE - 150 - (((int)(i / 4)) * (height + y_padding)), this);
			cards.add(card);
			layer.elements.add(card);
		}
	}

	@Override
	public void onClose() {
		selected = null;
		
		promote.disable();
		if(promoting) {
			promoting = false;
			layer.elements.removeAll(buttons);
		} else {
			layer.elements.removeAll(cards);
		}
	}
}

class Card extends UIElement {
	
	public Hermit hermit;
	
	private PeopleMenu menu;
	
	public UIImage icon;
	public UIText nameText;
	public UIText taskText;
	
	public Card(Hermit hermit, int x, int y, PeopleMenu menu) {
		super("people-menu-card", x, y, PeopleMenu.width, PeopleMenu.height);

		this.hermit = hermit;
		this.menu = menu;
		
		icon = new UIImage("settlementMenu-item", x + 10 * UILayer.SCALE, y, 24, 24, hermit.getJob().getIcon());
		taskText = new UIText("settlementMenu-item", x + 6 + (int)hermit.getJob().getIcon().getWidth() * UILayer.SCALE, y + 15, "", 8);
		nameText = new UIText("settlementMenu-item", x + 6 + (int)hermit.getJob().getIcon().getWidth() * UILayer.SCALE, y + 45, "" + hermit.getName(), 10);
		
		icon.setWantsClick(false);
		taskText.setWantsClick(false);
		nameText.setWantsClick(false);
		this.wantsClick = true;
		
		refresh();
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, UILayer.HEIGHT - y - h, w, h);
	}
	
	private void refresh() {
		nameText.setText(hermit.getName());
		
		String task = "Idle";
		
		if(hermit.getCurrentTask() != null) {
			task = hermit.getCurrentTask().getName();
		}
		
		taskText.setText(task);
	}
	
	@Override
	protected void onDeClick() {
		hermit.setSelected(true);
		menu.selected = this;
		
		menu.selectHermit();
	}

	@Override
	public void render(SpriteBatch b) {
		refresh();
		
		icon.render(b);
		nameText.render(b);
		taskText.render(b);
	}
}