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
	private JFrame frame;
	private ButtonGroup keyboardLayoutButtonGroup;
	private ButtonGroup modeButtonGroup;
	private String mode;
	private CenterPanel centerPanel;
	String[] history =
	{ "aloalo", "Marciano", "de boa na lagoa" };
	private final String[] LAYOUTS =
	{ "BR", "US" };
	private final String[] MODES =
	{ "Free mode", "Game Mode" };

	public MenuBar(Keyboard keyboard, KeyboardKeyBindings keybindings, CenterPanel centerPanel, JFrame frame)
	{
		this.keyboard = keyboard;
		this.frame = frame;
		this.centerPanel = centerPanel;
		mode = "Free mode";
		keybindings = new KeyboardKeyBindings(keyboard, centerPanel, mode);
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
					keybindings = new KeyboardKeyBindings(keyboard, centerPanel, mode);
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
			if(modeButtons[0].isSelected())
			{
				mode = modeButtons[0].getText();
				keybindings = new KeyboardKeyBindings(keyboard, centerPanel, mode);
				centerPanel.gamePanel.setVisible(false);
			}
			if(modeButtons[1].isSelected())
			{
				centerPanel.textArea.setText("");
				mode = modeButtons[1].getText();
				keybindings = new KeyboardKeyBindings(keyboard, centerPanel, mode);
				JOptionPane.showMessageDialog(null, "Welcome to GAME MODE\n to play type the text and press Enter", "GAME MODE", JOptionPane.WARNING_MESSAGE);
				centerPanel.gamePanel.setVisible(true);
			}
			
			// Update panel content
			centerPanel.revalidate();
			centerPanel.repaint();
			frame.pack();
			
		}
	}
}
