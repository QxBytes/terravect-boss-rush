package com.qxbytes.entities;
/**
 * 
 * @author QxBytes
 *
 */
public class Motion implements Control {
	private double speed;
	//of the entity
	private Entity e;
	//Horizontal Component
	private double x;
	//Vertical Component
	private double y;
	private double radians;
	/**
	 * Degrees 0 < x < 360
	 * Speed = Multiplication Factor
	 * This is a component vector basically.
	 * 180 = LEFT
	 * 0 = RIGHT
	 * 90 = DOWN
	 * 270 = UP
	 */
	public Motion(int degrees, double speed) {
		double radians = (degrees * (Math.PI/180));
		
		this.setX(Math.cos(radians) * speed);
		this.setY(Math.sin(radians) * speed);		
		
		this.speed = speed;
	}
	public Motion(Motion m) {
		this.setX(m.getX());
		this.setY(m.getY());
		
		this.speed = 1;
	}
	public Motion(NBT tag) {
		initComponent(tag);
	}
	
	public void netMove(int x,int y) {
		if (x == 0 ){ this.setX(0); this.setY(y*speed);return;}
		double deltax = x;
		double deltay = y;
		double radians = Math.atan(deltay/deltax);
		if (deltax < 0 ) {
			radians = radians + Math.PI;
		}
		this.setX(Math.cos(radians) * this.speed);
		this.setY(Math.sin(radians) * this.speed);
	}
	public void reset() {
		this.setX(0);
		this.setY(0);
	}
	public void goTo(double x, double y) {
		if (e == null) {
			return;
		}
		double deltax = x - e.getPos().getX();
		double deltay = y - e.getPos().getY();
		double radians = Math.atan(deltay/deltax);
		if (deltax < 0 ) {
			radians = radians + Math.PI;
		}
		this.setX(Math.cos(radians) * speed);
		this.setY(Math.sin(radians) * speed);
	}
	//thats it
	public void setAngle(int degrees) {
		double radians = (degrees * (Math.PI/180));
		
		this.setX(Math.cos(radians) * speed);
		this.setY(Math.sin(radians) * speed);	
		
		this.radians = Math.toRadians(degrees);
		
	}
	public int angleAt(double x, double y) {
		if (e == null) {
			return 0;
		}
		double deltax = x - e.getPos().getX();
		double deltay = y - e.getPos().getY();
		double radians = Math.atan(deltay/deltax);
		if (deltax < 0 ) {
			radians = radians + Math.PI;
		}
		return (int)Math.toDegrees(radians);
	}
	public void goFrom(double x, double y) {
		goTo(x,y);
		this.setX(-this.x);
		//TODO: Error?
		this.setY(-this.y);
	}
	public void updateRadians() {
		double radians = Math.atan(this.y/this.x);
		
		if (this.x < 0 ) {
			radians = radians + Math.PI;
		}

		this.radians = radians;
	}
//	public void undo() {
//		double curxy = this.getNbt().sD("x");
//		double curyy = this.getNbt().sD("y");
//		System.out.println("BEFORE: "+curxy+","+curyy);
//		this.getNbt().accum("x", -this.getX());
//		this.getNbt().accum("y", -this.getY());
//		
//		double curx = this.getNbt().sD("x");
//		double cury = this.getNbt().sD("y");
//		double s = this.getNbt().sD("d");
//		//System.out.print(		"X:"+
//			this.getNbt().set("cx", (int)((int)curx+(int)s/2)/(int)(Grid.TILESIZE));
//						//);
//				//System.out.println(" , Y:"+
//			this.getNbt().set("cy", (int)((int)cury+(int)s/2)/(int)(Grid.TILESIZE));
//						//);
//		
//		double curxx = this.getNbt().sD("x");
//		double curyx = this.getNbt().sD("y");
//		System.out.println("AFTER: "+curxx+","+curyx);
//		//System.exit(0);
//	}
	public double getRadians() {
		updateRadians();
		return radians;
	}
	public void addMotion(Motion m) {
		double newX = m.getX()+x;
		double newY = m.getY()+y;
		
		this.setX(newX);
		this.setY(newY);
	}
	public void addMotion(double newXX, double newYY) {
		double newX = newXX+x;
		double newY = newYY+y;
		
		this.setX(newX);
		this.setY(newY);
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		updateRadians();

		this.x = x;

	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		updateRadians();

		this.y = y;

	}
	
	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public Entity getE() {
		return e;
	}
	public void setE(Entity e) {
		this.e = e;
	}
	public String toString() {
		return "motion-x:" + x + ",motion-y:" + y + ",motion-speed:" + speed;
	}
	@Override
	public void saveComponent(NBT tag) {
		tag.set("motion-x", x);
		tag.set("motion-y", y);
		tag.set("motion-speed", speed);
	}
	@Override
	public void initComponent(NBT tag) {
		this.setX(tag.sD("motion-x"));
		this.setY(tag.sD("motion-y"));
		this.speed = tag.sD("motion-speed");
		
	}
	
}
