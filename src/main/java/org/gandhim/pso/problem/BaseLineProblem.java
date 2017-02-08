package org.gandhim.pso.problem;

/**
 * Created by mlpr2 on 8/02/17.
 */
public class BaseLineProblem implements PSOProblemSet {

    @Override
    public int getProblemDimension() {
        return 2;
    }

    @Override
    public int getSwarmSize() {
        return 30;
    }

    @Override
    public double getW(int iteration) {
        double w_UPPERBOUND = 1.0;
        double w_LOWERBOUND = 0.0;

        return w_UPPERBOUND - (((double) iteration) / getMaximumIterations()) * (w_UPPERBOUND - w_LOWERBOUND);
    }

    @Override
    public double getC1() {
        return 2;
    }

    @Override
    public double getC2() {
        return 2;
    }

    @Override
    public double getMinimumVelocity() {
        return -1;
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
        return 100;
    }

    @Override
    public double[] getLocationMinimum() {
        return new double[] {1, -1};
    }

    @Override
    public double[] getLocationMaximum() {
        return new double[] {4, 1};
    }

    @Override
    public double evaluate(double[] location) {
        double result = 0;
        double x = location[0]; // the "x" part of the location
        double y = location[1]; // the "y" part of the location

        result = Math.pow(2.8125 - x + x * Math.pow(y, 4), 2) +
                Math.pow(2.25 - x + x * Math.pow(y, 2), 2) +
                Math.pow(1.5 - x + x * y, 2);

        return result;
    }

    @Override
    public double getOptimum() {
        return 0;
    }
}
