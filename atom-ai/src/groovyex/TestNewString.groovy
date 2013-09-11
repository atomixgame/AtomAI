package groovyex

import com.jme3.math.Vector3f
/**
 *
 * @author cuong.nguyenmanh2
 */
println "Cuong la thien tai".reverseToUpperCase()

def vec3(x,y,z){
    new Vector3f(x,y,z)
}
v= vec3(1,2,3)
println v + vec3(4,5,6)

println vec3(1,6,7)*vec3(5,6,7)*2

println vec3(0,0,1)^vec3(1,1,0)

println vec3(0,0,1)|vec3(1,0,0)