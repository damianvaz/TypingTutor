import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class CenterPanel extends JPanel
{
	public JTextArea textArea;
	public JTextPane textPane;
	public JPanel gamePanel;
	public String textOnPanel;
	private Box verticalBox;
	

	public CenterPanel()
	{
		textArea = new JTextArea(10, 75);

		// Creating Border for TextArea
		Color color = this.getBackground();
		Border insideBorder = BorderFactory.createEtchedBorder(new Color(0, 128, 128), color);
		Border outsideBorder = BorderFactory.createMatteBorder(20, 25, 20, 20, color);
		textArea.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		
		makeJTextPane();
		textPane.setVisible(false);
		

		// Creating Border for Game Panel
		gamePanel = new JPanel();
		insideBorder = BorderFactory.createTitledBorder("Type the phrase and press Enter:");
		outsideBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		gamePanel.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		gamePanel.add(textPane);
		gamePanel.setVisible(false);

		verticalBox = new Box(BoxLayout.PAGE_AXIS);
		verticalBox.add(textArea);
		verticalBox.add(gamePanel);

		add(verticalBox);
	}

	public void makeJTextPane()
	{
		// Setting Text Pane
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(false);
		textPane.setBackground(Color.LIGHT_GRAY);
	}

	public void setTextPane(String phrase)
	{
		textOnPanel = phrase;
		gamePanel.removeAll();
		makeJTextPane();
		String htmlBegin = "<html><b style='color: red;'>";
		String htmlEnd = "</b></html>";
		textPane.setText(htmlBegin + phrase + htmlEnd);
		gamePanel.add(textPane);
	}
	public String getString()
	{
		return textOnPanel;
	}
}
