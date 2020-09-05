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
public class TokenizeTags {
    Twitter twit;
    TermList termlist;
    Tag tag;
    String[] TaggedTweet;
    List<String> TermList = new ArrayList<String>();
    List<String> Tag = new ArrayList<String>();
    
    public TokenizeTags(Twitter twit, TermList termlist, Tag tag){
        this.twit = twit;
        this.termlist = termlist;
        this.tag = tag;
    }
    
    public void run(){
        TaggedTweet = Arrays.copyOf(twit.getTaggedTweet(), twit.getTaggedTweet().length);
        for(int i=0; i< TaggedTweet.length; i++){
            tokenize(TaggedTweet[i]);
        }
        TermList = new ArrayList<String>(new LinkedHashSet<String>(TermList));
        Collections.sort(TermList);
        Tag = new ArrayList<String>(new LinkedHashSet<String>(Tag));
        Collections.sort(Tag);
        termlist.setTermList(TermList);
        tag.setTag(Tag);
    }
    
    public void tokenize(String taggedtweet){
        String[] TaggedWord = taggedtweet.split("\\s+");
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            String word = tokenized[0];
            String tag = tokenized[1].replaceAll(",", "");
            TermList.add(word);
            Tag.add(tag);
        }
    }
    
}
