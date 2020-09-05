/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Entity.Twitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Windows 10
 */
public class Preprocessing {
    Twitter twit;
    String[] normalizedtweet;
    String[] PreprocessedTweet;
    
    public Preprocessing(Twitter twit){
        this.twit = twit;
    }
    
    public void run(){
        normalizedtweet = Arrays.copyOf(twit.getNormalizedTweet(), twit.getNormalizedTweet().length);
        String[] stopword = new String[normalizedtweet.length];
        String[] stem = new String[stopword.length];
        String[] casefold = new String[stem.length];
        PreprocessedTweet = new String[casefold.length];
        
        for(int i=0; i< normalizedtweet.length; i++){
            stopword[i] = stopwording(normalizedtweet[i]);
            stem[i] = stemming(stopword[i]);
            casefold[i] = casefolding(stem[i]);
            PreprocessedTweet[i] = casefold[i];
        }
        twit.setPreprocessedTweet(PreprocessedTweet);
    }
    
    public String casefolding(String normalizedtweet){
        normalizedtweet = normalizedtweet.toLowerCase();
        return normalizedtweet;
    }
    
    public String stopwording(String normalizedtweet){
        String[] token = normalizedtweet.split("\\s");
        List<String>listStopword = new ArrayList<String>();
        DocumentReader dr = new DocumentReader();
        
        listStopword = dr.readStopWord();
        
        for(int i=0; i<listStopword.size(); i++){
            for(int j=0; j<token.length;j++){
                if(listStopword.get(i).equalsIgnoreCase(token[j])){
                    token[j] = token[j].trim().replaceAll(token[j], "");
                }
            }
        }
        
        String result = "";
        if(token.length > 0){
            result = token[0];
            for (int i=1; i<token.length; i++) {
                result = result + " " +token[i];
            }
        }
        
        result = result.trim().replaceAll(" +", " ");
	return result;
    }
    
    public String stemming(String normalizedtweet){
        String[] token = normalizedtweet.split("\\s");
        Stemming doStem = new Stemming();
        for(int i=0;i<token.length;i++){
            token[i] = doStem.kataDasar(token[i]);
        }
        
        String result = "";
        if(token.length > 0){
            result = token[0];
            for (int i=1; i<token.length; i++) {
                result = result + " " +token[i];
            }
        }
        result = result.trim().replaceAll(" +", " ");
        return result;
    }
    
    public String tokenizing(String normalizedtweet){
        StringTokenizer st = new StringTokenizer(normalizedtweet);
        while (st.hasMoreTokens()){
            System.out.println(st.nextToken()+" ");
        }
        return normalizedtweet;
    }
}
