package dev.codewizz.world.objects.tasks;

import dev.codewizz.utils.saving.TaskData;
import dev.codewizz.utils.saving.TaskDataLoader;
import dev.codewizz.utils.serialization.ByteUtils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import dev.codewizz.world.objects.TaskableObject;
import dev.codewizz.world.objects.hermits.Hermit;
import dev.codewizz.world.objects.hermits.Jobs;

public abstract class Task {
	
	protected List<Jobs> jobs = new CopyOnWriteArrayList<>();
	
	protected boolean tasking = false;
	protected boolean started = false;
	protected boolean shouldRestart = true;
	
	public Task(Jobs... jobs) {
		if(jobs != null) {
			for(int i = 0; i < jobs.length; i++) {
				this.jobs.add(jobs[i]);
			}
		}
	}
	
	public Task() {
		
	}

	public void load(TaskDataLoader loader, TaskData taskData) {
		byte[] data = taskData.take();

		byte b = data[0];

		tasking = ByteUtils.toBoolean(b, 0);
		started = ByteUtils.toBoolean(b, 1);
		shouldRestart = ByteUtils.toBoolean(b, 2);
	}

	public TaskData save(TaskData data) {
		byte b = 0;
		b = ByteUtils.toByte(b, tasking, 0);
		b = ByteUtils.toByte(b, started, 1);
		b = ByteUtils.toByte(b, shouldRestart, 2);

		data.addByte(b);


		//todo fix job saving, maybe rewriting job system here.

		data.end();
		return data;
	}
	
	public void addJob(Jobs job) {
		jobs.add(job);
	}
	
	// called when task is finished
	public abstract void finish();
	
	// called when task is stopped without it finishing
	public abstract void stop();
	
	// called when task is started
	public abstract void start(TaskableObject object);
	
	// called when cell of task is reached
	public abstract void reach();
	
	// called when more import task should be done first, and this task should be restarted later
	public abstract void reset();
	
	// called every frame
	public abstract void update(float d);
	
	public abstract String getName();
	
	public boolean canTake(Hermit hermit) {
		return (jobs.contains(hermit.getJob().getJob()) || jobs.isEmpty());
	}
	
	public boolean isTasking() {
		return tasking;
	}

	public void setTasking(boolean tasking) {
		this.tasking = tasking;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public boolean shouldRestart() {
		return shouldRestart;
	}
	
}
