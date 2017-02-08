package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the heart of the PSO program
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

//import org.gandhim.pso.delete.PSOConstants;
import org.gandhim.pso.problem.PSOProblemSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.IntStream;

public class PSOProcess  {

    private PSOProblemSet problem;
    private List<Particle> swarm;
    private double[] pBest;
    private List<Location> pBestLocation;
    private double gBest;
    private Location gBestLocation;
    private double[] fitnessValueList;

    Random generator = new Random(4);

    public PSOProcess(PSOProblemSet problem) {
        this.problem = problem;
        swarm = new ArrayList<>(problem.getSwarmSize());
        pBest = new double[problem.getSwarmSize()];
        pBestLocation = new ArrayList<>(problem.getSwarmSize());
        fitnessValueList = new double[problem.getSwarmSize()];
    }

    public void execute() {
        initializeSwarm();
        updateFitnessList();

        for(int i=0; i<problem.getSwarmSize(); i++) {
            pBest[i] = fitnessValueList[i];
            pBestLocation.add(swarm.get(i).getLocation());
        }

        int t = 0;
        double w;
        double err = 9999;

        while(t < problem.getMaximumIterations() && err > problem.getErrorTolerance()) {
            // step 1 - update pBest
            for(int i=0; i<problem.getSwarmSize(); i++) {
                if(fitnessValueList[i] < pBest[i]) {
                    pBest[i] = fitnessValueList[i];
                    pBestLocation.set(i, swarm.get(i).getLocation());
                }
            }

            // step 2 - update gBest
            int bestParticleIndex = PSOUtility.getMinPos(fitnessValueList);
            if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
                gBest = fitnessValueList[bestParticleIndex];
                gBestLocation = swarm.get(bestParticleIndex).getLocation();
            }

            w = problem.getW(t);//W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);

            for(int i=0; i<problem.getSwarmSize(); i++) {
                double r1 = generator.nextDouble();
                double r2 = generator.nextDouble();

                final Particle p = swarm.get(i);

                // step 3 - update velocity
                double[] newVel = new double[problem.getProblemDimension()];
                for (int j = 0; j < problem.getProblemDimension(); j++) {
                    newVel[j] = (w * p.getVelocity().getPos()[j]) +
                            (r1 * problem.getC1()) * (pBestLocation.get(i).getLoc()[j] - p.getLocation().getLoc()[j]) +
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
        Particle p;
        for(int i=0; i<problem.getSwarmSize(); i++) {
            p = new Particle();

            // randomize location inside a space defined in Problem Set
            // randomize velocity in the range defined in Problem Set
            double[] loc = new double[problem.getProblemDimension()];
            double[] vel = new double[problem.getProblemDimension()];

            IntStream.range(0, problem.getProblemDimension())
                    .forEach(j -> {
                        loc[j] = problem.getLocationMinimum()[j] + generator.nextDouble() * (problem.getLocationMaximum()[j] - problem.getLocationMinimum()[j]);
                        vel[j] = problem.getMinimumVelocity() + generator.nextDouble() * (problem.getMaximumVelocity() - problem.getMinimumVelocity());
                    });

            Location location = new Location(loc);
            Velocity velocity = new Velocity(vel);

            p.setLocation(location);
            p.setVelocity(velocity);
            swarm.add(p);
        }
    }

    public void updateFitnessList() {
        for(int i=0; i<problem.getSwarmSize(); i++) {
            fitnessValueList[i] = swarm.get(i).getFitnessValue(problem);
        }
    }
}