
public class Entity {
    private int id;
    private String name;
    private Position position;
    private float width;
    private float height;

    public Entity(){
        this.id = -1;
        this.name = "";
        this.position = new Position();
        this.width = 0;
        this.height = 0;
    }

    public Entity(int id, String name, Position position, float width, float height){
        this.id = id;
        this.name = name;
        this.position = position;
        this.width = width;
        this.height = height;
    }

	public float getHeight() {return height;}

	public void setHeight(float height) {this.height = height;}

	public String getName() {return name;}

	public void setName(String name) {this.name = name;}

	public float getWidth() {return width;}

	public void setWidth(float width) {this.width = width;}

	public int getId() {return id;}

	public void setId(int id) {this.id = id;}

	public Position getPosition() {return position;}

	public void setPosition(Position position) {this.position = position;}
}
