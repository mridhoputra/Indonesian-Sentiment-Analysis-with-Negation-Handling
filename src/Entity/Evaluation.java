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
public class Evaluation {
    int[] result_classSentiment;
    int[] result_NHclassSentiment;
    double accuracy;
    double recall;
    double precision;
    double f_measure;
    double NH_accuracy;
    double NH_recall;
    double NH_precision;
    double NH_f_measure;

    public int[] getResult_classSentiment() {
        return result_classSentiment;
    }

    public void setResult_classSentiment(int[] result_classSentiment) {
        this.result_classSentiment = result_classSentiment;
    }

    public int[] getResult_NHclassSentiment() {
        return result_NHclassSentiment;
    }

    public void setResult_NHclassSentiment(int[] result_NH_classSentiment) {
        this.result_NHclassSentiment = result_NH_classSentiment;
    }
    
    

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getF_measure() {
        return f_measure;
    }

    public void setF_measure(double f_measure) {
        this.f_measure = f_measure;
    }

    public double getNH_accuracy() {
        return NH_accuracy;
    }

    public void setNH_accuracy(double NH_accuracy) {
        this.NH_accuracy = NH_accuracy;
    }

    public double getNH_recall() {
        return NH_recall;
    }

    public void setNH_recall(double NH_recall) {
        this.NH_recall = NH_recall;
    }

    public double getNH_precision() {
        return NH_precision;
    }

    public void setNH_precision(double NH_precision) {
        this.NH_precision = NH_precision;
    }

    public double getNH_f_measure() {
        return NH_f_measure;
    }

    public void setNH_f_measure(double NH_f_measure) {
        this.NH_f_measure = NH_f_measure;
    }
        
        
}
