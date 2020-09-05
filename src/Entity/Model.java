/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Windows 10
 */
public class Model {
    private double[] priorProbability;
    private double[] NHpriorProbability;
    private double[][] conditionalProbability;
    private double[][] NHconditionalProbability;

    public double[] getPriorProbability() {
        return priorProbability;
    }

    public void setPriorProbability(double[] priorProbability) {
        this.priorProbability = priorProbability;
    }

    public double[] getNHpriorProbability() {
        return NHpriorProbability;
    }

    public void setNHpriorProbability(double[] NHpriorProbability) {
        this.NHpriorProbability = NHpriorProbability;
    }

    public double[][] getConditionalProbability() {
        return conditionalProbability;
    }

    public void setConditionalProbability(double[][] conditionalProbability) {
        this.conditionalProbability = conditionalProbability;
    }

    public double[][] getNHconditionalProbability() {
        return NHconditionalProbability;
    }

    public void setNHconditionalProbability(double[][] NHconditionalProbability) {
        this.NHconditionalProbability = NHconditionalProbability;
    }
    
    
}
