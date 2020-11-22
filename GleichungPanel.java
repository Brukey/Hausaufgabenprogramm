
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class GleichungPanel extends JPanel{

	public static String func = "f(x) = x";

	private JLabel label;
	private JTextField textField;
	
	public GleichungPanel(JFrame frame) {
		this.setSize(frame.getSize());
		this.setLayout(new BorderLayout());
		
		label = new JLabel("Geben Sie unten ihre Gleichung ein.");
		label.setSize(frame.getWidth(),50);
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(100, 50));
		textField.setText(func);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				func = textField.getText();
			}
			public void removeUpdate(DocumentEvent e) {
				func = textField.getText();
			}
			public void insertUpdate(DocumentEvent e) {
				func = textField.getText();
			}
		});
		
		this.add(label, BorderLayout.SOUTH);
		this.add(textField, BorderLayout.CENTER);
	}
}
