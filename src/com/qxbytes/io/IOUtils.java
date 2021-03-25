package com.qxbytes.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.qxbytes.entities.NBT;
import com.qxbytes.entities.Player;
import com.qxbytes.grid.Grid;
import com.qxbytes.states.World;
/**
 * 
 * @author QxBytes
 *
 */
public class IOUtils {
	public static String world = "default\\";
	public static String root = "saves\\";

	private static String compressString(String str, File inputfile) throws IOException{
		if (str == null || str.length() == 0) {
			str = "motion-x:-1,ess-earth:1,ess-wind:0,eid:000|01|4,motion-y:1.1,stats-def:15,type:3,ess-limit:9999999,ess-fire:1,ess-water:1,stats-maxhp:79100,stats-hp:79100,eid:000|01|4,d:0,multiplier:4,stats-spd:79,localheight:40.29942593170847,check:1,o:false,s:6,cx:0,stats-atk:42,cy:0,biome:2.325549177018983,x:0,motion-speed:1.7,y:0,";
		}

		BufferedWriter writer = null;
		GZIPOutputStream zip = null;
		try{
			File file =  inputfile;
			zip = new GZIPOutputStream(new FileOutputStream(file));

			writer = new BufferedWriter(new OutputStreamWriter(zip, "UTF-8"));

			writer.append(str);
		}
		finally{           
			if(writer != null){
				writer.close();
			}
			if (zip != null) {
				zip.close();
			}
		}
		return str;
	}
	private static List<String> decompressFile(File inputfile) throws FileNotFoundException, IOException {
		List<String> data = new ArrayList<String>();
		if (inputfile != null && inputfile.exists()) {
			BufferedReader reader = null;
			GZIPInputStream zip = null;
			try{
				File file =  inputfile;
				zip = new GZIPInputStream
						(
								new FileInputStream(file));

				reader = new BufferedReader(new InputStreamReader(zip, "UTF-8"));
				while (reader.ready() == true ) {
					data.add(reader.readLine());
				}

			}
			finally
			{           
				if(reader != null){
					reader.close();

				}
				if (zip != null) {
					zip.close();
				}
			}
		}
		return data;
	}
	private static void saveGrid(World w) {
		String e = w.getG().saveEntities();
		//EXTREME RESOURCE CONSUMPTION:
		String t = w.getG().saveTiles();
		System.err.println("Bytes theoretically taken up by t variable: " + t.length()*8);
		//END EXTREME RESOURCE CONSUMPTION
		System.out.println("Entitydata saved:" + e);

		File en = new File(root + world + w.getRoomx() + "_" + w.getRoomy() + ".entities");
		File ti = new File(root + world + w.getRoomx() + "_" + w.getRoomy() + ".tiles");
		//entities
		try {
			File parent = new File(root + world);
			if (parent.exists() == false) {
				parent.mkdirs();
			}
			if (en.exists() == false) en.createNewFile();


			compressString(e,en);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		//tiles
		try {
			if (ti.exists() == false) ti.createNewFile();
			System.out.println("Meta Written: " + w.getG().getMeta().toString());
			t = w.getG().getMeta().toString() +"\n"+ t;
			compressString(t,ti);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		//Make sure its garbaged
		t = null;
		
		savePlayer(w);
		
		
	}
	private static void loadGrid(World w,int roomx,int roomy) {
		try {
			if (new File(root + world + roomx + "_" + roomy + ".entities").exists() == true &&
					new File(root + world + roomx + "_" + roomy + ".tiles").exists() == true) {

				List<String> entities = new ArrayList<String>();
				/*
				 * Check if file empty!
				 */
				BufferedReader br = new BufferedReader(new FileReader(root + world + roomx + "_" + roomy + ".entities"));     
				if (br.readLine() == null) {
					System.out.println("No errors, and file empty");
				} else {
					entities = decompressFile(new File(root + world + roomx + "_" + roomy + ".entities"));
				}
				br.close();

				/*
				 * Should never be empty!
				 */
				List<String> tiles = decompressFile(new File(root + world + roomx + "_" + roomy + ".tiles"));

				w.setG(new Grid(w,tiles,entities));
			} else {
				if (w.getG() != null)w.getG().getMain().clear();
				System.err.println("Failed to find Room: " + roomx + "," + roomy + ". Generating...");
				w.setG(new Grid(w,roomx,roomy));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		w.setRoomx(roomx);
		w.setRoomy(roomy);
		w.getP().gT().set("room-x", roomx);
		w.getP().gT().set("room-y", roomy);
	}
	///Entrance
	private static void loadWorld(World w) throws IOException {
		//load player

		List<String> data = decompressFile(new File(root + world + "player.p"));
		w.setP(new Player(w,new NBT(data.get(0))));
		System.out.println("Player loaded as: " + data.get(0));
		w.setRoomx(w.getP().gT().sI("room-x"));
		w.setRoomy(w.getP().gT().sI("room-y"));
		//load grid
		loadGrid(w, w.getRoomx(), w.getRoomy());
	}
	public static void savePlayer(World w) {
		prepEnvironment();

		//entities
		try {
			File parent = new File(root + world);
			if (parent.exists() == false) {
				parent.mkdirs();
			}
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		
		String p = w.getP().toString();
		File pl = new File(root + world + "player.p");
		System.out.println("Player saved as: " + p);
		try {
			if (pl.exists() == false) pl.createNewFile();
			compressString(p,pl);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}
	public static void swapGrid(World w, int roomx, int roomy) {
		System.err.println("Swap Phase 1: " + "Free % " + ((double)Runtime.getRuntime().freeMemory()/Runtime.getRuntime().totalMemory()));
		saveGrid(w);
		System.err.println("Swap Phase 2: " + "Free % " + ((double)Runtime.getRuntime().freeMemory()/Runtime.getRuntime().totalMemory()));
		loadGrid(w,roomx,roomy);
		System.err.println("Swap Phase 3: " + "Free % " + ((double)Runtime.getRuntime().freeMemory()/Runtime.getRuntime().totalMemory()));
		Runtime.getRuntime().gc();
		System.err.println("Swap Phase 4: " + "Free % " + ((double)Runtime.getRuntime().freeMemory()/Runtime.getRuntime().totalMemory()));

	}
	//TODO:
	public static boolean load(String name, World w) {

		world = name;

		try {
			loadWorld(w);
		} catch (Exception e) {
			System.err.println("Failed to Load Pre-existing world/player. Starting from scratch...");
			return false;
		}
		return true;
	}

	public static void prepEnvironment() {
		JFileChooser fr = new JFileChooser();
		FileSystemView fw = fr.getFileSystemView();
		System.out.println("Default Directory: " + fw.getDefaultDirectory());
		root = fw.getDefaultDirectory().toString() + "\\" + "TerraVect\\worlds\\";
		
		File f = new File(root);
		boolean x = true;
		if (!f.exists()) {
			x = f.mkdirs();
		}
		System.out.println(x + " " + f);
		
	}
}
