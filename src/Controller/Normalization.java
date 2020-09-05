/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Entity.Twitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class Normalization {
    Twitter twit;
    String[] normalizedtweet;
    private List<Twitter> ContentTweetList;
    
    public Normalization(Twitter twit){
        this.twit = twit;
    }
    
    public void run() throws IOException{
        ContentTweetList = twit.getContentTweetList();
        String[] p = new String[ContentTweetList.size()];
        String[] u = new String[ContentTweetList.size()];
        String[] n = new String[ContentTweetList.size()];
        for(int i=0; i< ContentTweetList.size();i++) {
            Twitter temptwit = ContentTweetList.get(i);
            p[i] = removePunctuation(temptwit.getContentTweet());
            u[i] = removeUnimportantWords(p[i]);
            n[i] = dictionaryFormalize(u[i]);  
        }
        normalizedtweet = n;
        twit.setNormalizedTweet(normalizedtweet);
    }
    
    private String removePunctuation(String tweet){
        String P = "[!\"$%&'()*\\+,.;:/<=>?\\[\\]^~_\\`{|}…0987654321]";
        return tweet.replaceAll(P, "");
    }
    
    private String removeUnimportantWords(String tweet){
        String temp = tweet;
        String A = "#[A-Za-z]+";
        String B = "@[A-Za-z]+";
        String C = "http[A-Za-z]+";
        String D = "https[A-Za-z]+";
        String E = "-[A-Za-z]+";
        String F = "pictwitter[A-Za-z]+";
        String G = "www[A-Za-z]+";
        String H = "cc[A-Za-z]+";
        String I = "via";
        String J = "com[A-Za-z]+";
        String K = "[^\\p{ASCII}]";
        temp = temp.replaceAll(A, "");
        temp = temp.replaceAll(B, "");
        temp = temp.replaceAll(C, "");
        temp = temp.replaceAll(D, "");
        temp = temp.replaceAll(E, "");
        temp = temp.replaceAll(F, "");
        temp = temp.replaceAll(G, "");
        temp = temp.replaceAll(H, "");
        temp = temp.replaceAll(I, "");
        temp = temp.replaceAll(J, "");
        temp = temp.replaceAll(K, "");
        temp = temp.trim().replaceAll(" +", " ");
        return temp;
        
    }
    
        private String dictionaryFormalize(String tweet) throws IOException {
        
        String[] token = tweet.split("\\s");
        
        DocumentReader dr = new DocumentReader();
        String[][] kamusFormal = dr.readKamusFormal();
        
        for(int i=0;i<token.length;i++){
            for(int j=0;j<kamusFormal.length;j++){
                if(token[i].equals(kamusFormal[j][0])){

                    token[i] = token[i].toLowerCase().replaceAll(token[i], kamusFormal[j][1]);
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
        
        return result;
    }
     
    public StringBuilder cetak(){
        StringBuilder result = new StringBuilder();
        
        for(int i=0; i<normalizedtweet.length; i++){
            result.append(normalizedtweet[i]+ "\n");
        }
        
        return result;
    }
}
