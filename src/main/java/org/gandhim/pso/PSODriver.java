package org.gandhim.pso;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// this is a driver class to execute the PSO process

import org.gandhim.pso.problem.SphereProblem;

public class PSODriver {
	public static void main(String args[]) {
			new Swarm(new SphereProblem()).execute();
	}
}
