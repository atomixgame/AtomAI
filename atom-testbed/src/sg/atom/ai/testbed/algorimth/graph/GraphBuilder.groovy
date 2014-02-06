package sg.atom.ai.testbed.algorimth.graph


import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import groovy.lang.Closure;
import groovy.lang.GroovyInterceptable;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.Writable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GraphBuilder extends GroovyObjectSupport implements Writable {
    protected final String graphName;
    protected final Map<String, Node> nodes = new HashMap<String, Node>();
 
    class NodeImpl extends Node {
        /**
         * @param nodeName
         * @param args
         */
        public NodeImpl(final String nodeName, final Object args) {
            super(nodeName, args);
        }
   
        /* (non-JavaDoc)
         * @see uk.co.wilson.groovy.graphbuilder.Node#getFromNodes()
         */
        @Override
        public Node[] getFromNodes() {
            return findNodes(GraphBuilder.this.nodes, this.fromNodeNames);
        }

        /* (non-JavaDoc)
         * @see uk.co.wilson.groovy.graphbuilder.Node#getToNodes()
         */
        @Override
        public Node[] getToNodes() {
            return findNodes(GraphBuilder.this.nodes, this.toNodeNames);
        }
    }

    class GraphBuilderDelegate extends GroovyObjectSupport implements GroovyInterceptable {
        /* (non-JavaDoc)
         * @see groovy.lang.GroovyObjectSupport#invokeMethod(java.lang.String, java.lang.Object)
         */
        @Override
        public Node invokeMethod(final String nodeName, final Object args) {
            final Node node = new NodeImpl(nodeName, args);
                   
            GraphBuilder.this.nodes.put(nodeName, node);
     
            return node;
        }
   
        /* (non-JavaDoc)
         * @see groovy.lang.GroovyObjectSupport#getProperty(java.lang.String)
         */
        @Override
        public Object getProperty(final String property) {
            return property;
        }
    }
 
    /**
     * @param graphName
     * @param nodes
     */
    public GraphBuilder(final String graphName, final Closure closure) {
        closure.setDelegate(new GraphBuilderDelegate());

        closure.call();

        this.graphName = graphName;
    }

    /* (non-JavaDoc)
     * @see groovy.lang.GroovyObjectSupport#getProperty(java.lang.String)
     */
    @Override
    public Object getProperty(final String property) {
        return this.nodes.get(property);
    }

    /* (non-JavaDoc)
     * @see groovy.lang.Writable#writeTo(java.io.Writer)
     */
    public Writer writeTo(final Writer out) throws IOException {
        out.write("digraph " + this.graphName + " {\n");
        out.write("rankdir=LR;\n");
        out.write("size=\"8,5\"\n");

        for (final Iterator<Node> iterator = this.nodes.values().iterator(); iterator.hasNext();) {
            final Node node = iterator.next();
            final String nodeName = node.getNodeName();
            final Node[] toNodes = node.getToNodes();
            final Node[] fromNodes = node.getFromNodes();

            for (int i = 0; i != toNodes.length; i++) {
                out.write(nodeName + " -> " + toNodes[i].getNodeName() + ";\n");
            }

            for (int i = 0; i != fromNodes.length; i++) {
                out.write(fromNodes[i].getNodeName() + " -> " + nodeName + ";\n");
            }
        }

        out.write("}\n");

        return out;
    }
}


