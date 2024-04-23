package dev.codewizz.world.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Utils;
import dev.codewizz.world.Cell;
import dev.codewizz.world.objects.tasks.MoveTask;

public class Herd {

	private final static Random RANDOM = new Random();
	private List<Animal> members = new ArrayList<>();
	private Animal leader;
	private int offset = 2;

	public Herd() {
	}

	public Herd(Animal... animals) {
		for (int i = 0; i < animals.length; i++) {
			members.add(animals[i]);
		}

		this.leader = members.get(RANDOM.nextInt(members.size()));
		this.leader.setName(this.leader.getName() + " Alpha");
		this.offset = this.leader.getWanderDistance();
	}

	public Herd(Animal leader, Animal... animals) {
		for (int i = 0; i < animals.length; i++) {
			members.add(animals[i]);
		}

		this.leader = leader;
		this.leader.setName(this.leader.getName() + " Alpha");
		this.offset = this.leader.getWanderDistance();
	}

	public Cell newPath() {
		Cell cell = Main.inst.world.getCell(leader.getX(), leader.getY());

		int indexX = cell.indexX + Utils.getRandom(-offset, offset);
		int indexY = cell.indexY + Utils.getRandom(-offset, offset);
		
		return Main.inst.world.getCell(indexX * 32, indexY * 16);
	}

	public void attackHerd() {
		for (Animal a : members) {
			a.addTask(new MoveTask(newPath()), false);
		}
	}

	public List<Animal> getMembers() {
		return members;
	}

	public void addMember(Animal animal) {
		members.add(animal);
	}

	public void setLeader(Animal animal) {
		this.leader = animal;
	}

	public void setLeader() {
		this.leader = members.get(RANDOM.nextInt(members.size()));
		this.leader.setName(this.leader.getName() + " Alpha");
		this.offset = this.leader.getWanderDistance();
	}

	public Animal getLeader() {
		return leader;
	}

	public void leaderMoved() {

		for (Animal a : members) {
			if (!a.equals(leader)) {
				a.addTask(new MoveTask(newPath()), false);
			}
		}
	}
	
	public void removeMember(Animal member) {
		this.members.remove(member);
		
		if(member.equals(leader)) {
			setLeader();
		}
	}
	
	public void clear() {
		this.members.clear();
	}
}
