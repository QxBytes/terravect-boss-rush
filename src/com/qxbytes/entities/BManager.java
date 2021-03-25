package com.qxbytes.entities;

import java.util.ArrayList;
import java.util.List;

import com.qxbytes.spells.Spell;
/**
 * 
 * @author QxBytes
 *
 */
public class BManager {
	private int currentgroup = 0;
	private List<Group> bullets = new ArrayList<Group>();
	private List<Group> yourbullets = new ArrayList<Group>();
	public void clear() {
		bullets.clear();
		yourbullets.clear();
	}
	public void addYourbullet(Entity b) {
		if (yourbullets.isEmpty()) yourbullets.add(new Group());
		yourbullets.get(0).add(b);
	}
	public void add(Entity b) {
		if (bullets.isEmpty()) addGroup();
		while (bullets.size() <= currentgroup) {
			addGroup();
		}
		bullets.get(currentgroup).add(b);
	}
	public void add(Entity b, int group) {
		bullets.get(group).add(b);
	}
	public void remove() {
		bullets.get(currentgroup).remove(0);
	}
	public void remove(int group) {
		bullets.get(group).remove(0);
	}

	public void addGroup() {
		bullets.add(new Group());
		currentgroup = bullets.size()-1;
	}
	public void addGroup(Group b) {
		bullets.add(new Group());
		currentgroup = bullets.size()-1;
	}
	public void removeGroup() {
		bullets.remove(bullets.size()-1);
		currentgroup = bullets.size()-1;
	}
	public void clearGroup() {
		bullets.get(currentgroup).clear();
	}
	/**
	 * Current group will be set to 0
	 */
	public void removeEmptyGroups() {
		for (int i = bullets.size()-1 ; i >= 0 ; i--) {
			if (bullets.get(i).isEmpty()) {
				bullets.remove(i);
			}
		}
		this.currentgroup = 0;
	}
	public void cleanUp() {

		for (int i = bullets.size() - 1 ; i >= 0 ; i--) {
			for (int k = bullets.get(i).size()-1 ; k >= 0 ; k--) {
				if (bullets.get(i).get(k).isRender() == false) {
					bullets.get(i).remove(k);
					//Screen.log.addMessage("Cleaning... List Size: " + bullets.get(i).size());
				}
			}
		}
		for (int i = yourbullets.size() - 1 ; i >= 0 ; i--) {
			for (int k = yourbullets.get(i).size()-1 ; k >= 0 ; k--) {
				if (yourbullets.get(i).get(k).isRender() == false) {
					yourbullets.get(i).remove(k);
					//Screen.log.addMessage("Cleaning Yours... List Size: " + yourbullets.get(i).size());
				}
			}
		}
		System.out.println(Runtime.getRuntime().freeMemory() + "/" +  Runtime.getRuntime().totalMemory());
		if (Runtime.getRuntime().freeMemory() / (double)Runtime.getRuntime().totalMemory() < .10) {
			Runtime.getRuntime().gc();
		}
	}
	public void setCurrentGroup(int group) {
		this.currentgroup = group;
	}

	public void applySpell(Spell s) {
		s.doSpell(bullets.get(currentgroup));
	}
	public void applySpell(Spell s, int group) {
		s.doSpell(bullets.get(group));
	}

	public List<Group> getYourbullets() {
		return yourbullets;
	}

	public int getCurrentgroup() {
		return currentgroup;
	}
	public Group getGroup() {
		if (bullets.isEmpty()) addGroup();
		while (bullets.size() <= currentgroup) {
			addGroup();
		}
		return bullets.get(currentgroup);
	}
	public List<Group> getBullets() {
		return bullets;
	}

}
