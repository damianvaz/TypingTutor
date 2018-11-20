import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;

public class MenuBar extends JMenuBar
{
	private JRadioButtonMenuItem keyboardLayoutButtons[];
	private JRadioButtonMenuItem modeButtons[];
	private Keyboard keyboard;
	private KeyboardKeyBindings keybindings;
	private JTextArea textArea;
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup keyboardLayoutButtonGroup;
	private ButtonGroup modeButtonGroup;
	private String mode;
	String[] history =
	{ "aloalo", "Marciano", "de boa na lagoa" };
	private final String[] LAYOUTS =
	{ "BR", "US" };
	private final String[] MODES =
	{ "Free mode", "Game Mode" };

	public MenuBar(Keyboard keyboard, KeyboardKeyBindings keybindings, JTextArea textArea, JPanel panel, JFrame frame)
	{
		this.keyboard = keyboard;
		this.textArea = textArea;
		this.panel = panel;
		this.frame = frame;
		mode = "Free mode";
		keybindings = new KeyboardKeyBindings(keyboard, textArea, mode, panel);
		// create File Menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenu keyboardLayoutMenu = new JMenu("Keyboard Layout");
		keyboardLayoutButtons = new JRadioButtonMenuItem[LAYOUTS.length];
		keyboardLayoutButtonGroup = new ButtonGroup();
		ItemHandler itemHandler = new ItemHandler();

		// create Keyboard Layout menu radio buttons
		for (int i = 0; i < LAYOUTS.length; i++)
		{
			keyboardLayoutButtons[i] = new JRadioButtonMenuItem(LAYOUTS[i]);
			keyboardLayoutMenu.add(keyboardLayoutButtons[i]);
			keyboardLayoutButtonGroup.add(keyboardLayoutButtons[i]);
			keyboardLayoutButtons[i].addItemListener(itemHandler);
		}
		
		JMenu modeMenu = new JMenu("Mode");
		modeButtons = new JRadioButtonMenuItem[MODES.length];
		modeButtonGroup = new ButtonGroup();

		modeHandler modeHandler = new modeHandler();
		// create mode menu radio buttons
		for (int i = 0; i < MODES.length; i++)
		{
			modeButtons[i] = new JRadioButtonMenuItem(MODES[i]);
			modeMenu.add(modeButtons[i]);
			modeButtonGroup.add(modeButtons[i]);
			modeButtons[i].addItemListener(modeHandler);
		}

		keyboardLayoutButtons[0].setSelected(true);
		modeButtons[0].setSelected(true);
		fileMenu.add(keyboardLayoutMenu);
		fileMenu.add(modeMenu);
		fileMenu.addSeparator();

		// create About menu item
		JMenuItem aboutItem = new JMenuItem("About...");
		fileMenu.add(aboutItem);

		aboutItem.addActionListener(new ActionListener() {
			// Anonymous internal class
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(MenuBar.this,
						"<html>" + "<h1 style='color: purple'>This is the Typing Tutor by Damian Vaz!!</h1>" + "<ul>"
								+ "<li>The program that helps you to learn to type<br>"
								+ "without looking  at the keyboard!</li>" + "<li>Type anything in the text area,<br>"
								+ "and see how the virtual keyboard changes</li>"
								+ "<li>Try the Game mode, where you need to<br>"
								+ "Type the world that appears. The program <br>"
								+ "automatically detects how many keys you got right and wrong</li>"
								+ "<li>I hope you like it! :)</li>"
								+ "</ul>"
								+ "</html>",
						"About", JOptionPane.PLAIN_MESSAGE);
			}
		});
		add(fileMenu);

		// Create History Menu
		JMenu historyMenu = new JMenu("History");
		historyMenu.setMnemonic(KeyEvent.VK_H);

		for (int i = 0; i < history.length; i++)
		{
			historyMenu.add(new JMenuItem(history[i]));
		}
		add(historyMenu);

	}

	private class ItemHandler implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			for (int i = 0; i < LAYOUTS.length; i++)
			{
				if(keyboardLayoutButtons[i].isSelected())
				{
					keyboard.setKeyboard(LAYOUTS[i]);
					keyboard.revalidate();
					keyboard.repaint();
					keybindings = new KeyboardKeyBindings(keyboard, textArea, mode, panel);
					break;
				}
			}
		}
	}
	private class modeHandler implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			// Creating JTextPane
			String htmlText = "<html><div style='color: red;'>ALO ALO MARCIANO</html>";
			JTextPane textPane = new JTextPane();
			JPanel gamePanel = new JPanel();
			textPane.setContentType("text/html");
			textPane.setText(htmlText);
			textPane.setEditable(false);
			textPane.setMaximumSize(new Dimension(200,20));
			textPane.setPreferredSize(new Dimension(200, 20));
			textPane.setBackground(Color.BLACK);
			
			// Creating Border for Game Panel
			Border insideBorder = BorderFactory.createTitledBorder("Type the phrase and press Enter:");
			Border outsideBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
			gamePanel.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
						
			//Adding JTextPane to gamePanel and gamePanel to Vertical Box
			Box verticalBox = new Box(BoxLayout.PAGE_AXIS);
			gamePanel.add(textPane);
			verticalBox.add(textArea);
			verticalBox.add(gamePanel);
			
			
			
			if(modeButtons[0].isSelected())
			{
				mode = modeButtons[0].getText();
				keybindings = new KeyboardKeyBindings(keyboard, textArea, mode, panel);
				gamePanel.setVisible(false);
			}
			if(modeButtons[1].isSelected())
			{
				mode = modeButtons[1].getText();
				keybindings = new KeyboardKeyBindings(keyboard, textArea, mode, panel);
				gamePanel.setVisible(true);
			}
			
			// Update panel content
			panel.removeAll();
			panel.add(verticalBox);
			panel.revalidate();
			panel.repaint();
			frame.pack();
			
		}
	}
}
