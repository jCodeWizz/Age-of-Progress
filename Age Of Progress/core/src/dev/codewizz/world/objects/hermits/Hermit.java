package dev.codewizz.world.objects.hermits;

import java.awt.Polygon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import dev.codewizz.gfx.Animation;
import dev.codewizz.gfx.gui.UIImage;
import dev.codewizz.gfx.gui.UILayer;
import dev.codewizz.gfx.gui.UIText;
import dev.codewizz.gfx.gui.menus.SelectMenu;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Direction;
import dev.codewizz.utils.Utils;
import dev.codewizz.utils.serialization.RCField;
import dev.codewizz.utils.serialization.RCObject;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.Serializable;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.buildings.Building;
import dev.codewizz.world.objects.tasks.ClearInventoryTask;
import dev.codewizz.world.objects.tasks.Task;
import dev.codewizz.world.settlement.Settlement;

public class Hermit extends TaskableObject implements Serializable {

	public static final float walkAnimSpeed = 0.1f;
	
	private Direction dir = Direction.South;

	private Animation taskAnimation;
	private Animation currentAnimation;
	private Sprite currentDirection;
	
	private Settlement s;
	private Building home;
	private Job job;
	private Inventory inventory;
	
	//private float intelligence = 1f;
	//private float strength = 1f;
	//private float speed = 1f;
	//private float willpower = 1f;
	private float sleep = 1f;
	private float healthy = Utils.RANDOM.nextFloat();
	private float body = Utils.RANDOM.nextFloat();
	//private float social = 1f;
	//private float hunger = 1f;
	//private float thirst = 1f;
	
	private float sleepNeed = 10f;
	
	private int age = 101;
	
	private UIText nameText;
	private UIImage jobIcon;
	
	public Hermit(float x, float y) {
		super(x, y);
		this.id = "aop:hermit";
		this.name= Utils.getRandomName();
		this.inventory = new Inventory(5);
		
		this.w = 24;
		this.h = 36;
		this.health = 10f;
		this.maxHealth = 10f;
		
		this.speed = 20f;

		setMaxHealth(0f);
		setSleepNeed();

		this.setJob(new Worker());
		jobIcon = new UIImage("job-icon", ((UILayer.WIDTH / 2) - (146 * UILayer.SCALE) / 2)/2 + 39 * UILayer.SCALE, 0, 30 * UILayer.SCALE, 30 * UILayer.SCALE, this.getJob().getIcon(), 1);
	}
	
	@Override
	public void update(float d) {
		super.update(d);
		
		if(this.currentTask == null && s.getTasks().notEmpty()) {
			
			for(Task task : s.getTasks()) {
				
				if(task.canTake(this)) {
					this.addTask(task, false);
					s.getTasks().removeValue(task, false);
					break;
				}
			}
		}
		
		if(this.currentTask == null && this.getInventory().getItems().size() > 0) {
			this.addTask(new ClearInventoryTask(), true);
		}
		
		if(currentAnimation != null) {
			currentAnimation.tick(d);
		}
		
		if(taskAnimation != null) {
			taskAnimation.tick(d);
		}
		
		if(this.getAgent().moving) {
			dir = Utils.getDirFromVector(vel);	
			currentAnimation = job.animations.get(dir);
		} else {
			currentDirection = job.directions.get(dir);
		}
		
		job.update(d);
	}
	
	

	@Override
	public void render(SpriteBatch b) {
		
		if(this.taskAnimation != null) {
			b.draw(this.taskAnimation.getFrame(), x + this.taskAnimation.getX(), y + this.taskAnimation.getY(), this.taskAnimation.getFrame().getWidth()*0.6f, this.taskAnimation.getFrame().getHeight()*0.6f);
		} else {
			if(this.getAgent().moving) {
				if(currentAnimation != null) {
					b.draw(currentAnimation.getFrame(), x, y, w, h);
				}
			} else {
				if(currentDirection != null) {
					b.draw(currentDirection, x, y, w, h);
				}
			}
		}
		
		job.render(b);
	}
	
	@Override
	public Polygon getHitBox() {
		return new Polygon( new int[] {(int)x+7, (int)x+7, (int)x + 17, (int)x + 17}, 
							new int[] {(int)y + 3, (int)y + 24, (int)y + 24, (int)y + 3}, 4) ;
	}

	@Override
	public void renderUICard(SelectMenu m) {
		super.renderUICard(m);
		
		nameText = (UIText)m.layer.getElement("name-text");
	
		m.elements.add(0, jobIcon);
	}
	
	@Override
	public void updateUICard(SelectMenu m) {
		super.updateUICard(m);
		if(nameText != null) nameText.setText(name + "   ( " + age + " )");
		else nameText = (UIText) m.layer.getElement("name-text");

		jobIcon.setSprite(this.getJob().getIcon());
	}

	public Settlement getSettlement() {
		return s;
	}

	public void setSettlement(Settlement s) {
		this.s = s;
	}

	@Override
	public RCObject save(RCObject object) {
		object.addField(RCField.Float("health", health));
		
		return object;
	}

	@Override
	public void load(RCObject object) {
		this.health = object.findField("health").getFloat();
		Main.inst.world.settlement.addHermit(this);
	}
	
	@Override
	public void setMaxHealth(float maxHealth) {
		float a = this.getMaxHealth();
		
		this.maxHealth = 50f + ((healthy+0.5f) * 20f) + ((body + 0.1f) * 50f);
		
		float d = this.getMaxHealth() / a;
		this.setHealth(this.getHealth() * d);
	}
	
	public void setSleepNeed() {
		this.sleepNeed = sleep * 100f;
	}

	public float getSleepNeed() {
		return sleepNeed;
	}

	public void setSleepNeed(float sleepNeed) {
		this.sleepNeed = sleepNeed;
	}

	public Building getHome() {
		
		if(this.home == null) {
			
			for(GameObject object : s.objects) {
				if(object instanceof Building) {
					Building b = (Building) object;
					if(!b.isFull()) {
						b.owned.add(this);
						this.home = b;
						return b;
					}
				}
			}
			return null;
		} else {
			return home;
		}
	}

	public void setHome(Building home) {
		this.home = home;
	}
	
	
	
	public void setFacing(Direction dir) {
		this.dir = dir;
	}

	public Animation getTaskAnimation() {
		return taskAnimation;
	}

	public void setTaskAnimation(Animation taskAnimation) {
		this.taskAnimation = taskAnimation;
	}
	
	public Job getJob() {
		return this.job;
	}
	
	public void setJob(Job job) {
		this.job = job;
		this.job.setHermit(this);
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
}
