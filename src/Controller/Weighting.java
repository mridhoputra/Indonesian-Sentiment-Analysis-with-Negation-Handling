/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.*;
import java.util.Arrays;

/**
 *
 * @author Windows 10
 * @honored_author Mohamed Guendouz
 */
public class Weighting {
    Twitter twit;
    TermList termlist;
    Tag tag;
    private String[] TaggedTweet;
    private String[] NHTaggedTweet;

    public Weighting(Twitter twit, TermList termlist, Tag tag){
        this.twit = twit;
        this.termlist = termlist;
        this.tag = tag;
    }
    
    public void run(){
        //membuat hasil tfidf dalam bentuk matriks, panjang baris adalah size doc (jumlah seluruh tweet)
        //panjang kolom adalah size seluruh term
        TaggedTweet = Arrays.copyOf(twit.getTaggedTweet(), twit.getTaggedTweet().length);
        NHTaggedTweet = Arrays.copyOf(twit.getNHTaggedTweet(), twit.getNHTaggedTweet().length);
                      
        //tf[][] = [tweet-i][term-i]
        double tf[][] = new double[twit.getTaggedTweet().length][termlist.getTotalTerm()];
        double NHtf[][] = new double[twit.getNHTaggedTweet().length][termlist.getTotalNHTerm()];
        
        for(int i = 0; i< TaggedTweet.length; i++){
            for(int j = 0; j < termlist.getTotalTerm(); j++){
                tf[i][j] = tf(TaggedTweet[i],termlist.getTermList().get(j));
            }
        }
        
        for(int i = 0; i< NHTaggedTweet.length; i++){
            for(int j = 0; j < termlist.getTotalNHTerm(); j++){
                NHtf[i][j] = tf(NHTaggedTweet[i],termlist.getNHTermList().get(j));
            }
        }
        
        //idf[] = [term-i]
        double idf[] = new double[termlist.getTotalTerm()];
        double NHidf[] = new double[termlist.getTotalNHTerm()];
        for(int i = 0; i< termlist.getTotalTerm(); i++){
            idf[i] = idf(TaggedTweet,termlist.getTermList().get(i));
        }
        for(int i = 0; i< termlist.getTotalNHTerm(); i++){
            NHidf[i] = idf(NHTaggedTweet,termlist.getNHTermList().get(i));
        }
        
        //tfidf[][] = [tweet-i][term-i]
        double tfidf[][] = new double[twit.getTaggedTweet().length][termlist.getTotalTerm()];
        double NHtfidf[][] = new double[twit.getNHTaggedTweet().length][termlist.getTotalNHTerm()];
        for(int i = 0; i< TaggedTweet.length; i++){
            for(int j = 0; j < termlist.getTotalTerm(); j++){
                tfidf[i][j] = tfidf(tf[i][j],idf[j]);
            }
        }
        for(int i = 0; i< NHTaggedTweet.length; i++){
            for(int j = 0; j < termlist.getTotalNHTerm(); j++){
                NHtfidf[i][j] = tfidf(NHtf[i][j],NHidf[j]);
            }
        }
        
        //set tfidf and NHtifidf to entity
        termlist.setTermWeight(tfidf);
        termlist.setNHTermWeight(NHtfidf);
    }

    /**
     * @param doc  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(String kalimat, String term) {
        double n = 0;
        double w = 0;
        double result;
        
        String[] unTag = kalimat.split("\\s+");
        for(int i=0;i<unTag.length;i++){
            String[] tokenized = unTag[i].split("/");
            //check if term is exist in a kalimat (sentence)
            if(tokenized[0].equalsIgnoreCase(term)){
                n++;
                //set term weight
                if(tokenized[1].contains("JJ")) w = 4;
                else if(tokenized[1].contains("RB")) w = 4;
                else if(tokenized[1].contains("VB")) w = 2;  
                else if(tokenized[1].contains("NN")) w = 2;  
                else w = 1;
            }
               
        }
        
        result = n * w;
        return result;
    }

    /**
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(String[] docs, String term) {
        double n = 0;
        
        for(int i=0;i<docs.length;i++){
            String[] taggedtoken = docs[i].split("\\s+");
            for (int j=0;j<taggedtoken.length;j++) {
                String[] token = taggedtoken[j].split("/");
                if (term.equalsIgnoreCase(token[0])) {
                    n++;
                    break;
                }
            }
        }
        if(n==0) return 0;
        else return Math.log(docs.length / n);
    }

    /**
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfidf(double tf, double idf) {
        return tf*idf;
    }
}
