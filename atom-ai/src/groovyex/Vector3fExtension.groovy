/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package groovyex

import com.jme3.math.Vector3f
import com.jme3.math.Quaternion
/**
 *
 * @author cuong.nguyenmanh2
 */
public class Vector3fExtension {
    public static Vector3f plus(Vector3f self,Vector3f a){
        self.add(a)
    }
    
    public static Vector3f multiply(Vector3f self,Vector3f a){
        self.mult(a)
    }
    public static Vector3f multiply(Vector3f self,float a){
        self.mult(a)
    }
    public static Vector3f multiply(Vector3f self,double a){
        self.mult((float)a)
    }
    public static Vector3f multiply(Vector3f self,int a){
        self.mult((float)a)
    }
    public static Vector3f multiply(Vector3f self,Quaternion q){
        q.mult(self)
    }
    public static Vector3f minus(Vector3f self,Vector3f a){
        self.subtract(a)
    }
    
    public static Vector3f div(Vector3f self,Vector3f a){
        self.divide(a)
    }
    
    public static Vector3f div(Vector3f self,float a){
        self.divide(a)
    }

    public static negative(Vector3f self) {
        self.negate() 
    }
    public static Vector3f or(Vector3f self, Vector3f vec){
        self.cross(vec)
    }
    public static float xor(Vector3f self, Vector3f vec){
        self.dot(vec)
    }
    public static Vector3f bitwiseNegate(Vector3f self){
        self.normalize()
    }
}

