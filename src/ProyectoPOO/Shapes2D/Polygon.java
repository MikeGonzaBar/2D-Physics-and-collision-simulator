package ProyectoPOO.Shapes2D;

public abstract class Polygon extends Shape2d {
    protected Vector2D[] vertices;

    public Polygon(double[] x, double[] y){
        if(x.length != y.length){
            vertices = null;
        }else{
            vertices = new Vector2D[x.length];
            for(int i = 0; i < x.length; i++){
                vertices[i] = new Vector2D(x[i],y[i]);
            }
        }
    }

    public abstract void updateVertices();
    public abstract double getPerimeter();
    public abstract double getArea();

    public Vector2D[] getVertices(){
        return vertices;
    }    

    public int[] getXCoords(){
        int[] intArrayX = new int[this.getVertices().length]; 
        for (int i=0; i<intArrayX.length; i++){
            intArrayX[i] = (int) this.getVertices()[i].getX();
        }
        return intArrayX;
    }

    public int[] getYCoords(){
        int[] intArrayY = new int[this.getVertices().length];
        for (int i=0; i<intArrayY.length; i++){
            intArrayY[i] = (int) this.getVertices()[i].getY();
        }
        return intArrayY;
    }
}