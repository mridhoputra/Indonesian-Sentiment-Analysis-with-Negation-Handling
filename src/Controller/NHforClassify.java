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
public class NHforClassify {
    Twitter twit_testing;
    String[] TaggedTweet;
    
    public NHforClassify(Twitter twit_testing){
        this.twit_testing = twit_testing;
    }
    
    public void run(){
        TaggedTweet = Arrays.copyOf(twit_testing.getTaggedTweet(), twit_testing.getTaggedTweet().length);
        String[] NHp = new String[TaggedTweet.length];
        int[] isNH = new int[TaggedTweet.length];
        
        for(int i=0; i< TaggedTweet.length; i++){
            isNH[i] = checkNH(TaggedTweet[i]);
            NHp[i] = negationhandling(TaggedTweet[i]);
        }
        
        twit_testing.setIsNH(isNH);
        twit_testing.setNHTaggedTweet(NHp);
    }
    
    public int checkNH(String kalimat){
        int isNH = 0;
        String[] TaggedWord = kalimat.split("\\s+");
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            //tag this data as a data that will use Negation Handling process if word with tag NEG found
            if(tokenized[1].contains("NEG")){
                isNH = 1;
                break;
            }
        }
        return isNH;
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
