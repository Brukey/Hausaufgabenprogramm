package de.khwlani.hausaufgaben;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GleichungPanel extends JPanel{

	JLabel label;
	JTextField textField;
	
	public GleichungPanel(JFrame frame) {
		this.setSize(frame.getSize());
		this.setLocation(0,0);
		this.setLayout(new BorderLayout());
		
		label=new JLabel("Geben Sie unten ihre Gleichung ein.");
		label.setSize(frame.getWidth(),50);
		
		textField=new JTextField();
		textField.setPreferredSize(new Dimension(100, 50));
		
		this.add(label,BorderLayout.NORTH);
		this.add(textField, BorderLayout.CENTER);
	}
}
