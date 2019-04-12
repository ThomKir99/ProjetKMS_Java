package Entity.Carte;

import Entity.Entity;
import Entity.Group.Group;


public class Carte extends Entity{

	private String description;
	private int order;
	private boolean complete;
    private double z3DPosition;
    private final double carteHeight = 15;
    private final double carteWeight = 20;
    private final double cartedepth = 0.1;

	public String getDescription() {return this.description;}

	public int getOrder() {return this.order;}

	public boolean getIfComplete() {return this.complete;}

	public void setDescription(String description) {this.description = description;}

	public void setOrder(int order){this.order = order;}

	public void setComplete(boolean complete){this.complete = complete;}

  public Carte(){
  	super();
    this.description = "";
        setZ3DPosition(0);
  }

  public Carte(int id, String name, String description){
  	super(id,name);
  	this.description = description;
  }

  public Carte(int id, String name){
  	super(id,name);
  }

  public Carte(int id, String name, String description,int order, boolean complete){
  	super(id,name);
  	this.description = description;
  	this.order = order;
  	this.complete = complete;
  }

  public String getAllCarteByString(){
  	return getId()+ "-" + getName() + "-" + description;
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
	public boolean isEqualTo(Carte aCarte){
		if (this.getName().equals(aCarte.getName())){
			return true;
		}else{
			return false;
		}
	}

}
