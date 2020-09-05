/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Twitter;
import NLP_ITB.POSTagger.HMM.Decoder.MainTagger;
import java.util.Arrays;

/**
 *
 * @author digitalcreative
 */
public class POSTagging {
    
    Twitter twit;
    String[] PreprocessedTweet;
    
    public POSTagging(Twitter twit){
        this.twit = twit;
    }
    
    public void run(){
        PreprocessedTweet = Arrays.copyOf(twit.getPreprocessedTweet(), twit.getPreprocessedTweet().length);
        String[] p = new String[PreprocessedTweet.length];
        for(int i=0; i< PreprocessedTweet.length; i++){
            p[i] = posttagging(PreprocessedTweet[i]);
        }
        twit.setTaggedTweet(p);
    }

    public String posttagging(String kalimat){
        
        int pass2 = 0;
        String fileLexicon = "src/resources/postagger/Lexicon.trn";
        String fileNGram = "src/resources/postagger/Ngram.trn";
        MainTagger mt = new MainTagger(fileLexicon, fileNGram, pass2);
        String tempresult1 = mt.taggingStr(kalimat).toString();
        String tempresult2 = tempresult1.substring(1, tempresult1.length()-1);
        String result = reduceUnusedTags(tempresult2);
        return result;
    }
    
    public String reduceUnusedTags(String tempresult2){
        String[] TaggedWord = tempresult2.split("\\s+");
        for(int i=0;i<TaggedWord.length;i++){
            String[] tokenized = TaggedWord[i].split("/");
            
            //change spesific tags: NN NNG NNP NNPP to only NN
            if(tokenized[1].contains("NN")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "NN,");
            }
            
            //change spesific tags: VBI VBT to only VB
            if(tokenized[1].contains("VB")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "VB,");
            }
            
            //change tags other than JJ VB RB NN NEG to OTHER
            if(!tokenized[1].contains("JJ") &&
               !tokenized[1].contains("VB") &&
               !tokenized[1].contains("RB") &&
               !tokenized[1].contains("NN") &&
               !tokenized[1].contains("NEG")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "OTHER,");
            }
            
            //fix wrong pos-tagged word
            if(tokenized[0].contains("takjub")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "JJ,");
            }
            if(tokenized[0].contains("taksi")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "NN,");
            }
            if(tokenized[0].contains("takut")){
                tokenized[1] = tokenized[1].replaceAll(tokenized[1], "JJ,");
            }
            TaggedWord[i] = tokenized[0]+"/"+tokenized[1];
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
