
/**
 * @author 36672
 * @date   2023/6/5
 * @version 1.0
 * @since 1.0
 */
public class Planet {
    /**
     *  Its current x position.
     */
    public double xxPos;
    /**
     *  Its current y position.
     */
    public double yyPos;
    /**
     * Its current velocity in the x direction.
     */
    public double xxVel;
    /**
     * Its current velocity in the y direction.
     */
    public double yyVel;
    /**
     * Its mass.
     */
    public double mass;
    /**
     * The name of the file that corresponds to the image
     * that depicts the planet (for example, jupiter.gif).
     */
    public String imgFileName;
    /**
     * gravitational constant
     */
    private static final double G = 6.67e-11;
    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    /**
     * calculates the distance between two Planets.
     * @param p one of the planets.
     * @return the distance
     */
    public double calcDistance(Planet p){
        double distance;
        double diffX = this.xxPos - p.xxPos;
        double diffY = this.yyPos - p.yyPos;
        distance = Math.sqrt(diffX * diffX + diffY * diffY);
        return distance;
    }

    /**
     * takes in a planet, and returns a double describing
     * the force exerted on this planet by the given planet.
     * @param p one of the planets.
     * @return force exerted on this planet by the given planet.
     */
    public double calcForceExertedBy(Planet p){
        double r = this.calcDistance(p);
        return (G * this.mass * p.mass)/(r * r);
    }

    /**
     * describe the force exerted in the X direction.
     * @param p one of the planets.
     * @return force exerted in the X direction
     */
    public double calcForceExertedByX(Planet p){
        return this.calcForceExertedBy(p) * (p.xxPos - this.xxPos) / this.calcDistance(p);
    }

    /**
     * describe the force exerted in the Y direction.
     * @param p one of the planets.
     * @return force exerted in the Y direction
     */
    public double calcForceExertedByY(Planet p){
        return this.calcForceExertedBy(p) * (p.yyPos - this.yyPos) / this.calcDistance(p);
    }

    /**
     * take in an array of Planets and calculate the
     * net X and net Y force exerted by all planets
     * in that array upon the current Planet
     * @param ps an array of Planets.
     * @return all Planets net force X
     */
    public double calcNetForceExertedByX(Planet[] ps){
        double netX = 0.0;
        for(Planet p : ps){
            if(!this.equals(p)) {
                netX += this.calcForceExertedByX(p);
            }
        }
        return netX;
    }

    /**
     * take in an array of Planets and calculate the
     * net X and net Y force exerted by all planets
     * in that array upon the current Planet.
     * @param ps an array of Planets.
     * @return all Planets net force Y.
     */
    public double calcNetForceExertedByY(Planet[] ps){
        double netY = 0.0;
        for(Planet p : ps){
            if(!this.equals(p)) {
                netY += this.calcForceExertedByY(p);
            }
        }
        return netY;
    }

    /**
     * determines how much the forces exerted on the planet will cause that planet to accelerate,
     * and the resulting change in the planet’s velocity and position in a small period of time dt.
     * @param dt a small period of time when forcing.
     * @param fx force in X.
     * @param fy force in Y.
     */
    public void update(double dt, double fx, double fy){
        double ax = fx / this.mass;
        double ay = fy / this.mass;
        this.xxVel = this.xxVel + ax * dt;
        this.yyVel = this.yyVel + ay * dt;
        this.xxPos = this.xxPos + this.xxVel * dt;
        this.yyPos = this.yyPos + this.yyVel * dt;
    }

    /**
     * draw the Planet’s image at the Planet’s position.
     */
    public void draw(){
        StdDraw.picture(this.xxPos,this.yyPos,"images/"+imgFileName);
    }

}
