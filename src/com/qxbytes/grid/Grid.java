package com.qxbytes.grid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qxbytes.Game;
import com.qxbytes.SColor;
import com.qxbytes.Screen;
import com.qxbytes.audio.AChannel;
import com.qxbytes.entities.Creature;
import com.qxbytes.entities.Entity;
import com.qxbytes.entities.Group;
import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.rss.ImageUtils;
import com.qxbytes.states.ExpositionCard;
import com.qxbytes.states.World;
import com.qxbytes.story.Story;
import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class Grid {
	public static final int TILESIZE = 12;
	public static final double FEATURE_SIZE = 12;
	private Tile[][] t = new Tile[Game.WIDTH/TILESIZE][Game.HEIGHT/TILESIZE];
	private List<Group> main = new ArrayList<Group>();
	private NBT meta = new NBT("perm-build:true");
	private World w;
	private GHUD ghud;
	private Random r = new Random();

	//default
	public Grid (World w, int roomsoffx, int roomsoffy) {
		int initial = 0;
		ghud = new GHUD(w,this);
		//biome

		meta.set("perm-build", true);

		double height = OpenSimplexNoise.generateNoise(roomsoffx, roomsoffy)*256;
		meta.addTag(new NBT("height",height));

		meta.addTag(new NBT("biome",height/90));//0=water,1=earth,2=fire

		if (roomsoffx % 3 == 0 && roomsoffy % 3 == 0) meta.addTag(new NBT("mystery",true));
		if (Utils.distanceFrom(5, 5, roomsoffx, roomsoffy) >= 50 && Utils.distanceFrom(5, 5, roomsoffx, roomsoffy) < 52) {
			meta.set("biome", 2.3);
			meta.set("height", 240);
			height = 240;
		}
		double[][] values = OpenSimplexNoise.generateNoise(Game.WIDTH/TILESIZE, Game.HEIGHT/TILESIZE, FEATURE_SIZE,roomsoffx, roomsoffy);
		for (int row = 0 ; row < t.length ; row++) {
			for (int col = 0 ; col < t[row].length ; col++) {
				//normal
				t[row][col] = new Tile(w,row,col,TILESIZE,(int)(values[row][col]*256),((int)height)/90);
				if (roomsoffx % 3 == 0 && roomsoffy % 3 == 0) {
					Color c = Color.getHSBColor((float)((height/256.0)), 1.0f, (float)(values[row][col]));
					t[row][col].getEss().setFire(c.getRed());
					t[row][col].getEss().setEarth(c.getGreen());
					t[row][col].getEss().setWater(c.getBlue());
				}
				//bonus
				int amt = (int)(1/(Utils.distanceFrom(5, 5, roomsoffx, roomsoffy)+1)*200-70);
				t[row][col].getEss().addEarth(amt);
				t[row][col].getEss().addFire(amt);
				t[row][col].getEss().addWater(amt);
				initial += t[row][col].getEss().total();
				t[row][col].getTags().set("l", 0);

				if (roomsoffx == 5 && roomsoffy == 5) {
					//t[row][col].getTags().set("flag-solid", false);
					t[row][col].getTags().set("l", 0);
					//meta.addTag(new NBT("flag-spawn",true));
				}
			}
		}
		this.getMeta().set("initialchange", initial);
		this.setW(w);

		main.add(new Group()); 



		w.getP().getTag().accum("stat-rooms", 1);




	}

	//Set world grid to this grid.
	//Previous grid is destroyed
	//reading/writing IO constructor
	public Grid (World w, List<String> tile, List<String> entity) {
		ghud = new GHUD(w,this);
		setMeta(new NBT(tile.get(0)));
		//Utils.dumpData(tile);
		System.out.println("META READ: " + getMeta());
		System.out.println(tile.size());
		for (int x = 0 ; x < (Game.WIDTH/Grid.TILESIZE ); x++) {
			for (int y= 0 ; y < (Game.HEIGHT/Grid.TILESIZE) ; y++) {
				setTile(x, y, new Tile(w,new NBT(tile.get((x * ((Game.HEIGHT/Grid.TILESIZE)) + y)+1 ))));
			}
		}

		Group g = new Group();
		for (int i = 0 ; i < entity.size() ; i++) {
			g.add(new Creature(w,new NBT(entity.get(i))));
		}

		this.main.add(g);
		System.out.println("WORLD" + w);
		this.setW(w);


	}
	public void playMusic() {
		int track = 9;
		if (this.getMeta().sI("biome") == 0) {
			track = 9;
			//AChannel.playTrack(9, true);
		} else if (this.getMeta().sI("biome") == 1) {
			track = 7;
			//AChannel.playTrack(7, true);
		} else if (this.getMeta().sI("biome") == 2) {
			track = 8;
			//AChannel.playTrack(8, true);
		}
		if (this.getMeta().sB("mystery")) {
			track = 12;
			//AChannel.playTrack(12, true);
		}
		double percent = Utils.percent(this.getMeta().sI("netchange")+this.getMeta().sI("initialchange"), Game.TILESX*Game.TILESY*765);
		if (percent < 5 || this.getMeta().sB("corrupted")) {
			track = 5;
			//AChannel.playTrack(1, true);
			this.getMeta().set("corrupted", true);
		}
		AChannel.playTrack(track, true);

	}
	public void reveal(int x, int y, double r) {
		for (int i = 0 ; i < Game.TILESX ; i++) {
			for (int k = 0 ; k < Game.TILESY ; k++) {
				if (Utils.possibleDistance(x, y, i, k, r)) {
					if (Utils.withinDistance(x, y, i, k, r)) {
						if (getTile(i,k).getTags().sI("l")-5 < 0) {
							this.getTile(i, k).getTags().set("l", 0);
						} else {
							this.getTile(i, k).getTags().accum("l", -5);
						}
					} 
				}
			}
		}
		Screen.log.addMessage("REVEAL:" + x + "," + y,20);
	}
	public List<Tile> inrange(int x, int y, double r) {
		List<Tile> data = new ArrayList<Tile>();
		for (int i = 0 ; i < Game.TILESX ; i++) {
			for (int k = 0 ; k < Game.TILESY ; k++) {
				if (Utils.possibleDistance(x, y, i, k, r)) {
					if (Utils.withinDistance(x, y, i, k, r)) {
						data.add(this.getTile(i, k));
						//	this.getTile(i, k).getEss().setFire(255);
					} 
				}
			}
		}

		return data;
	}
	public String saveTiles() {
		String data= "";
		for (int x = 0 ; x < t.length ; x++) {
			for (int y = 0 ; y < t[x].length ; y++) {
				
				data += t[x][y].toString() + "\n";
				//System.out.println(t[x][y].toString());
			}
			System.err.println("Save Tiles Phase " + x + " " + Runtime.getRuntime().totalMemory());
		}
		return data;
	}
	public String saveEntities() {
		String data = "";

		for (Group g : main) {
			for (Entity e : g) {
				data += e.toString() + "\n";
			}
		}
		return data;
	}
	public Tile getTile(int x, int y) {
		if (x > Game.WIDTH/TILESIZE-1 || y > Game.HEIGHT/TILESIZE-1 || x < 0 || y < 0) {

			return new Tile(getW());
		}
		return t[x][y];
	}
	public void setTile(int x, int y, Tile t) {
		if (x > Game.WIDTH/TILESIZE || y > Game.HEIGHT/TILESIZE) {
			System.out.println("[WARNING]: Cannot set tile out of bounds");
			return;
		}
		this.t[x][y] = t;
	}
	public void update() {
		playMusic();
		Summoner.summon(getW(), this);
		//TEMP: MOVE TO NEW METHOD
		//TEMP
		for (Tile[] x : t) {
			for (Tile y : x) {
				y.update();
			}
		}
		//perm behavior omg! so bad all this code
		for (Group group : main) {
			for (int i = group.size()-1 ; i >= 0 ; i--) {
				group.get(i).update();
				if (group.get(i).getLifetime() % 10 == 0) {
					if (Utils.distanceFrom(getW().getP().getPos().getCenterX(), getW().getP().getPos().getCenterY(), group.get(i).getPos().getCenterX(), group.get(i).getPos().getCenterY()) <= (group.get(i).getTag().sI("multiplier")+1)*50) {

						group.get(i).getM().goTo(getW().getP().getPos().getCenterX(),getW().getP().getPos().getCenterY());


						int result = r.nextInt(7200);
						if (result < 72 * (group.get(i).getTag().sI("multiplier")+1)) {
							List<Tile> tiles = this.inrange((int)group.get(i).getPos().getCx(), (int)group.get(i).getPos().getCy(), 3.0);
							//System.err.println("Breach OK");
							for (Tile t : tiles) {
								t.getTags().set("flag-solid", false);
							}
						}

					} else {
						int result = r.nextInt(7200);
						if (result < 360) {
							group.get(i).getM().setAngle(result);

						} else if (result < 720) {
							group.get(i).getM().reset();
						}
						if (group.get(i).getM().getX() == 0 && group.get(i).getM().getY() == 0 && group.get(i).getLifetime() % 50 == 0) {
							List<Tile> tiles = this.inrange((int)group.get(i).getPos().getCx(), (int)group.get(i).getPos().getCy(), 2.0);
							int mult = group.get(i).getTag().sI("multiplier")+1;
							if (group.get(i).getTag().sI("type") == 1) {
								for (Tile t : tiles) {
									t.getEss().addWater(-1*mult);
								}
							}
							if (group.get(i).getTag().sI("type") == 2) {
								for (Tile t : tiles) {
									t.getEss().addEarth(-1*mult);
								}
							}
							if (group.get(i).getTag().sI("type") == 3) {
								for (Tile t : tiles) {
									t.getEss().addFire(-1*mult);
								}
							}
						}
					}

					if (!group.get(i).isRender()) {
						group.remove(i);
						continue;
					}
				}

			}

		}

		Screen.log.addMessage("Enemies:" + main.get(0).size(),20);

		//MOVE THIS SOMEWHERE ELSE!
		Tile ranged = this.getPressedTile();
		if (ranged != null && Utils.withinDistance(ranged.getPos().getCx(), ranged.getPos().getCy(), this.getW().getP().getPos().getCx(), this.getW().getP().getPos().getCy(), Player.reach*2) && this.getMeta().sB("flag-spawn") == false) {


			List<Tile> x = this.inrange((int)ranged.getPos().getCx(),(int) ranged.getPos().getCy(), Player.brush);
			int mode = Math.abs(this.getW().getP().getMode()%4);
			int mo = 0;
			for (Tile tile : x) {
				if (getW().getMouse().getButton() == 3) {
					//						if (this.getW().getKey().isPressed(16)){
					//							tile.getTags().set("flag-solid", true);
					//						} else {
					if (this.getMeta().sB("perm-build")) {

						if (mode == 0) {
							mo += -Utils.moveEssenceFireFrom(tile, getW().getP(), 5);
						} else if (mode == 1) {
							mo += -Utils.moveEssenceEarthFrom(tile, getW().getP(), 5);
						} else if (mode == 2) {
							mo += -Utils.moveEssenceWaterFrom(tile, getW().getP(), 5);
						}
					}
					//						}
				} 

				if (mode == 3 && this.getMeta().sB("perm-build")) {
					mo += Utils.penTool(getW().getP(), tile, this.getW().getP().c);
				}

				if (!this.enityNear(tile.getPos().getCx(), tile.getPos().getCy()) && !this.playerNear(tile.getPos().getCx(), tile.getPos().getCy())){

					if (getW().getMouse().getButton() == 1){
						//					if (this.getW().getKey().isPressed(16)){
						//						tile.getTags().set("flag-solid", false);
						//					} else {
						if (this.getMeta().sB("perm-build")) {
							if (mode == 0) {
								mo +=Utils.moveEssenceFireFrom(getW().getP(),tile, 5);
							} else if (mode == 1) {
								mo +=Utils.moveEssenceEarthFrom(getW().getP(),tile, 5);
							} else if (mode == 2) {
								mo +=Utils.moveEssenceWaterFrom(getW().getP(),tile, 5);
							}
						}
						//					}
					}

				}
				if (tile.getEss().getFire() > 200 || tile.getEss().getEarth() > 200 || tile.getEss().getWater() > 200) {
					tile.getTags().set("flag-solid", true);
				} else {
					tile.getTags().set("flag-solid", false);
				}
			}
			//System.out.println("NET CHANGE IN WORLD: " + mo);
			this.getMeta().accum("netchange", mo);
		}
		if (this.getMeta().sB("flag-loreexposed") == false) {
			if (w.getP().getTag().sI("stat-rooms") < 11) {
				((ExpositionCard)Screen.states.get(7)).addCard(Story.LINES[w.getP().getTag().sI("stat-rooms")-1],SColor.DARK_GRAY);
			} else {
				((ExpositionCard)Screen.states.get(7)).addCard(Story.LINES[w.getP().getTag().sI("stat-rooms")-1],SColor.BLUE);
			}
			this.getMeta().set("flag-loreexposed", true);
		}
	}
	public void render(Graphics g) {
		for (Tile[] x : t) {
			for (Tile y : x) {
				y.render(g);
			}
		}

		//perm
		for (Group group : main) {
			for (int i = 0 ; i < group.size() ; i++) {
				group.get(i).render(g);

			}
		}
		g.setColor(Color.CYAN);
		Utils.drawBar(g, Game.WIDTH-4, 0, this.getMeta().sD("height"), 255, Game.HEIGHT, false);
		Screen.log.addMessage(this.getMeta().sI("height")+"",10);
		ghud.render(g);
		if (this.getW().getKey().isPressed(32)) {
			g.drawImage(ImageUtils.help, 0, 0, null);
		}
	}
	public Tile getMouseTile() {
		return getTile((int)(getW().getMouse().getX()/Grid.TILESIZE),(int)(getW().getMouse().getY()/Grid.TILESIZE));
	}
	public Tile getPressedTile() {
		if (!getW().getMouse().isPressed()) return new Tile(this.getW());
		return getTile((int)(getW().getMouse().getX()/Grid.TILESIZE),(int)(getW().getMouse().getY()/Grid.TILESIZE));
	}
	/**
	 * @return the main
	 */
	public List<Group> getMain() {
		return main;
	}

	/**
	 * @param main the main to set
	 */
	public void setMain(List<Group> main) {
		this.main = main;
	}
	public NBT getMeta() {
		return meta;
	}
	public void setMeta(NBT meta) {
		this.meta = meta;
	}
	public boolean areaClear(int cx, int cy) {
		for (int x = cx-1 ; x < cx+1 ; x++) {
			for (int y = cy-1 ; y < cy+1 ; y++) {
				if (this.getTile(x, y).getTags().sB("flag-solid") == true) {
					return false;
				}
			}
		}
		return true;
	}
	public boolean playerNear(int cx, int cy) {
		for (int x = cx-2 ; x < cx+2 ; x++) {
			for (int y = cy-2 ; y < cy+2 ; y++) {
				if (this.getW().getP().getPos().getCx() == x && this.getW().getP().getPos().getCy() == y) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean enityNear(int cx, int cy) {
		for (Group xx : main) {
			for (Entity yy : xx) {
				for (int x = cx-2 ; x < cx+2 ; x++) {
					for (int y = cy-2 ; y < cy+2 ; y++) {
						if (yy.getPos().getCx() == x && yy.getPos().getCy() == y) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * State of the entity in its future position. If colliding, then will return true and undo collision.
	 */
	//	public static void checkCollision(Entity e, World w) {
	//		int cx = e.gT().sI("cx");
	//		int cy = e.gT().sI("cy");
	//		
	//		for (int x = cx-1 ; x < cx+2 ; x++) {
	//			for (int y = cy-1 ; y < cy+2 ; y++) {
	//				if (w.getG().getTile(x, y).getTags().sI("l") > 200) {
	//					
	//					System.out.println(cx + "," + cy);
	//					do {
	//						
	//						e.getM().undo();
	//					
	//					} while (isCollision(e,w));
	//
	//					return;
	//				}
	//			}
	//		}
	//	}
	//	public static boolean isCollision(Entity e, World w) {
	//		int cx = e.gT().sI("cx");
	//		int cy = e.gT().sI("cy");
	//		
	//		for (int x = cx-1 ; x < cx+2 ; x++) {
	//			for (int y = cy-1 ; y < cy+2 ; y++) {
	//				if (w.getG().getTile(x, y).getTags().sI("l") > 150) {
	//					
	//					return true;
	//
	//					
	//				}
	//			}
	//		}
	//		return false;
	//	}
	public World getW() {
		return w;
	}
	public void setW(World w) {
		this.w = w;
	}
}
