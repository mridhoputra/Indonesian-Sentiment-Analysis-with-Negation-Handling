/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Twitter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class NegationHandling {
    Twitter twit;
    private List<Twitter> ContentTweetList;
    String[] TaggedTweet;
    int[] classSentiment;
    
    public NegationHandling(Twitter twit){
        this.twit = twit;
    }
    
    public void run(){
        int total_NEG = 0;
        ContentTweetList = twit.getContentTweetList();
        classSentiment = new int[ContentTweetList.size()];
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);    
            classSentiment[i] = temptwit.getClassSentiment();
        }
        TaggedTweet = Arrays.copyOf(twit.getTaggedTweet(), twit.getTaggedTweet().length);
        String[] NHp = new String[TaggedTweet.length];
        int[] NHclassSentiment = new int[classSentiment.length];
        for(int i=0; i< TaggedTweet.length; i++){
            NHclassSentiment[i] = reversesentiment(TaggedTweet[i],classSentiment[i]);
            if(NHclassSentiment[i] != classSentiment[i]){
                total_NEG++;
            }
            NHp[i] = negationhandling(TaggedTweet[i]);
        }
        twit.setNHclassSentiment(NHclassSentiment);
        twit.setNHTaggedTweet(NHp);
    }
    
    public int reversesentiment(String kalimat, int classSentiment){
        int NHclassSentiment = classSentiment;
        String[] TaggedWord = kalimat.split("\\s+");
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            //reverse sentiment if word with tag NEG found
            if(tokenized[1].contains("NEG")){
                if(NHclassSentiment == 0){
                    NHclassSentiment = 1;
                }
                else if(NHclassSentiment == 1){
                    NHclassSentiment = 0;
                }
                break;
            }
        }
        return NHclassSentiment;
    }
    
    public String negationhandling(String kalimat){
        String[] TaggedWord = kalimat.split("\\s+");
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            
            //delete word with tag NEG
            if(tokenized[1].contains("NEG")){
                TaggedWord[i] = "";
            }
            else{
                TaggedWord[i] = tokenized[0]+"/"+tokenized[1];
            }
        }
        
        String result = "";
        if(TaggedWord.length > 0){
            result = TaggedWord[0];
            for (int i=1; i<TaggedWord.length; i++) {
                result = result + " " +TaggedWord[i];
            }
        }
        
        result = result.trim().replaceAll(" +", " ");

        return result;
    }
}
