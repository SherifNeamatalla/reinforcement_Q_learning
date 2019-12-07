package model;

import interfaces.IEnvironmentController;

public class QLearningConfiguration {

  private double learningRate;

  private double gamma;

  private double epsilon;

  private double epsilonMin;

  private double epsilonDecay;

  private IEnvironmentController environmentController;

  public QLearningConfiguration(
      double learningRate,
      double gamma,
      double epsilon,
      double epsilonMin,
      double epsilonDecay,
      IEnvironmentController environmentController) {
    this.learningRate = learningRate;
    this.gamma = gamma;
    this.epsilon = epsilon;
    this.epsilonMin = epsilonMin;
    this.epsilonDecay = epsilonDecay;
    this.environmentController = environmentController;
  }

  public double getLearningRate() {
    return learningRate;
  }

  public void setLearningRate(double learningRate) {
    this.learningRate = learningRate;
  }

  public double getGamma() {
    return gamma;
  }

  public void setGamma(double gamma) {
    this.gamma = gamma;
  }

  public double getEpsilon() {
    return epsilon;
  }

  public void setEpsilon(double epsilon) {
    this.epsilon = epsilon;
  }

  public double getEpsilonMin() {
    return epsilonMin;
  }

  public void setEpsilonMin(double epsilonMin) {
    this.epsilonMin = epsilonMin;
  }

  public double getEpsilonDecay() {
    return epsilonDecay;
  }

  public void setEpsilonDecay(double epsilonDecay) {
    this.epsilonDecay = epsilonDecay;
  }

  public IEnvironmentController getEnvironmentController() {
    return environmentController;
  }

  public void setEnvironmentController(IEnvironmentController environmentController) {
    this.environmentController = environmentController;
  }
}
