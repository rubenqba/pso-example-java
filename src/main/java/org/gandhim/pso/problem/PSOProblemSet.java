package org.gandhim.pso.problem;

public interface PSOProblemSet {

    int getProblemDimension();

    default int getSwarmSize() {
        return 30;
    }

    default double getW(int iteration)  {
        double w_UPPERBOUND = 1.0;
        double w_LOWERBOUND = 0.0;

        return w_UPPERBOUND - (((double) iteration) / getMaximumIterations()) * (w_UPPERBOUND - w_LOWERBOUND);
    }

    default double getC1() {
        return 2;
    }

    default double getC2() {
        return 2;
    }

    default double getMinimumVelocity() {
        return -1;
    }

    default double getMaximumVelocity() {
        return 1;
    }

    default double getErrorTolerance() {
        return 1E-20;
    }

    default int getMaximumIterations() {
        return 100;
    }

    double[] getLocationMinimum();
    double[] getLocationMaximum();

    double evaluate(double[] location);

    double getOptimum();
}