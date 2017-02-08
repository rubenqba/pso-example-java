package org.gandhim.pso.problem;

import java.util.Arrays;

/**
 * Created by ruben.bressler on 8/02/17.
 */
public class SphereProblem implements PSOProblemSet {
    @Override
    public int getProblemDimension() {
        return 2;
    }

    @Override
    public double[] getLocationMinimum() {
        return new double[] {-10, -10};
    }

    @Override
    public double[] getLocationMaximum() {
        return new double[] {10, 10};
    }

    @Override
    public double evaluate(double[] location) {
        return Arrays.stream(location)
                .map(l -> l*l)
                .sum();
    }

    @Override
    public double getOptimum() {
        return 0;
    }
}
