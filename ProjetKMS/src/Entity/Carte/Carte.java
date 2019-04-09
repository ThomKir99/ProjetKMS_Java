package Entity.Carte;

import Entity.Entity;
import Entity.Position;


public class Carte extends Entity{
    private String description;
    private double z3DPosition;
    private final double carteHeight = 15;
    private final double carteWeight = 20;
    private final double cartedepth = 0.1;

	public String getDescription() {return description;}

	public void setDescription(String description) {this.description = description;}

    public Carte(){
    	super();
        this.description = "";
        setZ3DPosition(0);
    }

    public Carte(int id, String name, Position position, float width, float height,String description){
    	super(id,name,position,width,height);
    	this.description = description;
    }
    public String getAllCarteByString(){
    	return getId()+ "-" + getName() + "-" + getPosition().getPosition() + "-"+getWidth() + "-"
    			+ getHeight() + "-" + description;
    }

	public double getZ3DPosition() {
		return z3DPosition;
	}

	public void setZ3DPosition(double zPosition) {
		z3DPosition = zPosition;
	}

	public double getCarteHeight() {
		return carteHeight;
	}

	public double getCarteWeight() {
		return carteWeight;
	}

	public double getCartedepth() {
		return cartedepth;
	}

}
