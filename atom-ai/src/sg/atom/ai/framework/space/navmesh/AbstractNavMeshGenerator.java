/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.navmesh;

import com.jme3.scene.Mesh;

/**
 *
 * @author cuongnguyen
 */
public interface AbstractNavMeshGenerator {

    public Mesh optimize(Mesh mesh);
    
}
