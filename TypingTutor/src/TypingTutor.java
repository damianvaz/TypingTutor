import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class TypingTutor extends JFrame
{
	Keyboard keyboard;
	JPanel centerPanel = new JPanel();
	JTextArea textArea;
	JMenuBar menuBar;
	
	public TypingTutor()
	{
		super("Typing Tutor");
		this.keyboard = new Keyboard();
		textArea = new JTextArea(10,75);


		
		// Creating Border for TextArea
		Color color = this.getBackground();
		Border insideBorder = BorderFactory.createEtchedBorder(new Color(0,128,128), color);
		Border outsideBorder = BorderFactory.createMatteBorder(20, 25, 20, 20, color);
		textArea.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));

		// create empty border for Keyboard
		keyboard.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), null));
		
		
		add(keyboard, BorderLayout.SOUTH);
		
		JTextPane textPane = new JTextPane();
		textPane.setVisible(false);
		/*
		String htmlText = "<html><div style='color: red;'>ALO<b style='color: blue;'>asasa</b>ALO</div></html>";
	
		textPane.setContentType("text/html");
		textPane.setText(htmlText);
		textPane.setEditable(false);
		textPane.setMaximumSize(new Dimension(200,20));
		textPane.setMinimumSize(new Dimension (200, 20));
		textPane.setPreferredSize(new Dimension(200, 20));
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setVisible(true);
		*/
		Box verticalBox = new Box(BoxLayout.PAGE_AXIS);
		verticalBox.add(textArea);
		verticalBox.add(textPane);
		
		verticalBox.add(textPane);
		centerPanel.add(verticalBox);
		//centerPanel.add(textArea);
		KeyboardKeyBindings kb = new KeyboardKeyBindings(keyboard, textArea, "Free mode", centerPanel);

		add(centerPanel);


		menuBar = new MenuBar(keyboard, kb, textArea, centerPanel, this);
		setJMenuBar(menuBar);
		//add(textArea);
		/*
		centerPanel.add(textArea);
		centerPanel.setLayout(new BoxLayout());
		centerPanel.add(new JTextField("ALOALO"), BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);
	//	add(textArea, BorderLayout.CENTER);
		//add(new JTextField("aloalo"));
		
	*/
	
		
	}

	public static void main(String[] args)
	{
		TypingTutor frame = new TypingTutor();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);		
	}
	
}