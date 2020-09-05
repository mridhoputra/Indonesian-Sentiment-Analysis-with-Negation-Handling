/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class Classifier {
    Twitter twit_testing;
    private List<Twitter> ContentTweetList;
    TermList termlist_testing;
    Tag tag_testing;
    Model model;
    Evaluation evaluation;
    int[] classSentiment;
    int[] isNH;
    String[] TaggedTweet;
    String[] NHTaggedTweet;
    double[] priorProbability;
    double[][] conditionalProbability;
    double[] NHpriorProbability;
    double[][] NHconditionalProbability;

    public Classifier(Twitter twit_testing, TermList termlist_testing, Model model, Evaluation evaluation) {
        this.twit_testing = twit_testing;
        this.termlist_testing = termlist_testing;
        this.model = model;
        this.evaluation = evaluation;
    }
    
    public void run(){
        TaggedTweet = Arrays.copyOf(twit_testing.getTaggedTweet(), twit_testing.getTaggedTweet().length);
        NHTaggedTweet = Arrays.copyOf(twit_testing.getNHTaggedTweet(), twit_testing.getNHTaggedTweet().length);
        priorProbability = model.getPriorProbability();
        conditionalProbability = model.getConditionalProbability();
        NHpriorProbability = model.getNHpriorProbability();
        NHconditionalProbability = model.getNHconditionalProbability();
        int[] result_classSentiment = new int[TaggedTweet.length];
        int[] temp_result_NHclassSentiment = new int[NHTaggedTweet.length];
        int[] result_NHclassSentiment = new int[NHTaggedTweet.length];
        
        for(int i=0; i< TaggedTweet.length; i++){
            result_classSentiment[i] = MultiNaiveBayes(TaggedTweet[i], priorProbability, conditionalProbability);
        }
        
        for(int i=0;i<NHTaggedTweet.length;i++){
            temp_result_NHclassSentiment[i] = NHMultiNaiveBayes(NHTaggedTweet[i], NHpriorProbability, NHconditionalProbability);
        }
        result_NHclassSentiment = reverse_result(temp_result_NHclassSentiment);
        
        performance_measure(result_classSentiment, result_NHclassSentiment);
    }
    
    public int MultiNaiveBayes(String kalimat, double[] priorProbability, double[][] conditionalProbability)
    {
        String[] TaggedWord = kalimat.split("\\s+");
        double negative_Probability = Math.log(priorProbability[0]);
        double positive_Probability = Math.log(priorProbability[1]);
        
        int result;
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            negative_Probability = negative_Probability + getNegativeProbability(tokenized[0],conditionalProbability);
            positive_Probability = positive_Probability + getPositiveProbability(tokenized[0],conditionalProbability);
        }
        result = ArgMax(negative_Probability,positive_Probability);
        return result;
    }
    
    public int NHMultiNaiveBayes(String kalimat, double[] NHpriorProbability, double[][] NHconditionalProbability)
    {
        String[] TaggedWord = kalimat.split("\\s+");
        double negative_Probability = Math.log(NHpriorProbability[0]);
        double positive_Probability = Math.log(NHpriorProbability[1]);
        
        int result;
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            negative_Probability = negative_Probability + getNHNegativeProbability(tokenized[0], NHconditionalProbability);
            positive_Probability = positive_Probability + getNHPositiveProbability(tokenized[0], NHconditionalProbability);
        }
        result = ArgMax(negative_Probability, positive_Probability);
        return result;
    }
    
    public double getNegativeProbability(String token, double[][] conditionalProbability){
        double result = 0;
        for(int i = 0; i < termlist_testing.getTotalTerm(); i++){
            if(token.equalsIgnoreCase(termlist_testing.getTermList().get(i))){
                result = conditionalProbability[i][0];
                break;
            }
        }
        if(result == 0) result = 1;
        return Math.log(result);
    }
    
    public double getPositiveProbability(String token, double[][] conditionalProbability){
        double result = 0;
        for(int i = 0; i < termlist_testing.getTotalTerm(); i++){
            if(token.equalsIgnoreCase(termlist_testing.getTermList().get(i))){
                result = conditionalProbability[i][1];
                break;
            }
        }
        if(result == 0) result = 1;
        return Math.log(result);
    }
    
    public double getNHNegativeProbability(String token, double[][] NHconditionalProbability){
        double result = 0;
        for(int i = 0; i < termlist_testing.getTotalNHTerm(); i++){
            if(token.equalsIgnoreCase(termlist_testing.getNHTermList().get(i))){
                result = NHconditionalProbability[i][0];
                break;
            }
        }
        if(result == 0) result = 1;
        return Math.log(result);
    }
    
    public double getNHPositiveProbability(String token, double[][] NHconditionalProbability){
        double result = 0;
        for(int i = 0; i < termlist_testing.getTotalNHTerm(); i++){
            if(token.equalsIgnoreCase(termlist_testing.getNHTermList().get(i))){
                result = NHconditionalProbability[i][1];
                break;
            }
        }
        if(result == 0) result = 1;
        return Math.log(result);
    }
    
    public int ArgMax(double negative_Probability, double positive_Probability){
        int result = 1;
        if(negative_Probability > positive_Probability) result = 0;
        else result = 1;
        return result; 
    }
    
    public int[] reverse_result(int[] result_NHclassSentiment){
        int result[] = new int[twit_testing.getNHTaggedTweet().length];
        for(int i=0;i<result.length;i++){
            result[i]=result_NHclassSentiment[i];
        }
        isNH = twit_testing.getIsNH();
        for(int i=0;i<twit_testing.getNHTaggedTweet().length;i++){
            if(isNH[i] == 1){
                if(result_NHclassSentiment[i] == 1) result[i] = 0 ;
                else if(result_NHclassSentiment[i] == 0) result[i] = 1;
            }
        }
        return result;
    }
    
    public void performance_measure(int[] result_classSentiment, int[] result_NHclassSentiment){
        int totalData;
        
        //Confusion Matrix
        int TP = 0;
        int FP = 0;
        int TN = 0;
        int FN = 0;
        int NH_TP = 0;
        int NH_FP = 0;
        int NH_TN = 0;
        int NH_FN = 0;
        
        //performance measure
        double accuracy;
        double recall;
        double precision;
        double f_measure;
        double NH_accuracy;
        double NH_recall;
        double NH_precision;
        double NH_f_measure;
        
        ContentTweetList = twit_testing.getContentTweetList();
        totalData = ContentTweetList.size();
        
        classSentiment = new int[ContentTweetList.size()];
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);    
            classSentiment[i] = temptwit.getClassSentiment();
        }
        
        for(int i=0;i<totalData;i++){
            if(classSentiment[i] == 1){
                if(result_classSentiment[i] == 1){
                    TP++;
                }
                else if(result_classSentiment[i] == 0){
                    FN++;
                }
                
                if(result_NHclassSentiment[i] == 1){
                    NH_TP++;
                }
                else if(result_NHclassSentiment[i] == 0){
                    NH_FN++;
                }
            }
            else if(classSentiment[i] == 0){
                if(result_classSentiment[i] == 1){
                    FP++;
                }
                else if(result_classSentiment[i] == 0){
                    TN++;
                }
                
                if(result_NHclassSentiment[i] == 1){
                    NH_FP++;
                }
                else if(result_NHclassSentiment[i] == 0){
                    NH_TN++;
                }
            }
        }
        
        System.out.println("Total True Positive:  "+TP);
        System.out.println("Total True Negative:  "+TN);
        System.out.println("Total False Positive:  "+FP);
        System.out.println("Total False Negative:  "+FN);
        
        System.out.println("Total NH_True Positive:  "+NH_TP);
        System.out.println("Total NH_True Negative:  "+NH_TN);
        System.out.println("Total NH_False Positive:  "+NH_FP);
        System.out.println("Total NH_False Negative:  "+NH_FN);
        
        //performance measure for without negation handling
        accuracy = calculateAccuracy(TP, FN, FP, TN);
        recall = calculateRecall(TP, FN);
        precision = calculatePrecision(TP, FP);
        f_measure = calculateF_measure(recall, precision);
        
        //performance measure for negation handling
        NH_accuracy = calculateAccuracy(NH_TP, NH_FN, NH_FP, NH_TN);
        NH_recall = calculateRecall(NH_TP, NH_FN);
        NH_precision = calculatePrecision(NH_TP, NH_FP);
        NH_f_measure = calculateF_measure(NH_recall, NH_precision);
        
        System.out.println("Without Negation Handling");
        System.out.println("Accuracy: "+accuracy+"%");
        System.out.println("Recall: "+recall+"%");
        System.out.println("Precision: "+precision+"%");
        System.out.println("F-Measure: "+f_measure+"%");
        
        System.out.println("With Negation Handling");
        System.out.println("Accuracy: "+NH_accuracy+"%");
        System.out.println("Recall: "+NH_recall+"%");
        System.out.println("Precision: "+NH_precision+"%");
        System.out.println("F-Measure: "+NH_f_measure+"%");
        
        evaluation.setResult_classSentiment(result_classSentiment);
        evaluation.setAccuracy(accuracy);
        evaluation.setRecall(recall);
        evaluation.setPrecision(precision);
        evaluation.setF_measure(f_measure);
        
        evaluation.setResult_NHclassSentiment(result_NHclassSentiment);
        evaluation.setNH_accuracy(NH_accuracy);
        evaluation.setNH_recall(NH_recall);
        evaluation.setNH_precision(NH_precision);
        evaluation.setNH_f_measure(NH_f_measure);
    }
    
    public double calculateAccuracy(int TP, int FN, int FP, int TN){
        return (((double)(TP + TN)) / ((double)(TP + TN + FP + FN)) * 100);
    }
    
    public double calculateRecall(int TP, int FN){
        return (((double) TP) / ((double)(TP + FN)) * 100);
    }
    
    public double calculatePrecision(int TP, int FP){
        return (((double) TP) / ((double)(TP + FP)) * 100);
    }
    
    public double calculateF_measure(double recall, double precision){
        return (2* recall * precision) / (recall + precision);
    }
}
