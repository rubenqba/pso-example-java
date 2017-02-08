package org.gandhim.pso.problem;

public interface PSOProblemSet {

    int getProblemDimension();
    int getSwarmSize();

    double getW(int iteration);
    double getC1();
    double getC2();

    double getMinimumVelocity();
    double getMaximumVelocity();

    double getErrorTolerance();
    int getMaximumIterations();

    double[] getLocationMinimum();
    double[] getLocationMaximum();

    double evaluate(double[] location);

    double getOptimum();
}