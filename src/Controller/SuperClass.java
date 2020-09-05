/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import java.io.IOException;
import Entity.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList; 
import java.util.Random; 

/**
 *
 * @author Windows 10
 */
public class SuperClass{
    private List<Twitter> ContentTweetList;
    private List<Twitter> ShuffledContentTweetList;
    Twitter twit = new Twitter();
    Twitter shuffled_twit = new Twitter();
    TermList termlist = new TermList();
    Tag tag = new Tag();
    Model model = new Model();
    Twitter twitCV[] = new Twitter[10];
    Twitter twit_testingCV[] = new Twitter[10];
    TermList termlistCV[] = new TermList[10];
    Tag tagCV[] = new Tag[10];
    Model modelCV[] = new Model[10];
    
    
    public SuperClass(String selectedPath) throws IOException{
        DocumentReader dr = new DocumentReader(selectedPath, twit);
        try{
        dr.readTwitter();
        } catch (NullPointerException e) {
            System.out.println("format file not .txt, Failed");
        }
        //System.out.println(a.cetak());
    }
    
    public void run() throws IOException{
        setShuffledTweet();
        twit_testingCV=make10FoldCrossValidation();
    }
    
    public void train(int i) throws IOException{
        modelCV=doCrossValidation(i);
    }
    
    public StringBuilder ReadDoc(){
        ContentTweetList = twit.getContentTweetList();
        StringBuilder forprinting = new StringBuilder();
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);    
            forprinting.append(temptwit.getClassSentiment()+" "+temptwit.getContentTweet()+"\n");
        }
        return forprinting;
    }
    
    public void doNormalization(Twitter twit) throws IOException{
        Normalization go = new Normalization(twit);
        go.run();
    }
    
    public void doPreprocess(Twitter twit){
        Preprocessing go = new Preprocessing(twit);
        go.run();
    }
    
    public void doPOSTagging(Twitter twit){
        POSTagging go = new POSTagging(twit);
        go.run();
    }
    
    public void doTokenizing(Twitter twit, TermList termlist, Tag tag){
        TokenizeTags go = new TokenizeTags(twit, termlist, tag);
        go.run();
    }
    
    public void doNegationHandling(Twitter twit){
        NegationHandling go = new NegationHandling(twit);
        go.run();
    }
    
    public void doNHTokenizing(Twitter twit, TermList termlist, Tag tag){
        TokenizeNHTags go = new TokenizeNHTags(twit, termlist, tag);
        go.run();
    }
    
    public void doWeighting(Twitter twit, TermList termlist, Tag tag){
        Weighting go = new Weighting(twit, termlist, tag);
        go.run();
    }
    
    public Model CreateProbabilisticModel(Twitter twit, TermList termlist, Model model){
        ProbabilisticModel go = new ProbabilisticModel(twit, termlist, model);
        go.run();
        return go.getModel();
    }
    
    public StringBuilder printSomething(){
        StringBuilder forprinting = new StringBuilder();
        String[] TaggedTweet = Arrays.copyOf(twit.getTaggedTweet(), twit.getTaggedTweet().length);
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);    
            forprinting.append("["+temptwit.getClassSentiment()+"]"+" "+TaggedTweet[i]+"\n");
        }
        return forprinting;
    }
    
    public StringBuilder printAnother(){
        StringBuilder forprinting = new StringBuilder();
        String[] NHTaggedTweet = Arrays.copyOf(twit.getNHTaggedTweet(), twit.getNHTaggedTweet().length);
        int[] NHclassSentiment = Arrays.copyOf(twit.getNHclassSentiment(), twit.getNHclassSentiment().length);
        for(int i=0; i< ContentTweetList.size();i++) {    
            forprinting.append("["+NHclassSentiment[i]+"]"+" "+NHTaggedTweet[i]+"\n");
        }
        return forprinting;
    }
        
    public void setShuffledTweet(){
        List<Twitter> tempTweetList1;
        List<Twitter> tempTweetList2 = new ArrayList();
        tempTweetList1 = twit.getContentTweetList();
        tempTweetList2.addAll(tempTweetList1);
        Random rand = new Random(1);
        for(int i=0; i< twit.getContentTweetList().size();i++) {
            // take a random index between 0 to size  
            // of given List 
            int randomIndex = rand.nextInt(tempTweetList2.size()); 
            // add element in temporary list 
            Twitter temptwit = tempTweetList2.get(randomIndex);
            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
            shuffled_twit.setContentTweetList(myTwit);
            
            // Remove selected element from orginal list 
            tempTweetList2.remove(randomIndex); 
        }
    }
    
    public StringBuilder ReadShuffledDoc(){
        ShuffledContentTweetList = shuffled_twit.getContentTweetList();
        StringBuilder forprinting = new StringBuilder();
        for(int i=0; i< ShuffledContentTweetList.size();i++) {
            Twitter temptwit = ShuffledContentTweetList.get(i);    
            forprinting.append(temptwit.getClassSentiment()+" "+temptwit.getContentTweet()+"\n");
        }
        return forprinting;
    }
            
    public Twitter[] make10FoldCrossValidation(){
        //this will make twit into ten array of twit?
        ShuffledContentTweetList = shuffled_twit.getContentTweetList();
        for(int i=0;i<10;i++){
            twitCV[i] = new Twitter();
            twit_testingCV[i] = new Twitter();
            for(int j=0; j<ShuffledContentTweetList.size();j++) {
                switch(i){
                    case 0:{
                        if(j >= 0 && j < 50){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 1:{
                        if(j >= 50 && j < 100){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 2:{
                        if(j >= 100 && j < 150){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 3:{
                        if(j >= 150 && j < 200){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 4:{
                        if(j >= 200 && j < 250){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 5:{
                        if(j >= 250 && j < 300){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 6:{
                        if(j >= 300 && j < 350){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 7:{
                        if(j >= 350 && j < 400){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 8:{
                        if(j >= 400 && j < 450){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                    case 9:{
                        if(j >= 450 && j < 500){
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twit_testingCV[i].setContentTweetList(myTwit);
                            break;
                        }
                        else{
                            Twitter temptwit = ShuffledContentTweetList.get(j); 
                            Twitter myTwit = new Twitter(temptwit.getContentTweet(), temptwit.getClassSentiment());
                            twitCV[i].setContentTweetList(myTwit);
                            break;
                        }
                    }
                }
            }
        }
        return twit_testingCV;
    }
    
    public Model[] doCrossValidation(int i) throws IOException{
        termlistCV[i] = new TermList();
        tagCV[i] = new Tag();
        modelCV[i] = new Model();
        doNormalization(twitCV[i]);
        doPreprocess(twitCV[i]);
        doPOSTagging(twitCV[i]);
        doTokenizing(twitCV[i], termlistCV[i], tagCV[i]);
        doNegationHandling(twitCV[i]);
        doNHTokenizing(twitCV[i], termlistCV[i], tagCV[i]);
        doWeighting(twitCV[i], termlistCV[i], tagCV[i]);
        modelCV[i]=CreateProbabilisticModel(twitCV[i], termlistCV[i], modelCV[i]);
        System.out.println("Iterasi ke-"+(i+1)+" = done.");
        return modelCV;
    }
    
    public Twitter[] getTestingTweet(){
        return twit_testingCV;
    }
    
    public TermList[] getTermList(){
        return termlistCV;
    }
    
    public Model[] getModel(){
        return modelCV;
    }
}
