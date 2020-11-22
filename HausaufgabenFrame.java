
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class HausaufgabenFrame extends JFrame{

	private JTabbedPane tabbedPane;
	private GleichungPanel gleichungPanel;
	
	public HausaufgabenFrame() {
		this.setSize(1020, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane();
		gleichungPanel = new GleichungPanel(this);

		tabbedPane.addTab("Gleichungen", gleichungPanel);
		tabbedPane.addTab("Plotter", new Plotter());
		
		this.add(tabbedPane);
		this.setVisible(true);
	}

	
	
	public static void main(String[] args) {
		new HausaufgabenFrame();
	}

}
