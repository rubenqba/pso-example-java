package org.gandhim.pso.problem;

public class SimpleQuadratic  implements PSOProblemSet {
    @Override
    public int getProblemDimension() {
        return 1;
    }

    @Override
    public double[] getLocationMinimum() {
        return new double[]{-10};
    }

    @Override
    public double[]getLocationMaximum() {
        return new double[]{10};
    }

    @Override
    public double evaluate(double[] location) {
        return location[0] * location[0] + 2;
    }

    @Override
    public double getOptimum() {
        return 2;
    }
}
