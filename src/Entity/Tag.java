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
 * @author Windows 10
 */
public class Tag {
    private List<String> Tag = new ArrayList<String>();
    private List<String> NHTag = new ArrayList<String>();

    public List<String> getTag() {
        return Tag;
    }

    public void setTag(List<String> Tag) {
        this.Tag = Tag;
    }

    public List<String> getNHTag() {
        return NHTag;
    }

    public void setNHTag(List<String> nhTag) {
        this.NHTag = nhTag;
    }
          
    public int getTotalTag(){
        return Tag.size();
    }
    
    public int getTotalNHTag(){
        return NHTag.size();
    }
}
