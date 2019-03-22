package Entity.Carte;

import Entity.Entity;
import Entity.Position;

public class Carte extends Entity{
    private String description;

	public String getDescription() {return description;}

	public void setDescription(String description) {this.description = description;}

    public Carte(){
    	super();
        this.description = "";
    }

    public Carte(int id, String name, Position position, float width, float height,String description){
    	super(id,name,position,width,height);
    	this.description = description;
    }


}
