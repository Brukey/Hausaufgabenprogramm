package de.khwlani.hausaufgaben;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;

public class HausaufgabenFrame extends JFrame{

	JTabbedPane tabbedPane;
	GleichungPanel gleichungPanel;
	public HausaufgabenFrame() {
		this.setSize(640,480);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);

		tabbedPane=new JTabbedPane();

		gleichungPanel=new GleichungPanel(this);
		this.getContentPane().add(gleichungPanel);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new HausaufgabenFrame();
		
	}

}
