package com;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
public class Login extends JFrame{
	JLabel l1,l2,l3,l4;
	JTextField tf1,tf2;
	JButton b1,b2,b3;
	Font f1,f2;
	JPanel p1,p2,p3,p4,p5,p6;
	ImageIcon icon;
public Login(){
	super("Login Screen");
	p1 = new JPanel();
	p1.setBackground(Color.black);
	f1 = new Font("Times New Roman",Font.BOLD,22);
	l1 = new JLabel("<html><body><center>User Login Screen</center></body></html>");
	l1.setForeground(new Color(125,54,2));
	l1.setFont(f1);
	p1.add(l1);
	p1.setBackground(new Color(140,150,180));

	p2 = new JPanel();
	p2.setLayout(null);

	f2 = new Font("Times New Roman",Font.PLAIN,14);
	l3 = new JLabel("Annotator/Worker");
	l3.setFont(f2);
	l3.setBounds(70,50,150,30);
	p2.add(l3);

	tf1 = new JTextField(15);
	tf1.setFont(f2);
	tf1.setBounds(180,50,150,30);
	p2.add(tf1);

	l4 = new JLabel("Password");
	l4.setBounds(70,100,150,30);
	l4.setFont(f2);
	p2.add(l4);

	tf2 = new JPasswordField(15);
	tf2.setFont(f2);
	tf2.setBounds(180,100,150,30);
	p2.add(tf2);

	b1 = new JButton("Login");
	b1.setFont(f2);
	b1.setBounds(50,150,100,30);
	p2.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			login();
        }
	});

	b2 = new JButton("Reset");
	b2.setFont(f2);
	b2.setBounds(160,150,100,30);
	p2.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			reset();
        }
	});

	b3 = new JButton("New Annotator/Worker");
	b3.setFont(f2);
	b3.setBounds(270,150,200,30);
	p2.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			Register register = new Register();
			register.pack();
			register.setVisible(true);
			register.setLocationRelativeTo(null);
        }
	});
	
	getContentPane().add(p1,BorderLayout.NORTH);
	getContentPane().add(p2,BorderLayout.CENTER);
}
public static void main(String a[])throws Exception{
	 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 Login screen = new Login();
	 screen.setVisible(true);
	 screen.setSize(500,400);
}
public void reset(){
	tf1.setText("");
	tf2.setText("");
}
public void login(){
	try{
		String user = tf1.getText();
		String pass = tf2.getText();
		if(user == null || user.trim().length() <= 0){
			JOptionPane.showMessageDialog(this,"Username must be enter");
			tf1.requestFocus();
			return;
		}
		if(pass == null || pass.trim().length() <= 0){
			JOptionPane.showMessageDialog(this,"Password must be enter");
			tf2.requestFocus();
			return;
		}
		String input_data[]={user,pass};
		String msg = DBCon.login(input_data);
		if(msg.equals("pass")){
			setVisible(false);
			AnnotatorScreen as = new AnnotatorScreen(this,user);
			as.setVisible(true);
			as.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}else{
			if(user.equals("supervisor") && pass.equals("supervisor")) {
				SuperVisor sv = new SuperVisor();
				sv.setVisible(true);
				sv.setSize(400,200);
			} else {
				JOptionPane.showMessageDialog(this,"invalid login");
			}
		}
		reset();
	}catch(Exception e){
        e.printStackTrace();
    }
		
}

}