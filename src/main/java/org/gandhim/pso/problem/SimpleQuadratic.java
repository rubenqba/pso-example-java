package org.gandhim.pso.problem;

public class SimpleQuadratic  implements PSOProblemSet {
    @Override
    public int getProblemDimension() {
        return 1;
    }

    @Override
    public int getSwarmSize() {
        return 5;
    }

    @Override
    public double getW(int iteration) {
        return .9;
    }

    @Override
    public double getC1() {
        return .2;
    }

    @Override
    public double getC2() {
        return 3.5;
    }

    @Override
    public double getMinimumVelocity() {
        return 0;
    }

    @Override
    public double getMaximumVelocity() {
        return 1;
    }

    @Override
    public double getErrorTolerance() {
        return 1E-20;
    }

    @Override
    public int getMaximumIterations() {
        return 10;
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
