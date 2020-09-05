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
public class TermList {
    private List<String> TermList = new ArrayList<String>();
    private List<String> NHTermList = new ArrayList<String>();
    double TermWeight[][];
    double NHTermWeight[][];

    public List<String> getTermList() {
        return TermList;
    }

    public void setTermList(List<String> TermList) {
        this.TermList = TermList;
    }

    public List<String> getNHTermList() {
        return NHTermList;
    }

    public void setNHTermList(List<String> NHTermList) {
        this.NHTermList = NHTermList;
    }
              
    public int getTotalTerm(){
        return TermList.size();
    }
    
    public int getTotalNHTerm(){
        return NHTermList.size();
    }

    public double[][] getTermWeight() {
        return TermWeight;
    }

    public void setTermWeight(double[][] TermWeight) {
        this.TermWeight = TermWeight;
    }

    public double[][] getNHTermWeight() {
        return NHTermWeight;
    }

    public void setNHTermWeight(double[][] NHTermWeight) {
        this.NHTermWeight = NHTermWeight;
    }
    
    
    
}
