/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.core.environment;

import com.jme3.bounding.BoundingBox;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Region {

    protected String name;
    protected BoundingBox bound;
    protected boolean entered;
    
    public Region(String name) {
        this.name = name;
        this.bound = null;
    }

    public Region(String name, BoundingBox bound) {
        this.name = name;
        this.bound = bound;
    }
}
