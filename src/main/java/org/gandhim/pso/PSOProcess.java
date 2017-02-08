package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is the heart of the PSO program
// the code is for 2-dimensional space problem
// but you can easily modify it to solve higher dimensional space problem

import org.gandhim.pso.problem.PSOProblemSet;

import java.util.Random;
import java.util.Vector;

public class PSOProcess implements PSOConstants {
	private PSOProblemSet problem;
	private Vector<Particle> swarm ;
	private double[] pBest;
	private Vector<Location> pBestLocation;
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList;
	
	Random generator = new Random();

    public PSOProcess(PSOProblemSet problem) {
        this.problem = problem;
        swarm = new Vector<>(problem.getSwarmSize());
        pBest = new double[problem.getSwarmSize()];
        pBestLocation = new Vector<>(problem.getSwarmSize());
        fitnessValueList = new double[problem.getSwarmSize()];
    }

    public void execute() throws InterruptedException {
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
			
			w = problem.getW(t); //W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
			
			for(int i=0; i<problem.getSwarmSize(); i++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				// step 3 - update velocity
				double[] newVel = new double[problem.getProblemDimension()];
				newVel[0] = (w * p.getVelocity().getPos()[0]) + 
							(r1 * problem.getC1()) * (pBestLocation.get(i).getLoc()[0] - p.getLocation().getLoc()[0]) +
							(r2 * problem.getC2()) * (gBestLocation.getLoc()[0] - p.getLocation().getLoc()[0]);
				newVel[1] = (w * p.getVelocity().getPos()[1]) + 
							(r1 * problem.getC1()) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +
							(r2 * problem.getC2()) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1]);
				Velocity vel = new Velocity(newVel);
				p.setVelocity(vel);
				
				// step 4 - update location
				double[] newLoc = new double[problem.getProblemDimension()];
				newLoc[0] = p.getLocation().getLoc()[0] + newVel[0];
				newLoc[1] = p.getLocation().getLoc()[1] + newVel[1];
				Location loc = new Location(newLoc);
				p.setLocation(loc);
			}
			
			err = ProblemSet.evaluate(gBestLocation) - 0; // minimizing the functions means it's getting closer to 0
			
			
			System.out.println("ITERATION " + t + ": ");
			System.out.println("     Best X: " + gBestLocation.getLoc()[0]);
			System.out.println("     Best Y: " + gBestLocation.getLoc()[1]);
			System.out.println("     Value: " + ProblemSet.evaluate(gBestLocation));
			
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
			double[] loc = new double[problem.getProblemDimension()];
			
			loc[0] = problem.getLocationMinimum()[0] + generator.nextDouble() * (problem.getLocationMaximum()[0] - problem.getLocationMinimum()[0]);
			loc[1] = problem.getLocationMinimum()[1] + generator.nextDouble() * (problem.getLocationMaximum()[1] - problem.getLocationMinimum()[1]);
			Location location = new Location(loc);
			
			// randomize velocity in the range defined in Problem Set
			double[] vel = new double[problem.getProblemDimension()];
			vel[0] = problem.getMinimumVelocity() + generator.nextDouble() * (problem.getMaximumVelocity() - problem.getMinimumVelocity());
			vel[1] = problem.getMinimumVelocity() + generator.nextDouble() * (problem.getMaximumVelocity() - problem.getMinimumVelocity());
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
