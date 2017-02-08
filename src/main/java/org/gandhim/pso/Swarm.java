package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the heart of the PSO program
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

//import org.gandhim.pso.delete.PSOConstants;

import org.gandhim.pso.data.Location;
import org.gandhim.pso.data.Particle;
import org.gandhim.pso.data.Velocity;
import org.gandhim.pso.problem.PSOProblemSet;
import org.gandhim.pso.util.PSOUtility;
import org.gandhim.pso.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;

public class Swarm {

    private PSOProblemSet problem;
    private List<Particle> swarm;
    private double gBest;
    private Location gBestLocation;

    private Random generator;
    private int iteration;

    public Swarm(PSOProblemSet problem) {
        this.problem = problem;
        swarm = new ArrayList<>(problem.getSwarmSize());
        gBest = Double.MAX_VALUE;
        generator = RandomGenerator.getInstance().getRandom();
    }

    public void execute() {
        initializeSwarm();
//        updateFitnessList();

        iteration = 0;
        while (iteration < problem.getMaximumIterations() && getError() > problem.getErrorTolerance()) {
            updateFitnessList();
            iterate();
        }

        System.out.println("\nSolution found at iteration " + (iteration - 1) + ", the solutions is:");
        System.out.println("     Best : " + gBestLocation);
        System.out.println("     Value: " + problem.evaluate(gBestLocation.getLoc()));
        System.out.println("     Error: " + getError());
    }

    public void iterate() {
        updateFitnessList();

        final double w = problem.getW(iteration);

        swarm.stream().forEach(p -> {
            double r1 = generator.nextDouble();
            double r2 = generator.nextDouble();

            // step 3 - update velocity
            // step 4 - update location
            double[] newVel = new double[problem.getProblemDimension()];
            double[] newLoc = new double[problem.getProblemDimension()];

            IntStream.range(0, problem.getProblemDimension())
                    .forEach(j -> {
                        newVel[j] = (w * p.getVelocity().getPos()[j]) +
                                (r1 * problem.getC1()) * (p.getBestLocation().getLoc()[j] - p.getLocation().getLoc()[j]) +
                                (r2 * problem.getC2()) * (gBestLocation.getLoc()[j] - p.getLocation().getLoc()[j]);
                        newLoc[j] = p.getLocation().getLoc()[j] + newVel[j];
                    });
            p.setVelocity(new Velocity(newVel));
            p.setLocation(new Location(newLoc));
        });

        System.out.println("ITERATION " + iteration + ": ");
        System.out.println("     Best : " + gBestLocation);
        System.out.println("     Value: " + problem.evaluate(gBestLocation.getLoc()));
        System.out.println("     Error: " + getError());

        iteration++;
    }

    public void initializeSwarm() {
        swarm = IntStream.range(0, problem.getSwarmSize())
                .mapToObj(i -> {
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

    public double getError() {
        // minimizing the functions means it's getting closer to problem optimum
        return Math.abs(gBest - problem.getOptimum());
    }
}