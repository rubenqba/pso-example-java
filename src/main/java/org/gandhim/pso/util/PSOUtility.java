package org.gandhim.pso.util;

/* author: gandhi - gandhi.mtm [at] gmail [dot] com - Depok, Indonesia */

// just a simple utility class to find a minimum position on a list

import org.gandhim.pso.data.Location;
import org.gandhim.pso.data.Velocity;
import org.gandhim.pso.problem.PSOProblemSet;

import java.util.stream.IntStream;

public class PSOUtility {
    public static int getMinPos(double[] list) {
        int pos = IntStream.range(0, list.length)
                .reduce((i, j) -> list[i] > list[j] ? j : i)
                .getAsInt();  // or throw
        return pos;
    }

    public static Location randomLocation(PSOProblemSet problem) {
        double[] loc = new double[problem.getProblemDimension()];
        IntStream.range(0, problem.getProblemDimension())
                .forEach(j -> {
                    loc[j] = problem.getLocationMinimum()[j]
                            + RandomGenerator.getInstance().getRandom().nextDouble()
                            * (problem.getLocationMaximum()[j] - problem.getLocationMinimum()[j]);
                });

        return new Location(loc);
    }

    public static Velocity randomVelocity(PSOProblemSet problem) {
        double[] vel = new double[problem.getProblemDimension()];
        IntStream.range(0, problem.getProblemDimension())
                .forEach(j -> {
                    vel[j] = problem.getMinimumVelocity()
                            + RandomGenerator.getInstance().getRandom().nextDouble()
                            * (problem.getMaximumVelocity() - problem.getMinimumVelocity());
                });

        return new Velocity(vel);
    }
}
