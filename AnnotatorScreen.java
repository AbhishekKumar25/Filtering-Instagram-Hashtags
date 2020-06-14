package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
public class AnnotatorScreen extends JFrame{
	JButton b1,b2,b3,b4;
	JPanel p1,p2;
	Font f1;
	Login login;
	String user;
	JComboBox c1;
	JLabel l1,l2,l3,l4;
	JTextField tf1;
public AnnotatorScreen(Login log,String usr){
	super("Annotator/Worker Screen");
	login = log;
	user = usr;
	p1 = new JPanel();
	f1 = new Font("Times New Roman",Font.PLAIN,14);
	

	p1.setLayout(null);
	l1 = new JLabel("Enter 1 or two words of hashtag for below selected image");
	l1.setFont(f1);
	l1.setBounds(10,20,500,30);
	p1.add(l1);

	l2 = new JLabel("Select Image");
	l2.setFont(f1);
	l2.setBounds(10,70,120,30);
	p1.add(l2);
	c1 = new JComboBox();
	c1.setFont(f1);
	c1.setBounds(130,70,200,30);
	p1.add(c1);
	File file = new File("images");
	File list[] = file.listFiles();
	for(File f : list){
		c1.addItem(f.getName());
	}

	l3 = new JLabel();
	l3.setBounds(500,10,400,400);
	p1.add(l3);

	l4 = new JLabel("Your Hashtag");
	l4.setFont(f1);
	l4.setBounds(10,120,120,30);
	p1.add(l4);
	tf1 = new JTextField();
	tf1.setFont(f1);
	tf1.setBounds(130,120,250,30);
	p1.add(tf1);

	b1 = new JButton("View Image");
	b1.setFont(f1);
	p1.add(b1);
	b1.setBounds(10,170,200,30);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			String str = c1.getSelectedItem().toString();
			ImageIcon icon = new ImageIcon("images/"+str);
			icon.getImage().flush();
			l3.setIcon(icon);
		}
	});
	
	b2 = new JButton("Store Annotate Hashtag");
	b2.setFont(f1);
	p1.add(b2);
	b2.setBounds(220,170,200,30);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			store();
		}
	});

	b3 = new JButton("View Annotate Hashtags");
	b3.setFont(f1);
	p1.add(b3);
	b3.setBounds(10,220,200,30);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			viewTags();
		}
	});

	b4 = new JButton("Logout");
	b4.setFont(f1);
	p1.add(b4);
	b4.setBounds(220,220,200,30);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			setVisible(false);
			login.setVisible(true);
		}
	});

	getContentPane().add(p1,BorderLayout.CENTER);

}
public void viewTags(){
	try{
		ViewHashTag vht = new ViewHashTag();
		File file = new File("figure8.txt");
		if(file.exists()){
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
			Object obj = (Object)oin.readObject();
			ArrayList<Figure8> list = (ArrayList<Figure8>)obj;
			for(int i=0;i<list.size();i++){
				Figure8 f8 = list.get(i);
				if(f8.getAnnotator().equals(user)) {
					Object row[] = {f8.getHashtag(),user,f8.getAnnotate().toString()};
					vht.dtm.addRow(row);
				}
			}
		} else {
			JOptionPane.showMessageDialog(this,"No annotation found from your side");
		}
		if(vht.dtm.getRowCount() > 0) {
			vht.setVisible(true);
			vht.setSize(600,400);
		} else {
			JOptionPane.showMessageDialog(this,"No annotation found from your side");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
}

public void store(){
	String text = tf1.getText();
	String img = c1.getSelectedItem().toString().trim();
	if(text == null || text.trim().length() <= 0){
		JOptionPane.showMessageDialog(this,"Please enter your own hash tag");
		tf1.requestFocus();
		return;
	}
	if(!text.startsWith("#")){
		JOptionPane.showMessageDialog(this,"Your annotation must start with hash tag symbol");
		tf1.requestFocus();
		return;
	}
	try{
		Figure8 f8 = new Figure8();
		f8.setHashtag(img);
		f8.setAnnotator(user);
		f8.setAnnotate(text);
		File file = new File("figure8.txt");
		if(file.exists()){
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
			Object obj = (Object)oin.readObject();
			ArrayList<Figure8> list = (ArrayList<Figure8>)obj;
			check(list,f8,img,text);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(list);
			out.flush();
			out.close();
		} else {
			ArrayList<Figure8> list = new ArrayList<Figure8>();
			check(list,f8,img,text);
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(list);
			out.flush();
			out.close();
		}
		JOptionPane.showMessageDialog(this,user+" hash tag annotation details added");
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void check(ArrayList<Figure8> list,Figure8 f8,String img,String text){
	boolean flag = false;
	for(int i=0;i<list.size();i++){
		Figure8 f88 = list.get(i);
		if(f88.getAnnotator().equals(user) && f88.getHashtag().equals(img)) {
			f88.setAnnotate(text);
			flag = true;
			break;
		}
	}
	if(!flag)
		list.add(f8);
}
}