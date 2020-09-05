/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Twitter {
    private String ContentTweet;
    private int classSentiment;
    private List<Twitter> ContentTweetList = new ArrayList<Twitter>();
    private String[] NormalizedTweet;
    private String[] PreprocessedTweet;
    private String[] TaggedTweet;
    private int[] NHclassSentiment;
    private String[] NHTaggedTweet;
    private int[] isNH;
        
    public Twitter(){
        
    }
    
    public Twitter(String ContentTweet){
        this.ContentTweet = ContentTweet;
    }

    public Twitter(String ContentTweet, int classSentiment){
        this.ContentTweet = ContentTweet;
        this.classSentiment = classSentiment;
    }
    
    public String getContentTweet() {
        return ContentTweet;
    }

    public void setContentTweet(String ContentTweet) {
        this.ContentTweet = ContentTweet;
    }
    
    public int getClassSentiment(){
        return classSentiment;
    }
    
    public void setClassSentiment(int classSentiment){
        this.classSentiment = classSentiment;
    }
    
    public List<Twitter> getContentTweetList(){
        return ContentTweetList;
    }
    
    public void setContentTweetList(Twitter twit){
        ContentTweetList.add(twit);
    }
    
    public String[] getNormalizedTweet() {
        return NormalizedTweet;
    }

    public void setNormalizedTweet(String[] NormalizedTweet) {
        this.NormalizedTweet = NormalizedTweet;
    }
    
    public String[] getPreprocessedTweet() {
        return PreprocessedTweet;
    }

    public void setPreprocessedTweet(String[] PreprocessedTweet) {
        this.PreprocessedTweet = PreprocessedTweet;
    }
    
    public String[] getTaggedTweet() {
        return TaggedTweet;
    }

    public void setTaggedTweet(String[] TaggedTweet) {
        this.TaggedTweet = TaggedTweet;
    }

    public int[] getNHclassSentiment() {
        return NHclassSentiment;
    }

    public void setNHclassSentiment(int[] NHclassSentiment) {
        this.NHclassSentiment = NHclassSentiment;
    }

    public String[] getNHTaggedTweet() {
        return NHTaggedTweet;
    }

    public void setNHTaggedTweet(String[] NHTaggedTweet) {
        this.NHTaggedTweet = NHTaggedTweet;
    }

    public int[] getIsNH() {
        return isNH;
    }

    public void setIsNH(int[] isNH) {
        this.isNH = isNH;
    }
    
    @Override
    public String toString(){
        return ContentTweet;
    }
    
}
