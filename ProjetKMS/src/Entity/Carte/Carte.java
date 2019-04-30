package Entity.Carte;

import Entity.Entity;
import Entity.Group.Group;


public class Carte extends Entity{

	private String description;
	private int ordre_de_priorite;
	private boolean complete;
    private final double carteHeight = 15;
    private final double carteWeight = 20;
    private final double cartedepth = 0.1;
    private float positionX;
    private float positionY;
    private float positionZ;
    private int groupId;


	public String getDescription() {return this.description;}

	public int getOrder() {return this.ordre_de_priorite;}

	public boolean getIfComplete() {return this.complete;}

	public void setDescription(String description) {this.description = description;}

	public void setOrder(int order){this.ordre_de_priorite = order;}

	public void setComplete(boolean complete){this.complete = complete;}

  public Carte(){
  	super();
    this.description = "";
  }

  public Carte(int id, String name, String description){
  	super(id,name);
  	this.description = description;

  }

  public Carte(int id, String name){
  	super(id,name);
  	this.positionX =0;
  	this.positionY =0;
  	this.positionZ = 0;
  }


  public Carte(int id, String name, String description,int order, boolean complete,
		  int carteGroupId){
  	super(id,name);
  	this.description = description;
  	this.ordre_de_priorite = order;
  	this.complete = complete;
  	this.groupId = carteGroupId;
  	this.positionX = 0;
  	this.positionY =0;
  	this.positionZ = 0;
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

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public float getPositionX() {
		return positionX;
	}
	public float getPositionY() {
		return positionY;
	}
	public float getPositionZ() {
		return positionZ;
	}
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}
	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}
	public void setPositionZ(float positionZ) {
		this.positionZ = positionZ;
	}

}
