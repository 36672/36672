public class NBody {
    /**
     * Get the radius in the txt
     * @param path the file path
     * @return the planet radius
     */
    public static double readRadius(String path){
        In in = new In(path);
        int planetNums = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /**
     * Given a file name, it should return an array of Planets corresponding to the planets in the file,
     * e.g. readPlanets("./data/planets.txt") should return an array of five planets.
     * @param path the file path
     * @return an array of the numbers that in the file planets
     */
    public static Planet[] readPlanets(String path){
        In in = new In(path);
        int planetNums = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[planetNums];
        for(int i = 0; i < planetNums; i++){
            planets[i] = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return planets;
    }

    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("Please supply T, dt and filename as the command line arguments.");
            System.out.println("e.g. 15500.0 10.3 ./data/planets.txt");
        }
        else {
            double T = Double.parseDouble(args[0]);
            double dt = Double.parseDouble(args[1]);
            String path = args[2];

            double radius = readRadius(path);
            Planet[] planets = readPlanets(path);

            StdDraw.setXscale(-radius,radius);
            StdDraw.setYscale(-radius,radius);
            StdDraw.enableDoubleBuffering();

            for(int time = 0; time <= T; time+=dt){
                /* calculate x and y netForce every planets  */
                double[] xForces = new double[planets.length];
                double[] yForces = new double[planets.length];
                for(int i = 0; i < planets.length; i++){
                    xForces[i] = planets[i].calcNetForceExertedByX(planets);
                    yForces[i] = planets[i].calcNetForceExertedByY(planets);
                }
                /**
                 * After calculating the xy direction force of each planet,
                 * update the position, speed and acceleration of each planet.
                 * It cannot be updated immediately, otherwise there will be errors
                 */
                for (int i = 0 ; i < planets.length; i++) {
                    planets[i].update(dt,xForces[i],yForces[i]);
                }
                StdDraw.picture(0,0,"images/starfield.jpg");
                for (Planet p : planets){
                    p.draw();
                }
                StdDraw.show();
                StdDraw.pause(10);
            }
            StdOut.printf("%d\n",planets.length);
            StdOut.printf("%.2e\n",radius);
            for(int i = 0; i < planets.length; i++) {
                StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                        planets[i].xxPos,planets[i].yyPos,planets[i].xxVel,
                        planets[i].yyVel,planets[i].mass,planets[i].imgFileName);
            }
        }
    }
}
