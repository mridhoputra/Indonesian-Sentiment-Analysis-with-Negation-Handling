/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class Testing {
    Model model;
    private List<Twitter> ContentTweetList_testing;
    Twitter twit_testing = new Twitter();
    TermList termlist_testing = new TermList();
    TermList termlist_from_model;
    Tag tag_testing = new Tag();
    Evaluation evaluation = new Evaluation();
    
    public Testing(Twitter twit_testing, TermList termlist_from_model, Model model){
        this.twit_testing = twit_testing;
        this.termlist_from_model = termlist_from_model;
        this.model = model;
    }
    
    public void run() throws IOException{
        doNormalization();
        doPreprocess();
        doPOSTagging();
        doTokenizing();
        doNegationHandling();
        doNHTokenizing();
        doClassify();
        System.out.println("Done.");
    }
    
    public StringBuilder ReadDoc(){
        ContentTweetList_testing = twit_testing.getContentTweetList();
        StringBuilder forprinting = new StringBuilder();
        for(int i=0; i< ContentTweetList_testing.size();i++) {
            Twitter temptwit = ContentTweetList_testing.get(i);
            forprinting.append("["+temptwit.getClassSentiment()+"] "+temptwit.getContentTweet()+"\n");
        }
        return forprinting;
    }
    
    public void doNormalization() throws IOException{
        Normalization go = new Normalization(twit_testing);
        go.run();
    }
    
    public void doPreprocess(){
        Preprocessing go = new Preprocessing(twit_testing);
        go.run();
    }
    
    public void doPOSTagging(){
        POSTagging go = new POSTagging(twit_testing);
        go.run();
    }
    
    public void doTokenizing(){
        TokenizeTags go = new TokenizeTags(twit_testing, termlist_testing, tag_testing);
        go.run();
    }
    
    public void doNegationHandling(){
        NHforClassify go = new NHforClassify(twit_testing);
        go.run();
    }
    
    public void doNHTokenizing(){
        TokenizeNHTags go = new TokenizeNHTags(twit_testing, termlist_testing, tag_testing);
        go.run();
    }
    
    public void doClassify(){
        Classifier go = new Classifier(twit_testing, termlist_from_model, model, evaluation);
        go.run();
    }
    
    public StringBuilder printSomething(){
        StringBuilder forprinting = new StringBuilder();
        String[] TaggedTweet = Arrays.copyOf(twit_testing.getTaggedTweet(), twit_testing.getTaggedTweet().length);
        for(int i=0; i< ContentTweetList_testing.size();i++) {   
            forprinting.append("["+evaluation.getResult_classSentiment()[i]+"] "+TaggedTweet[i]+"\n");
        }
        return forprinting;
    }
    
    public StringBuilder printAnother(){
        StringBuilder forprinting = new StringBuilder();
        String[] NHTaggedTweet = Arrays.copyOf(twit_testing.getNHTaggedTweet(), twit_testing.getNHTaggedTweet().length);
        for(int i=0; i< ContentTweetList_testing.size();i++) {    
            forprinting.append("["+evaluation.getResult_NHclassSentiment()[i]+"] "+NHTaggedTweet[i]+"\n");
        }
        return forprinting;
    }
    
    public double getPrecision(){
        return evaluation.getPrecision();
    }
    
    public double getNH_precision(){
        return evaluation.getNH_precision();
    }
    
    public double getRecall(){
        return evaluation.getRecall();
    }
    
    public double getNH_recall(){
        return evaluation.getNH_recall();
    }
    
    public double getF_measure(){
        return evaluation.getF_measure();
    }
    
    public double getNH_f_measure(){
        return evaluation.getNH_f_measure();
    }
    
    public double getAccuracy(){
        return evaluation.getAccuracy();
    }
    
    public double getNH_accuracy(){
        return evaluation.getNH_accuracy();
    }
}
