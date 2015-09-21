/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.core.data;

/**
 * Base data warper for AI operations. This class has initial operation of
 * private, shared data between agents. Processing this data resulted in
 * AISample.
 *
 * <p> This Data is managed by the System in a high conccurent, non-blocking
 * enviroment. If blocking scenario is required, an approriate routine should be
 * choosed. For normal kind data which is non-frequent accessed by agents or not
 * shared between them or modules, no need to use this kind of Data.
 *
 * <p>Piece of information can be wraped by Messages and send between Agents.
 *
 * @author hungcuong
 */
public abstract class AIData {
}
