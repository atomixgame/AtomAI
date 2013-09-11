package sg.atom.ai.core.system;

import java.util.ArrayList;

/**
 *
 * @author hungcuong
 */
/**
 * AIModule. Gateway for Module architecture. using Guice and Java ServiceLoader
 */
public interface AIModule {

    public abstract ArrayList<AITech> getTechs();

    public abstract void setEnable(boolean enabled);
}
