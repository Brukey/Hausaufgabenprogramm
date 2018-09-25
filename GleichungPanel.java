package de.khwlani.hausaufgaben;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GleichungPanel extends JPanel{

	JLabel anzlVarLabel;
	JTextField anzlVarTextField;
	
	public GleichungPanel(JFrame frame) {
		this.setSize(frame.getSize());
		this.setLocation(0,0);
		this.setLayout(null);
		
		anzlVarLabel= new JLabel("Wie viele Variablen besitzen Sie?");
		anzlVarLabel.setSize(frame.getWidth(),50);
		anzlVarLabel.setLocation(0, 50);
		
		anzlVarTextField=new JTextField();
		anzlVarTextField.setEditable(true);
		anzlVarTextField.setSize(50,50);
		anzlVarTextField.setLocation(frame.getWidth()/2-anzlVarTextField.getWidth()/2,anzlVarLabel.getY()+25);

		
		this.add(anzlVarLabel);
		this.add(anzlVarTextField);
	}
}
