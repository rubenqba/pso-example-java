package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the heart of the PSO program
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

//import org.gandhim.pso.delete.PSOConstants;
import org.gandhim.pso.problem.PSOProblemSet;
import org.gandhim.pso.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;

public class PSOProcess  {

    private PSOProblemSet problem;
    private List<Particle> swarm;
//    private double[] pBest;
//    private List<Location> pBestLocation;
    private double gBest;
    private Location gBestLocation;
//    private double[] fitnessValueList;

    private Random generator;

    public PSOProcess(PSOProblemSet problem) {
        this.problem = problem;
        swarm = new ArrayList<>(problem.getSwarmSize());
        gBest = Double.MAX_VALUE;
        generator = RandomGenerator.getInstance().getRandom();
    }

    public void execute() {
        initializeSwarm();
        updateFitnessList();

        int t = 0;
        double w;
        double err = 9999;

        while(t < problem.getMaximumIterations() && err > problem.getErrorTolerance()) {

            w = problem.getW(t);

            for(int i=0; i<problem.getSwarmSize(); i++) {
                double r1 = generator.nextDouble();
                double r2 = generator.nextDouble();

                final Particle p = swarm.get(i);

                // step 3 - update velocity
                double[] newVel = new double[problem.getProblemDimension()];
                for (int j = 0; j < problem.getProblemDimension(); j++) {
                    newVel[j] = (w * p.getVelocity().getPos()[j]) +
                            (r1 * problem.getC1()) * (p.getBestLocation().getLoc()[j] - p.getLocation().getLoc()[j]) +
                            (r2 * problem.getC2()) * (gBestLocation.getLoc()[j] - p.getLocation().getLoc()[j]);
                }

                Velocity vel = new Velocity(newVel);
                p.setVelocity(vel);

                // step 4 - update location
                double[] newLoc = new double[problem.getProblemDimension()];
                for (int j = 0; j < problem.getProblemDimension(); j++) {
                    newLoc[j] = p.getLocation().getLoc()[j] + newVel[j];
                }
                Location loc = new Location(newLoc);
                p.setLocation(loc);
            }

            err = problem.evaluate(gBestLocation.getLoc()) - problem.getOptimum(); // minimizing the functions means it's getting closer to 0


            System.out.println("ITERATION " + t + ": ");
            System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
            System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
            System.out.println("     Value: " + problem.evaluate(gBestLocation.getLoc()));
            System.out.println("     Error: " + err);

            t++;
            updateFitnessList();
            //Thread.sleep(1000);
        }

        System.out.println("\nSolution found at iteration " + (t - 1) + ", the solutions is:");
        System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
        System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
    }

    public void initializeSwarm() {
        swarm = IntStream.range(0, problem.getSwarmSize())
                .mapToObj(i ->{
                    Particle p = new Particle();
                    p.setLocation(PSOUtility.randomLocation(problem));
                    p.setVelocity(PSOUtility.randomVelocity(problem));
                    return p;
                }).collect(Collectors.toList());
    }

    public void updateFitnessList() {
        List<Double> fitness = swarm.stream()
                .map(p -> p.getFitnessValue(problem))
                .collect(Collectors.toList());

        int best = IntStream.range(0, swarm.size()).boxed()
                .min(comparingDouble(fitness::get))
                .get();

        if (gBest > fitness.get(best)) {
            gBest = fitness.get(best);
            gBestLocation = new Location(swarm.get(best).getLocation());
        }
    }
}