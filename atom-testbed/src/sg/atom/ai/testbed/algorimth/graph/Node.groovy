/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.atom.ai.testbed.algorimth.graph

/**
 *
 * @author cuong.nguyenmanh2
 */
import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author John
 *
 */
public abstract class Node extends GroovyObjectSupport {
    private static final Closure noAction = new Closure(new Object()) {
        /* (non-JavaDoc)
         * @see groovy.lang.Closure#call(java.lang.Object[])
         */
        @Override
        public Object call(Object[] args) {
            return null;
        }
    };

    private static String[] noNodes = [];

    protected final String nodeName;
    protected final Closure action;
    protected final String[] toNodeNames;
    protected final String[] fromNodeNames;

    /**
     * @param nodeName
     * @param args
     */
    @SuppressWarnings("unchecked")
    public Node(final String nodeName, final Object args) {
        final Object[] argArray = (Object[])args;

        this.nodeName = nodeName;

        switch(argArray.length) {
        case 0:
            this.action = noAction;
            this.toNodeNames = noNodes;
            this.fromNodeNames = noNodes;
            break;

        case 1:
            if (argArray[0] instanceof List) {
                this.action = noAction;
                this.toNodeNames = getNodes((List<Object>)argArray[0]);
                this.fromNodeNames = noNodes;
            } else if (argArray[0] instanceof String) {
                this.action = noAction;
                this.toNodeNames = new String[]{(String)argArray[0]};
                this.fromNodeNames = noNodes;
            } else if (argArray[0] instanceof Closure) {
                this.action = (Closure)argArray[0];
                this.toNodeNames = noNodes;
                this.fromNodeNames = noNodes;
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a parameter of type '" + argArray[0].getClass().getSimpleName()
                    + "'. This parameter must be a String, List or Closure");
            }
            break;

        case 2:
            if (argArray[0] instanceof List) {
                this.toNodeNames = getNodes((List<Object>)argArray[0]);
            } else if (argArray[0] instanceof String) {
                this.toNodeNames = new String[]{(String)argArray[0]};
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a first parameter of type '" + argArray[0].getClass().getSimpleName()
                    + "'. The first parameter must be a String or List.");
            }

            if (argArray[1] instanceof List) {
                this.action = noAction;
                this.fromNodeNames = getNodes((List<Object>)argArray[1]);
            } else if (argArray[1] instanceof String) {
                this.action = noAction;
                this.fromNodeNames = new String[]{(String)argArray[1]};
            } else if (argArray[1] instanceof Closure) {
                this.action = (Closure)argArray[1];
                this.fromNodeNames = noNodes;
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a second parameter of type '" + argArray[1].getClass().getSimpleName()
                    + "'. The second parameter must be a String, List or Closure.");
            }
            break;

        case 3:
            if (argArray[0] instanceof List) {
                this.toNodeNames = getNodes((List<Object>)argArray[0]);
            } else if (argArray[0] instanceof String) {
                this.toNodeNames = new String[]{(String)argArray[0]};
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a first parameter of type '" + argArray[0].getClass().getSimpleName()
                    + "'. The first parameter must be a String or List.");
            }

            if (argArray[1] instanceof List) {
                this.fromNodeNames = getNodes((List<Object>)argArray[1]);
            } else if (argArray[1] instanceof String) {
                this.fromNodeNames = new String[]{(String)argArray[1]};
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a second parameter of type '" + argArray[1].getClass().getSimpleName()
                    + "'. The second parameter must be a String or List.");
            }

            if (argArray[2] instanceof Closure) {
                this.action = (Closure)argArray[2];
            } else {
                throw new NodeBuilderException("Node '" + nodeName + "' has a third parameter of type '" + argArray[2].getClass().getSimpleName()
                    + "'. The third parameter must be a Closure.");
            }
            break;

        default:
            throw new NodeBuilderException("Node '" + nodeName + "' has " + argArray.length + " parameters. Only 3 parameters are allowed");
        }
    }

    protected String[] getNodes(final List<Object> nodeList) {
        final String[] result = new String[nodeList.size()];
        int i = 0;

        for (final Iterator<Object> iterator = nodeList.iterator(); iterator.hasNext();) {
            final Object name = iterator.next();

            if (name instanceof String) {
                result[i++] = (String)name;
            } else {
                throw new NodeBuilderException("Node '" + this.nodeName + "has a non String element in one of its List parameters");
            }
        }

        return result;
    }

    protected Node[] findNodes(final Map<String, Node> nodeMap, final String[] nodeNames) {
        final Node[] result = new Node[nodeNames.length];

        for (int i = 0; i != nodeNames.length; i++) {
            if (nodeMap.containsKey(nodeNames[i])) {
                result[i] = nodeMap.get(nodeNames[i]);
            } else {
                throw new NodeBuilderException("Node '" + this.nodeName + "' refers to the node '" + nodeNames[i] + "' which is undefined");
            }
        }
        return result;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return this.nodeName;
    }

    /**
     * @return the action
     */
    public Closure getAction() {
        return this.action;
    }

    /**
     * @return
     */
    public abstract Node[] getToNodes();

    /**
     * @return
     */
    public abstract Node[] getFromNodes();
}


