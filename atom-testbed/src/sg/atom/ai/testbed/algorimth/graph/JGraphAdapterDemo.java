package sg.atom.ai.testbed.algorimth.graph;

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

/**
 * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
 *
 * @author Barak Naveh
 *
 * @since Aug 3, 2003
 */
public class JGraphAdapterDemo extends JFrame {

    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    // 
    private JGraphModelAdapter m_jgAdapter;
    JGraph jgraph;

    public JGraphAdapterDemo(String title) throws HeadlessException {
        super(title);
        setSize(DEFAULT_SIZE);
    }

    public AbstractGraph createGraphModel() {
        // create a JGraphT graph
        AbstractGraph g = new ListenableDirectedGraph(DefaultEdge.class);     
        return g;
    }

    public void init(AbstractGraph g) {
        // create a visualization using JGraph, via an adapter
        m_jgAdapter = new JGraphModelAdapter(g);
        jgraph = new JGraph(m_jgAdapter);
        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        addData(g);
        // that's all there is to it!...
    }

    public void addData(AbstractGraph g) {
        // add some sample data (graph manipulated via JGraphT)
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");
        g.addVertex("v4");

        g.addEdge("v1", "v2");
        g.addEdge("v2", "v3");
        g.addEdge("v3", "v1");
        g.addEdge("v4", "v3");

        // position vertices nicely within JGraph component
        positionVertexAt("v1", 130, 40);
        positionVertexAt("v2", 60, 200);
        positionVertexAt("v3", 310, 230);
        positionVertexAt("v4", 380, 70);
    }

    private void adjustDisplaySettings(JGraph jg) {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        jg.setBackground(c);
    }

    private void positionVertexAt(Object vertex, int x, int y) {
        DefaultGraphCell cell = m_jgAdapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        Rectangle2D b = GraphConstants.getBounds(attr);

        GraphConstants.setBounds(attr, new Rectangle.Double((double) x, (double) y, (double) b.getWidth(), b.getHeight()));

        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        m_jgAdapter.edit(cellAttr, null, null, null);
    }

    JGraph getGraphDisplay() {
        return jgraph;
    }
}
