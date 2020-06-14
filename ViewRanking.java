package com;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.BorderLayout;
public class ViewRanking extends JFrame{
	
	JTable table;
	DefaultTableModel dtm;
	JScrollPane jsp;
	Font f1;
	JPanel p1,p2;
	
public ViewRanking(){
	super("View Ranking");
	setLayout(new BorderLayout());
	f1 = new Font("Times New Roman",Font.PLAIN,14);
	p2 = new JPanel();
	p2.setBackground(Color.white);
	p2.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.getTableHeader().setFont(new Font("Times New Roman",Font.PLAIN,15));
	jsp = new JScrollPane(table);
	table.setFont(f1);
	table.setRowHeight(30);
	dtm.addColumn("HashTag");
	dtm.addColumn("Annotator");
	dtm.addColumn("Annotate Tag");
	dtm.addColumn("Result");
	dtm.addColumn("Ranking Score");
	
	
	p2.add(jsp,BorderLayout.CENTER);

	
	
	getContentPane().add(p2,BorderLayout.CENTER);
	
}
}