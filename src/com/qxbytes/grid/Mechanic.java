package com.qxbytes.grid;

import java.awt.Graphics;

import com.qxbytes.states.World;

public interface Mechanic {
	public void render(Graphics g);
	public void update(World w, Tile t);
}
