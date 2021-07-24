package ProyectoPOO.Engine;

import java.util.ArrayList;
import ProyectoPOO.Shapes2D.*;

public class Collisions {
    
    private ArrayList<Shape2d> shapes;
    private Window window;
    public static double BOUNCE = .9;

    public Collisions(ArrayList<Shape2d> shapes, Window window){
        this.shapes = shapes;
        this.window = window;
    }

    public void checkWallCollisions(){
        for(int i = 0; i < shapes.size(); i++){
            Shape2d o1 = shapes.get(i);
            // Wall collision Circle
            if(o1 instanceof Circle){
                Circle c1 = (Circle) o1;
                if(c1.getCenter().getX()-c1.getRadius() <= 0){
                    c1.setCenter(c1.getRadius(), c1.getY());
                    c1.setVelocity(-c1.getVx()*BOUNCE,c1.getVy());

                }else if(c1.getCenter().getX()+c1.getRadius() >= window.getWidth()){
                    c1.setCenter(window.getWidth()-c1.getRadius(), c1.getY());
                    c1.setVelocity(-c1.getVx()*BOUNCE,c1.getVy());

                }else if(c1.getCenter().getY()+c1.getRadius() >= window.getHeight()){
                    c1.setCenter(c1.getX(), window.getHeight()-c1.getRadius());
                    c1.setVelocity(c1.getVx(),-c1.getVy()*BOUNCE);
                }
            }else if(o1 instanceof Rectangle){
                Rectangle r1 = (Rectangle) o1;
                if(r1.getCenter().getX()-r1.getWidth()/2 <= 0){
                    r1.setCenter(r1.getWidth()/2, r1.getY());
                    r1.setVelocity(-r1.getVx(),r1.getVy());

                }else if(r1.getCenter().getX()+r1.getWidth()/2 >= window.getWidth()){
                    r1.setCenter(window.getWidth()-r1.getWidth()/2, r1.getY());
                    r1.setVelocity(-r1.getVx(),r1.getVy());
                }else if(r1.getCenter().getY()+r1.getHeigth()/2 >= window.getHeight()){
                    r1.setCenter(r1.getX(), window.getHeight()-r1.getHeigth()/2);
                    r1.setVelocity(r1.getVx(),-r1.getVy());
                }else if(r1.getCenter().getY()-r1.getHeigth()/2 <= 0){
                    r1.setCenter(r1.getX(), r1.getHeigth()/2);
                    r1.setVelocity(r1.getVx(),-r1.getVy());
                }   
            }
        }
    }

    public void checkCollisionsObjects(){
        for(int i = 0; i < shapes.size(); i++){
            Shape2d o1 = shapes.get(i);
            for(int j = i+1; j < shapes.size(); j++){
                Shape2d o2 = shapes.get(j);
                // Circle vs circle
                if(o1 instanceof Circle && o2 instanceof Circle){
                    Circle c1 = (Circle) o1;
                    Circle c2 = (Circle) o2;
                    Vector2D subs = new Vector2D(c1.getCenter().getX()-c2.getCenter().getX(),c1.getCenter().getY()-c2.getCenter().getY());
                    double distance = Math.sqrt(subs.getX()*subs.getX() + subs.getY()*subs.getY());
                    if(distance <= c1.getRadius()+c2.getRadius()){
                        AttendCircleCollision(c1,c2,distance);
                    } 
                // Circulo vs triangulo
                }else if((o1 instanceof Circle && o2 instanceof Triangle) || (o1 instanceof Triangle && o2 instanceof Circle)){
                    Circle c1 = o1 instanceof Circle ? (Circle) o1 : (Circle) o2;
                    Triangle t1 = o1 instanceof Triangle ? (Triangle) o1 : (Triangle) o2;

                    for(int k = 0; k < t1.getVertices().length; k++){
                        Vector2D p1;
                        Vector2D p2 ;
                        if(k == t1.getVertices().length-1){
                            p1 = t1.getVertices()[k];
                            p2 = t1.getVertices()[0];
                        }else{
                            p1 = t1.getVertices()[k];
                            p2 = t1.getVertices()[k+1];
                        }
                        
                        boolean p1InCircle = pointInsideCircle(p1,c1);
                        boolean p2InCircle = pointInsideCircle(p2,c1);
                        if (p1InCircle || p2InCircle){
                            c1.setVelocity(-c1.getVx(), -c1.getVy());
                            continue;
                        }
                        
                        double distX = p1.getX() - p2.getX();
                        double distY = p1.getY() - p2.getY();
                        double len = Math.sqrt((distX*distX) + (distY*distY));

                        double dot = (((c1.getX()-p1.getX())*(p2.getX()-p1.getX())) + ((c1.getY()-p1.getY())*(p2.getY()-p1.getY()))) / (len*len);
                        
                        double closestX = p1.getX() + (dot * (p2.getX()-p1.getX()));
                        double closestY = p1.getY() + (dot * (p2.getY()-p1.getY()));

                        boolean onLine = pointInLine(p1,p2,new Vector2D(closestX,closestY));
                        if (!onLine) continue;       
                        
                        distX = closestX - c1.getX();
                        distY = closestY - c1.getY();
                        double distance = Math.sqrt((distX*distX) + (distY*distY));
                        if(distance <= c1.getRadius()){
                            if(t1 instanceof Triangle){
                                if(p1 == t1.getVertices()[2] && p2 == t1.getVertices()[0]){ // Left side
                                    c1.setCenter(c1.getX()-5, c1.getY()-5);
                                    c1.setVelocity(-c1.getVx(),-c1.getVy());
                                }else if(p1 == t1.getVertices()[1] && p2 == t1.getVertices()[2]){ // Right side
                                    c1.setCenter(c1.getX()+5, c1.getY()-5);
                                    c1.setVelocity(-c1.getVx(),-c1.getVy());
                                }else{ // Bottom side
                                    c1.setCenter(c1.getX(), c1.getY()+5);
                                    c1.setVelocity(c1.getVx(),-c1.getVy());
                                }
                            }
                        }
                    }
                // Circulo vs Rectangulo
                }else if((o1 instanceof Circle && o2 instanceof Rectangle) || (o1 instanceof Rectangle && o2 instanceof Circle)){

                    Circle c1 = o1 instanceof Circle ? (Circle) o1 : (Circle) o2;
                    Rectangle r1 = o1 instanceof Rectangle ? (Rectangle) o1 : (Rectangle) o2;
                    
                    double x = r1.getX() - r1.getWidth()/2.0;
                    double y = r1.getY() - r1.getHeigth()/2.0;
                    double px = c1.getX(); // En principio son iguales
                    if(px < x) px = x;
                    if(px > x + r1.getWidth()) px = x + r1.getWidth();
                    double py = c1.getY();
                    if(py < y) py = y;
                    if(py > y + r1.getHeigth()) py = y + r1.getHeigth();
                    double distance = Math.sqrt( (c1.getX() - px)*(c1.getX() - px) + (c1.getY() - py)*(c1.getY() - py));
                    if (distance > c1.getRadius()) {
                        continue;
                    }	
                    // Collision management
                    if(r1 instanceof Rectangle){
                        if(c1.getX() < x){ // Left side
                            c1.setCenter(c1.getX()-3, c1.getY());
                            c1.setVelocity(-c1.getVx(),c1.getVy());
                        }else if(c1.getX() > x + r1.getWidth()){ // Right side
                            c1.setCenter(c1.getX()+3, c1.getY());
                            c1.setVelocity(-c1.getVx(),c1.getVy());
                        }else if(c1.getY() < y){ // Top side
                            c1.setCenter(c1.getX(), c1.getY()-3);
                            c1.setVelocity(c1.getVx(),-c1.getVy());
                        }else{ // Bottom side
                            c1.setCenter(c1.getX(), c1.getY()+3);
                            c1.setVelocity(c1.getVx(),-c1.getVy());
                        }
                    }
                // Rectangle vs rectangle
                }else if(o1 instanceof Rectangle && o2 instanceof Rectangle){
                    Rectangle r1 = (Rectangle) o1;
                    Rectangle r2 = (Rectangle) o2;
                    // Top left corners of the rectangle
                    double x1 = r1.getX() - r1.getWidth()/2.0;
                    double y1 = r1.getY() - r1.getHeigth()/2.0;
                    double x2 = r2.getX() - r2.getWidth()/2.0;
                    double y2 = r2.getY() - r2.getHeigth()/2.0;
                    // Checking if they are not colliding
                    if(x1 > x2 + r2.getWidth()) continue;
                    if(x1+ r1.getWidth() < x2) continue;
                    if(y1 > y2+r2.getHeigth()) continue;
                    if(y1+r1.getHeigth() < y2) continue;
                    
                    // If the rectangles dont meet the any conditions above, they are overlapping
                    // Collision management
                    int vx1_s = Integer.signum((int) r1.getVx());
                    int vx2_s = Integer.signum((int) r2.getVx());
                    int vy1_s = Integer.signum((int) r1.getVy());
                    int vy2_s = Integer.signum((int) r2.getVy());

                    // Going in same direction
                    if(vx1_s == vx2_s && vy1_s == vy2_s){
                        double speedR1 = r1.getVelocity().magnitude();
                        double speedR2 = r2.getVelocity().magnitude();
                        if(speedR1 > speedR2){
                            r2.setCenter(r2.getX() + vx2_s*5, r2.getY() + vy2_s*5);
                        }else if(speedR1 < speedR2){
                            r1.setCenter(r1.getX() + vx1_s*5, r1.getY() + vy1_s*5);
                        }
                    }else if(vx1_s == vx2_s*-1 && vy1_s == vy2_s*-1){
                        r2.setCenter(r2.getX() + vx2_s*-2, r2.getY() + vy2_s*-2);
                        r1.setCenter(r1.getX() + vx1_s*-2, r1.getY() + vy1_s*-2);
                    }else if((vx1_s == vx2_s && vy1_s == vy2_s*-1) || (vx1_s == vx2_s*-1 && vy1_s == vy2_s)){
                        if(vx1_s == vx2_s*-1){
                            r2.setCenter(r2.getX() + vx2_s*-2, r2.getY());
                            r1.setCenter(r1.getX() + vx1_s*-2, r1.getY());
                        }else if(vy1_s == vy2_s*-1){
                            r2.setCenter(r2.getX(), r2.getY() + vy2_s*-2);
                            r1.setCenter(r1.getX(), r1.getY() + vy1_s*-2);
                        }
                    }else if((vx1_s == 0 && vx2_s != 0) || (vy1_s == 0 && vy2_s != 0) ||
                             (vx1_s != 0 && vx2_s == 0) || (vy1_s != 0 && vy2_s == 0)){
                        double vx = r2.getVx() + r1.getVx();
                        double vy = r2.getVy() + r1.getVy();
                        if(vx1_s != 0){
                            r1.setCenter(r1.getX() + vx1_s*-3, r1.getY() + 3*vy2_s);
                            r1.setVelocity(vx*-.5, vy*.5); 
                            r2.setVelocity(vx*.5, vy*.5);
                        }else if(vy1_s != 0){
                            r1.setCenter(r1.getX() + 3*vx2_s, r1.getY() + vy1_s*-3);
                            r1.setVelocity(vx*.5, vy*-.5); 
                            r2.setVelocity(vx*.5, vy*.5);
                        }                        
                    }
                    Vector2D tmpVel = r1.getVelocity().clone();
                    r1.setVelocity(r2.getVelocity());
                    r2.setVelocity(tmpVel);
                }
            }
        }
    }

    public static void AttendCircleCollision(Circle c1, Circle c2, double distance){
		double collisionAngle = Math.atan2(c1.getY() - c2.getY(), c1.getX() - c2.getX());
        
        Vector2D tempVel1 = c1.getVelocity().clone();
        tempVel1.rotateCoordinates(collisionAngle);
		Vector2D tempVel2 = c2.getVelocity().clone();
        tempVel2.rotateCoordinates(collisionAngle);
        
		double swap = tempVel1.getX();
		tempVel1.setX(tempVel2.getX());
		tempVel2.setX(swap);
	
		tempVel1.restoreCoordinates();
		tempVel2.restoreCoordinates();

		c1.setVelocity(tempVel1.getX() * BOUNCE, tempVel1.getY() * BOUNCE);
		c2.setVelocity(tempVel2.getX() * BOUNCE, tempVel2.getY() * BOUNCE);
      
		double minDist = c1.getRadius() + c2.getRadius();
		double overlap = minDist - distance;
        double toMove = overlap / 2.0;
        c1.setCenter(c1.getX() + (toMove * Math.cos(collisionAngle)),c1.getY() + (toMove * Math.sin(collisionAngle)));
        c2.setCenter(c2.getX() - (toMove * Math.cos(collisionAngle)),c2.getY() - (toMove * Math.sin(collisionAngle)));
    }

    public boolean pointInsideCircle(Vector2D p1, Circle c1){
        double distX = p1.getX() - c1.getX();
        double distY = p1.getY() - c1.getY();
        double distance = Math.sqrt((distX*distX)+(distY*distY));
        if (distance <= c1.getRadius()) {
            return true;
        }
        return false;
    }

    public boolean pointInLine(Vector2D p1, Vector2D p2, Vector2D px) {
        double d1x = px.getX() - p1.getX();
        double d1y = px.getY() - p1.getY();
        double d1 = Math.sqrt((d1x*d1x) + (d1y*d1y));

        double d2x = px.getX() - p2.getX();
        double d2y = px.getY() - p2.getY();
        double d2 = Math.sqrt((d2x*d2x) + (d2y*d2y));
      
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        double lineLen  = Math.sqrt((dx*dx) + (dy*dy));
        double buffer = 0.1;
        if (d1+d2 >= lineLen-buffer && d1+d2 <= lineLen+buffer) {
          return true;
        }
        return false;
      }
}




