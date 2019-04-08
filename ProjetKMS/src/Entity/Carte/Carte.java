package Entity.Carte;

import Entity.Entity;


public class Carte extends Entity{
    private String description;

	public String getDescription() {return description;}

	public void setDescription(String description) {this.description = description;}

    public Carte(){
    	super();
        this.description = "";
    }

    public Carte(int id, String name, String description){
    	super(id,name);
    	this.description = description;
    }

    public String getAllCarteByString(){
    	return getId()+ "-" + getName() + "-" + description;
    }

}
