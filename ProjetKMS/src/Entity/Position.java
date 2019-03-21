package Entity;


public class Position {
	private float x ;
    private float y;
    private float z;

	public float getX() {return x;}

	public void setX(float x) {this.x = x;}

	public float getY() {return y;}

	public void setY(float y) {this.y = y;}

	public float getZ() {return z;}

	public void setZ(float z) {this.z = z;}

	public Position(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
	}

	public Position(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
	}


    public boolean SamePosition(Position point1)
    {
    	boolean samePosition = false;

        if (areEqual(this.x, point1.x) && areEqual(this.y, point1.y) && areEqual(this.z, point1.z))
        {
            samePosition = true;
        }

        return samePosition;
    }

    private boolean areEqual(float firstNumber, float secondNumber)
    {
    	boolean areEqual = false;
        float tolerance = 0.01f;
        float difference = firstNumber - secondNumber;
        if (Math.abs(difference) <= tolerance)
        {
            areEqual = true;
        }

        return areEqual;
    }


}
