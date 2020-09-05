/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Tag;
import Entity.TermList;
import Entity.Twitter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Windows 10
 */
public class TokenizeNHTags {
    Twitter twit_testing;
    TermList termlist_testing;
    Tag tag_testing;
    String[] NHTaggedTweet;
    List<String> NHTermList = new ArrayList<String>();
    List<String> NHTag = new ArrayList<String>();
    
    public TokenizeNHTags(Twitter twit_testing, TermList termlist_testing, Tag tag_testing){
        this.twit_testing = twit_testing;
        this.termlist_testing = termlist_testing;
        this.tag_testing = tag_testing;
    }
    
    public void run(){
        NHTaggedTweet = Arrays.copyOf(twit_testing.getNHTaggedTweet(), twit_testing.getNHTaggedTweet().length);
        for(int i=0; i< NHTaggedTweet.length; i++){
            tokenize(NHTaggedTweet[i]);
        }
        NHTermList = new ArrayList<String>(new LinkedHashSet<String>(NHTermList));
        Collections.sort(NHTermList);
        NHTag = new ArrayList<String>(new LinkedHashSet<String>(NHTag));
        Collections.sort(NHTag);
        termlist_testing.setNHTermList(NHTermList);
        tag_testing.setNHTag(NHTag);
    }
    
    public void tokenize(String NHtaggedtweet){
        String[] NHTaggedWord = NHtaggedtweet.split("\\s+");
        for(int i=0;i<NHTaggedWord.length;i++){
            String[] tokenized = NHTaggedWord[i].split("/");
            String word = tokenized[0];
            String tag = tokenized[1].replaceAll(",", "");
            NHTermList.add(word);
            NHTag.add(tag);
        }
    }
    
}
