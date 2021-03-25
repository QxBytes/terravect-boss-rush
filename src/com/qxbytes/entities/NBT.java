package com.qxbytes.entities;

import java.util.HashMap;
import java.util.Map;

import com.qxbytes.utils.Utils;
/**
 * 
 * @author QxBytes
 *
 */
public class NBT {
	private Map<String,NBT> data = new HashMap<String,NBT>();
	private String name;
	private String value;
	/**
	 * All NBT tags must be in the format Name:Data,Name2:Data2 etc.
	 */
	public NBT() {
	}
	public NBT(NBT copy) {
		NBT newer = Utils.parseNBTRaw(copy.toString());
		data = newer.getData();
	}
	public NBT(String x) {
		data = Utils.parseNBTRaw(x).getData();
	}

	/**
	 * Add creation
	 */
	public NBT(String tag, String value) {
		this.setName(tag);
		this.setValue(value);
	}
	public NBT(String tag, int value) {
		this.setName(tag);
		this.setValue(""+value);
	}
	public NBT(String tag, double value) {
		this.setName(tag);
		this.setValue(""+value);
	}
	public NBT(String tag, boolean value) {
		this.setName(tag);
		this.setValue(""+value);
	}
	public void addTag(NBT tag) {
		data.put(tag.getName(),tag);
	}
	/**
	 * TODO: BROKEN
	 */
	public String toString() {
		String data = "";
		//check root case
		if (name == null || value == null) {
			for (Map.Entry<String, NBT> entry : getData().entrySet())
			{
				data += entry.getKey();
				data += ":";
				data += entry.getValue().getValue();
				data += ",";
			}

//			for (NBT value : this.getData().values()) {
//				data += value.getName();
//				data += ":";
//				data += value.getValue();
//				data += ",";
//			}
		}

		return data;
	}
	/**
	 * @return the data
	 */
	public Map<String,NBT> getData() {
		return data;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		String temp = name;
		temp = temp.toLowerCase();
		this.name = temp;
	}
	/**
	 * Shorthand
	 */
	public String s(String key) { return search(key);}
	public int sI(String key) { return searchInt(key);}
	public double sD(String key) { return searchDouble(key);}
	public boolean sB(String key) { return searchBoolean(key);}

	/**
	 * NBT ROOT SEARCH FUNCTION
	 */
	public String set(String key, String value) {
		this.getData().put(key, new NBT(key,value));

		return value;
	}
	public int set(String key, int value) {
		return Integer.parseInt(set(key,""+value));
	}
	public double set(String key, double value) {
		return Double.parseDouble(set(key,""+value));
	}
	public boolean set(String key, boolean value) {
		return Boolean.parseBoolean(set(key,""+value));
	}
	public double accum(String key, double value) {
		
	//	System.out.println(value + "|" + sD(key)+ "|Accumulated: "+(sD(key)+value));
		this.addTag(new NBT(key,sD(key)+value));
		return value;
	}
	/**
	 * Search stuffs
	 */
	private String search(String key) {
		//System.out.println("Searching for:" + key);
		String x = null;
		try {
			x =  this.getData().get(key).getValue();
		} catch (Exception e) {
			
		}
		return x;
	}
	private int searchInt(String key) {
		String s = this.search(key);
		if (s == null) return 0;
		int x = (int)Double.parseDouble(s);
		return x;
	}
	private double searchDouble(String key) {
		String s = this.search(key);
		if (s == null) return 0;
		double x =  Double.parseDouble(s);
		return x;
	}
	private boolean searchBoolean(String key) {
		String s = this.search(key);
		if (s == null) return false;
		boolean x = Boolean.parseBoolean(s);
		return x;
	}
	/**
	 * DO NOT USE IF ROOT
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	public int getIntValue() {
		return Integer.parseInt(value);
	}
	public double getDoubleValue() {
		return Double.parseDouble(value);
	}
	public boolean getBooleanValue() {
		return Boolean.parseBoolean(value);
	}
	/**
	 * ===>DO NOT USE IF ROOT
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
