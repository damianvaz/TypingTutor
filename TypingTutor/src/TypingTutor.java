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
	CenterPanel centerPanel = new CenterPanel();
	JTextArea textArea;
	JMenuBar menuBar;
	
	public TypingTutor()
	{
		super("Typing Tutor");
		this.keyboard = new Keyboard();

		// create empty border for Keyboard
		keyboard.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), null));
		KeyboardKeyBindings kb = new KeyboardKeyBindings(keyboard, centerPanel, "Free mode");

		add(centerPanel);
		add(keyboard, BorderLayout.SOUTH);

		menuBar = new MenuBar(keyboard, kb, centerPanel, this);
		setJMenuBar(menuBar);
		
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