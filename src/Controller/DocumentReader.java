/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Twitter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Asus
 */
public class DocumentReader {
    
    private String filePath;
    private Twitter twit;
    
    private static Map<Character, String[]> KamusKDid;
    private static List<Character> indexAlphabet;

    public DocumentReader(){
        
    }
    public DocumentReader(String filePath, Twitter twit){
        this.filePath = filePath;
        this.twit = twit;
    }
    
    public void readTwitter()throws FileNotFoundException, IOException{
        
        
        FileInputStream file = new FileInputStream(filePath);
        InputStreamReader isr = new InputStreamReader(file);
        
        if (isr != null){
            BufferedReader in = new BufferedReader(isr);
            
            String ContentTweet = new String();
            int classSentiment = 0;
                   
            String s = null;
            while((s = in.readLine()) != null){
                classSentiment = Character.getNumericValue(s.charAt(0));
                
                //number of the first line unreadable fix:
                if(classSentiment == -1){
                    classSentiment = Character.getNumericValue(s.charAt(1));
                }
                
                ContentTweet = s.substring(2, s.length());
                Twitter myTwit = new Twitter(ContentTweet, classSentiment);
                twit.setContentTweetList(myTwit);
                classSentiment = 0;
                
            }
            
            in.close();
            isr.close();
            file.close();
        }
    }
    
    public String[][] readKamusFormal() throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream("src/resources/bakutidakbaku.txt");
        InputStreamReader isr = new InputStreamReader(file);
        BufferedReader in = new BufferedReader(isr);
        
        Object tableLine[] = in.lines().toArray();
        int baris = tableLine.length;
        int kolom = tableLine[0].toString().trim().split(" ").length;
        
        String [][] normalize = new String[baris][kolom];
        for(int i=0; i<baris; i++) {
            String line = tableLine[i].toString().trim();
            String dataRow [] = line.split(" ");
            for(int j=0; j<kolom; j++) {
                normalize[i][j]=dataRow[j];
                                
            }
        }
        
        in.close();
        isr.close();
        file.close();
        
        return normalize;   
    }
    
    
    //Method dibawah digunakan untuk membaca Kamus Bahasa Indonesia
    
    public static void initKamusKDid() {
        
        String pathKamusKDid = "src/resources/kamusKDid/";
        Map<Character, String[]> tmpKamusKDid = new LinkedHashMap<Character, String[]>();
        
        try {
            for(char c = 'a'; c <= 'z'; c++){
                char idx = c;
                ArrayList<String> tmp = new ArrayList<String>();
                String line;
                FileInputStream file = new FileInputStream(pathKamusKDid+idx+".txt");
                InputStreamReader isr = new InputStreamReader(file);
                BufferedReader in = new BufferedReader(isr);
                while ((line = in.readLine()) != null) {
                    tmp.add(line);
                }
                tmpKamusKDid.put(idx, tmp.toArray(new String[tmp.size()]));
                
                in.close();
                isr.close();
                file.close();
            }
            
            String[] s = {"`"};
            tmpKamusKDid.put('`', s);
            KamusKDid = tmpKamusKDid;
        }
        catch(IOException e){
        }
        // init Index
        List<Character> tmpIndexAlphabet = new ArrayList<Character>();
        for(char c='a'; c<='z'; c++) {
            tmpIndexAlphabet.add(c);
        }
        tmpIndexAlphabet.add('`');
        indexAlphabet = tmpIndexAlphabet;
        
        
    }
    
    public static Map<Character, String[]> getKamusKDid() {
        return KamusKDid;
    }
    
    public static List<Character> getIndexAlphabet() {
        return indexAlphabet;
    }
    
    public List<String> readStopWord() {
        List<String>listStopword = new ArrayList<String>();
		
        try {

            FileInputStream file = new FileInputStream("src/resources/stopwordlist.txt");
            InputStreamReader isr = new InputStreamReader(file);
            BufferedReader in = new BufferedReader(isr);
            
            StringBuffer sb = new StringBuffer();
            String s = null;
            while((s = in.readLine()) != null){
                sb.append(s);
            }
            listStopword.addAll(Arrays.asList(sb.toString().split(";")));
            
            in.close();
            isr.close();
            file.close();

	} catch(FileNotFoundException ex) {
            
            System.out.println("File not found");
            
	} catch(IOException ex){
                   
            System.out.println("IOexception"); 
	}
        
        return listStopword;
    }
    
}
