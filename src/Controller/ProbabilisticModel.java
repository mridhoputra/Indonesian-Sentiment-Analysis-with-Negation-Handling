/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.*;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class ProbabilisticModel {
    Twitter twit;
    TermList termlist;
    Model model;
    private List<Twitter> ContentTweetList;
    String[] TaggedTweet;
    int[] classSentiment;
    int[] NHclassSentiment;
    double TermWeight[][];
    double NHTermWeight[][];
    private double[] priorProbability;
    private double[] NHpriorProbability;
    private double[][] conditionalProbability;
    private double[][] NHconditionalProbability;
    
    public ProbabilisticModel(Twitter twit, TermList termlist, Model model) {
        this.twit = twit;
        this.termlist = termlist;
        this.model = model;
    }
    
    public void run(){
        ContentTweetList = twit.getContentTweetList();
        classSentiment = new int[ContentTweetList.size()];
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);    
            classSentiment[i] = temptwit.getClassSentiment();
        }
        NHclassSentiment = twit.getNHclassSentiment();
        priorProbability = calculatePriorProbability(classSentiment);
        NHpriorProbability = calculatePriorProbability(NHclassSentiment);
        
        TermWeight = termlist.getTermWeight();
        NHTermWeight = termlist.getNHTermWeight();
        
        conditionalProbability = calculateConditionalProbability(classSentiment, termlist.getTotalTerm(), TermWeight);
        NHconditionalProbability = calculateConditionalProbability(NHclassSentiment, termlist.getTotalNHTerm(), NHTermWeight);

        model.setPriorProbability(priorProbability);
        model.setNHpriorProbability(NHpriorProbability);
        model.setConditionalProbability(conditionalProbability);
        model.setNHconditionalProbability(NHconditionalProbability);
    }

    public double[] calculatePriorProbability(int[] classData){       
        int classes[] = new int[]{
            0,
            1
        };
        
        double prior[] = new double[classes.length];
        int numberOfData = twit.getContentTweetList().size();
        double result = 0;
        
        for (int i = 0; i < classes.length; i++) {
            int classCount = classCount(classData,i);
            result = (double) (classCount + 1) / (numberOfData + classes.length);
            prior[i] = result; 
        }
        
        return prior;
    }
    
    public int classCount(int[]classdata, int classes){
        int count = 0;
        for(int i = 0; i < classdata.length; i++){
            if(classdata[i] == classes){
                count++;
            }
        }
        return count;
    }
    
    public double[][] calculateConditionalProbability(int[] classData, int termsize, double[][] weight) {
        int classes[] = new int[]{
            0,
            1
        };
  
        double countTerm;
        double totalweight;
        //conditional[term-i][class-j]
        double conditional[][] = new double[termsize][classes.length];
        
        for(int i=0; i < termsize; i++){
            for(int j=0 ; j < classes.length; j++){
                countTerm = countTermwithWeight(classData, termsize, weight, i, j);
                totalweight = countTotalWeight(classData, termsize, weight, j);
                conditional[i][j] = ( (countTerm + 1) / (totalweight + termsize) );
            }
        }
        return conditional;
    }
    
    public double countTermwithWeight(int[] classData, int termsize, double[][] weight, int term_number, int classes){
        double count = 0;
        
        for(int i = 0; i < classData.length; i++){
            //jika nilai class (0 atau 1) dari tweet ke-i sama dengan nilai class
            if(classData[i] == classes){
                count = count + weight[i][term_number];
            }
        }
        return count;
    }
    
    public double countTotalWeight(int[] classData, int termsize, double[][] weight, int classes){
        double count = 0;
        
        for(int i = 0; i< classData.length; i++){
            for(int j = 0; j < termsize; j++){
                //jika nilai class (0 atau 1) dari tweet ke-i sama dengan nilai class
                if(classData[i] == classes){
                    count = count + weight[i][j];
                }
            }
        }
        return count;
    }
    
    public Model getModel(){
        return model;
    }
    
}
