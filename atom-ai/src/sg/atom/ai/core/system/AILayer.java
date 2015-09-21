/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.core.system;

/**
 * A Group of related module and techniques can be enable/ disable together.
 * Layered AI architecture is a very common approach beside of Modules.
 *
 * <p> This is a stragegy to use with DI to enable better configuration. Beside
 * of configed, a bunch of relate modules can be update or executed within a
 * same fraction or slice of time, by the scheduler.
 *
 * <P>Layers also provided a standard mechanism to broadcast layer inner
 * messages and knowledge and from one under to next above layer just like in
 * network architecture.
 *
 * @author cuong.nguyenmanh2
 */
public class AILayer {
}
