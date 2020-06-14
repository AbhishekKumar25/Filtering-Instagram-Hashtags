package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class SuperVisor extends JFrame{
	JButton b1,b2;
	Font f1;
	JPanel p1;
	
public SuperVisor(){
	super("SuperVisor Screen");
	p1 = new JPanel();
	p1.setLayout(null);

	f1 = new Font("Times New Roman",Font.PLAIN,14);
	
	b1 = new JButton("View Bipartitie Graph");
	b1.setFont(f1);
	b1.setBounds(10,10,300,30);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			Graph sn = new Graph();
			sn.createTree();
			sn.setVisible(true);
			sn.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
	});

	b2 = new JButton("Run Hit Algorithm");
	b2.setFont(f1);
	b2.setBounds(10,60,300,30);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			runHit();
        }
	});

	getContentPane().add(p1,BorderLayout.CENTER);
}
public void runHit(){
	try{
		ViewRanking vr = new ViewRanking();
		ObjectInputStream oin = new ObjectInputStream(new FileInputStream("figure8.txt"));
		Object obj = (Object)oin.readObject();
		ArrayList<Figure8> list = (ArrayList<Figure8>)obj;
		for(int i=0;i<list.size();i++){
			Figure8 f8 = list.get(i);
			ArrayList<String> tags = f8.getAnnotate();
			String ht = f8.getHashtag();
			int index = ht.lastIndexOf(".");
			ht = ht.substring(0,index);
			for(int j=0;j<tags.size();j++){
				String tag = tags.get(j);
				double relevant = HIT.getHit(ht,tag);
				System.out.println(f8.getHashtag()+" "+tag+" "+relevant);
				if(relevant > 0.20) {
					Object row[] = {ht,f8.getAnnotator(),tag,"Relevant",relevant};
					vr.dtm.addRow(row);
					
				} else {
					Object row[] = {ht,f8.getAnnotator(),tag,"Irrelevant",relevant};
					vr.dtm.addRow(row);
				}
			}
		}
		vr.setVisible(true);
		vr.setSize(800,600);
	}catch(Exception e){
		e.printStackTrace();
	}
}
}