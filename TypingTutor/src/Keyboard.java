
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

// JFrame, JPanel, JLabel, JButton
import javax.swing.JButton;
import javax.swing.JPanel;

import KeyboardLayouts.BRLayout;
import KeyboardLayouts.GenericLayout;
import KeyboardLayouts.USLayout;

public class Keyboard extends JPanel
{
	private final JPanel keyboard = new JPanel();

	public GenericLayout keysLayout = new BRLayout();
	public JButton[][] keyButtons;
	
	public JButton[][] getButtons()
	{
		return keyButtons;
	}
	public void setKeyboard(String layout)
	{
		switch(layout)
		{
			case("US"):
			{
				keyboard.removeAll();
				keysLayout = new USLayout();
				makeKeyboard();
				break;
			}
			case("BR"):
			{
				keyboard.removeAll();
				keysLayout = new BRLayout();
				makeKeyboard();
				break;
			}
			default:
			{
				keysLayout = new BRLayout();
			}
		}
	}
	public void makeKeyboard()
	{
		keyboard.setLayout(new GridBagLayout());
		JPanel pRow;
		 
		String[][] key = keysLayout.getKeys();
		keyButtons = new JButton[key.length][];

		GridBagConstraints cRow = new GridBagConstraints(), cButton = new GridBagConstraints();
		cRow.anchor = GridBagConstraints.WEST;
		cButton.ipady = 21;


		// first dimension of the key array
		// representing a row on the keyboard
		for (int row = 0; row < key.length; row++)
		{
			pRow = new JPanel(new GridBagLayout());
			
			cRow.gridy = row;
			
			//initializing the second dimension of array to be the number of collumns that row has
			keyButtons[row] = new JButton[key[row].length];
			
			// second dimension representing each key
			for (int col = 0; col < key[row].length; col++)
			{
				keyButtons[row][col] = new JButton(key[row][col]);
				// specify padding and insets for the buttons
				switch (key[row][col])
				{
				case "Backspace":
					cButton.ipadx = keysLayout.backspaceIpadx;
					break;
				case "Tab":
					cButton.ipadx = keysLayout.tabIpadx;
					break;
				case "Caps":
					cButton.ipadx = keysLayout.capsIpadx;
					cButton.gridy = keysLayout.capsGridy;
					cButton.gridx = keysLayout.capsGridx;
					break;
				case "Enter":
					cButton.ipadx = keysLayout.enterIpadx;
					cButton.fill = GridBagConstraints.BOTH;
					cButton.gridheight = GridBagConstraints.REMAINDER;
					break;
				case "Shift":
					cButton.ipadx = keysLayout.shiftIpadx;
					break;
				case "\u2191":
					cButton.ipadx = 8;
					cButton.insets = keysLayout.upperArrowKeyInsets;
					break;
				case "Ctrl":
					cButton.ipadx = keysLayout.ctrlIpadx;
					cButton.insets = keysLayout.insets;
					break;
				case "Super":
					cButton.ipadx = keysLayout.superIpadx;
					break;
				case "Alt":
					cButton.ipadx = keysLayout.altIpadx;
					break;
				case " ":
					cButton.ipadx = keysLayout.spaceIpadx;
					break;
				case "Alt Gr":
					cButton.ipadx = keysLayout.altGrIpadx;
					cButton.insets = keysLayout.altGrInset;
					break;
				default:
					keyButtons[row][col].setPreferredSize(new Dimension(50, 26));
					cButton.ipadx = 0;
					cButton.insets = keysLayout.insets;
				}
				// if is in the second row (tab, a, s, d...) AND col > 13 (after the "Enter")
				// jumps to a new line and set gridx back to the begining
				// for the BRLayout to work
				if (col > 13 && cRow.gridy == 1)
				{
					cButton.gridx = col - 14;
				} else
				{
					cButton.gridx = col;
				}
				keyButtons[row][col].setFocusable(false);
				pRow.add(keyButtons[row][col], cButton);
			}
			keyboard.add(pRow, cRow);
		}
		add(keyboard);
	}
	public Keyboard()
	{
		makeKeyboard();
	}
}