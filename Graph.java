package com;
import javax.swing.JFrame;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.DirectedSubgraph;
import org.jgrapht.GraphPath;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.experimental.isomorphism.AdaptiveIsomorphismInspectorFactory;
import org.jgrapht.experimental.isomorphism.GraphIsomorphismInspector;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Random;
public class Graph extends JFrame {
	private static final long serialVersionUID = 3256444702936019250L;
	private static final Color DEFAULT_BG_COLOR = Color.yellow;
	private static final Dimension DEFAULT_SIZE = new Dimension(530, 600);
  	JGraph graph;
	JScrollPane jsp;
	JPanel p1,p2;
	JGraphModelAdapter adapter;
	int num;
	ListenableDirectedMultigraph directed;
	static ArrayList<String> temp = new ArrayList<String>();

	
public Graph(){
	setTitle("Graph");
	
	directed = new ListenableDirectedMultigraph(DefaultEdge.class);
    adapter = new JGraphModelAdapter(directed);
    graph = new JGraph(adapter);
    adjustDisplaySettings();
	p1 = new JPanel();
	p1.setLayout(new BorderLayout());
	jsp = new JScrollPane(graph,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	p1.add(jsp,BorderLayout.CENTER);
	getContentPane().add(p1,BorderLayout.CENTER);
}
public static boolean checkDistance(int x,int y){
	boolean flag = false;
	for(int i=0;i<temp.size();i++){
		String arr[] = temp.get(i).split(",");
		double d = getDistance(x,y,Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
		if(d < 50){
			flag = true;
			break;
		}
	}
	return flag;
}
public static double getDistance(int n1x,int n1y,int n2x,int n2y) {
	int dx = (n1x - n2x) * (n1x - n2x);
	int dy = (n1y - n2y) * (n1y - n2y);
	int total = dx + dy; 
	return Math.sqrt(total);
}
public static int getXPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}
public static int getYPosition(int start,int end){
	Random rn = new Random();
	int range = end - start + 1;
	return rn.nextInt(range) + start;
}	

public void createTree(){
	try{
		temp.clear();
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream("figure8.txt"));
		Object obj = (Object)oin.readObject();
		ArrayList<Figure8> list = (ArrayList<Figure8>)obj;
		for(int i=0;i<list.size();i++){
			Figure8 f8 = list.get(i);
			int x = getXPosition(10,800);
			int y = getYPosition(50,600);
			boolean flag = true; 
			while(flag) {
				if(!checkDistance(x,y)) {
					temp.add(x+","+y);
					directed.addVertex(f8.getAnnotator());
					positionVertexAt(f8.getAnnotator(),x,y);
					flag = false;
				}
				x = getXPosition(10,800);
				y = getYPosition(50,600);
			}

			flag = true; 
			while(flag) {
				if(!checkDistance(x,y)) {
					temp.add(x+","+y);
					directed.addVertex(f8.getHashtag());
					positionVertexAt(f8.getHashtag(),x,y);
					flag = false;
				}
				x = getXPosition(10,800);
				y = getYPosition(50,600);
			}
			directed.addEdge(f8.getAnnotator(),f8.getHashtag());
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

private void positionVertexAt(Object name, int x, int y){
	DefaultGraphCell localDefaultGraphCell = adapter.getVertexCell(name);
	AttributeMap localAttributeMap1 = localDefaultGraphCell.getAttributes();
    Rectangle2D localRectangle2D = GraphConstants.getBounds(localAttributeMap1);
    Rectangle2D.Double localDouble = new Rectangle2D.Double(x, y, localRectangle2D.getWidth(), localRectangle2D.getHeight());
    GraphConstants.setBounds(localAttributeMap1, localDouble);
	GraphConstants.setBackground(localAttributeMap1,Color.blue);
	GraphConstants.setForeground(localAttributeMap1,Color.white);
    AttributeMap localAttributeMap2 = new AttributeMap();
    localAttributeMap2.put(localDefaultGraphCell, localAttributeMap1);
    adapter.edit(localAttributeMap2, null, null, null);
}
public void adjustDisplaySettings(){
	graph.setPreferredSize(DEFAULT_SIZE);
    Color color = DEFAULT_BG_COLOR;
    graph.setBackground(color);
}
private static class ListenableDirectedMultigraph<V, E> extends DefaultListenableGraph<V, E> implements DirectedGraph<V, E>{
	private static final long serialVersionUID = 1L;
ListenableDirectedMultigraph(Class<E> paramClass){
	super(new DirectedMultigraph(paramClass));
}
}
}
