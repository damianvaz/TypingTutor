import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;

public class KeyboardKeyBindings
{
	static Keyboard keyboard;
	static JTextArea textArea;
	static String mode = "";
	static ReadStringList r = new ReadStringList();
	static Scanner input;
	static CenterPanel centerPanel;
	int wrongKeyPressed, rightKeyPressed;
	public KeyboardKeyBindings(Keyboard kb, CenterPanel cP, String m)
	{
		keyboard = kb;
		textArea = cP.textArea;
		textArea.setTabSize(4);
		centerPanel = cP;
		mode = m;
		r.openFile();
		input = r.getStringList();
		String breakLineCode = System.getProperty("line.separator");
		input.useDelimiter(breakLineCode);
		centerPanel.setTextPane(input.next());
		wrongKeyPressed = 0;
		rightKeyPressed = 0;
		
		//disable keybindings for textArea, for it to work on the keyboard JPanel.
		InputMap textIm = textArea.getInputMap();
		textIm.put(KeyStroke.getKeyStroke("ENTER"), "none");
		textIm.put(KeyStroke.getKeyStroke("TAB"), "none");
		textIm.put(KeyStroke.getKeyStroke("LEFT"), "none");
		textIm.put(KeyStroke.getKeyStroke("UP"), "none");
		textIm.put(KeyStroke.getKeyStroke("DOWN"), "none");
		textIm.put(KeyStroke.getKeyStroke("RIGHT"), "none");
		textIm.put(KeyStroke.getKeyStroke("BACK_SPACE"), "none");
		
		InputMap im = keyboard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap ap = keyboard.getActionMap();
		
		// for every Button in keyboard add a keyBinding
		for (int i = 0; i < keyboard.keyButtons.length; i++)
		{
			for (int j = 0; j < keyboard.keyButtons[i].length; j++)
			{
				JButton keyButton = keyboard.keyButtons[i][j];
				String buttonText = keyboard.keyButtons[i][j].getText();
				addKeyBinding(keyButton, buttonText, buttonText + "Pressed");
			}
		}			
	}
	public static void checkKeyPressed()
	{
		String textTyped = textArea.getText();
		int length = textTyped.length();
		centerPanel.textPane.setBackground(Color.LIGHT_GRAY);
		String gameText = centerPanel.textOnPanel;
		if(length <= gameText.length())
		{
			if(gameText.substring(0, length).equals(textTyped))
			{
				String htmlBegin = "<html><b style='color: green;'>";
				String bTagEnd   = "</b>";
				String htmlEnd   = "</b></html>";
				centerPanel.textPane.setText(htmlBegin + gameText.substring(0, length) 
												+ bTagEnd + "<b style='color: red;'>" 
												+ gameText.substring(length, gameText.length()) + htmlEnd);
			//	centerPanel.textPane.setBackground(Color.GREEN);
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		}
		else
		{
			centerPanel.revalidate();
			centerPanel.repaint();
		}
		
	}
	public static void addKeyBinding(JButton keyButton, String keyText, String actionId)
	{
		InputMap im = keyboard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap ap = keyboard.getActionMap();
		String releasedId = "released " + keyText;
				
		//default action for pressed keys
		AbstractAction pressedAction = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				keyButton.setBackground(Color.BLACK);
				keyButton.setForeground(Color.WHITE);
			}
		};
		
		//default Action for released keys
		AbstractAction releasedAction =  new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//set de default color for buttons
				keyButton.setBackground(null);
				keyButton.setForeground(null);
				checkKeyPressed();
				
			}
		};
		
		switch(keyText)
		{
			case("Enter"):
			{
				//for the method KeyStroke.getKeyStroke("ENTER") to be recognized  
				keyText = "ENTER";
				releasedId = "released ENTER";
				//action for Enter, same + break line on TextArea
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						int caretPosition = textArea.getCaretPosition();
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						if (mode.equals("Game Mode"))
						{
							String textTyped = textArea.getText();
							String panelString = centerPanel.textOnPanel;
							String message;
							if (textTyped.equals(panelString))
							{
								message = "Congratulations!!!\n Text typed correctly";
							}
							else
							{
								message = ":( Try the next one";
							}
							JOptionPane.showMessageDialog(null, message, "Score", JOptionPane.DEFAULT_OPTION);
							textArea.setText("");
							
							String phraseFromList;
							if (input.hasNext())
							{
								phraseFromList = input.next();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "The phrases List is Over, well done!\n Try uploading your own list of phrases next", "ListOver", JOptionPane.DEFAULT_OPTION);
								phraseFromList = "List is Over :)";
							}
							
							centerPanel.setTextPane(phraseFromList);
							centerPanel.gamePanel.revalidate();
							centerPanel.gamePanel.repaint();
							
						}
						else
						{
							textArea.replaceRange("\n", caretPosition, caretPosition);
						}
					}
				};
				break;
			}
			case("'"):
			{
				keyText = "QUOTE";
				releasedId = "released QUOTE";
				break;
			}
			case("Tab"):
			{
				keyText = "TAB";
				releasedId = "released TAB";
				//make tab function on TextArea too since we disabled it before
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						textArea.append("\t");
					}
				};
				break;
			}
			case("Caps"):
			{
				keyText = "CAPS_LOCK";
				releasedId = "released CAPS_LOCK";
				break;
			}
			case("Shift"):
			{
				keyText = "shift SHIFT";
				releasedId = "released SHIFT";
				break;
			}
			case("Ctrl"):
			{
				keyText = "control CONTROL";
				releasedId = "released CONTROL";
				break;
			}
			case("Super"):
			{
				keyText = "WINDOWS";
				releasedId = "released WINDOWS";
				break;
			}
			case("Alt"):
			{
				keyText = "alt ALT";
			//  since control + alt or Alt Gr already makes an Action to release alt, we dont do it here
			//	releasedId = "released ALT";
				break;
			}
			case(" "):
			{
				keyText = "SPACE";
				releasedId = "released SPACE";
				break;
			}
			case("Alt Gr"):
			{
				keyText = "control alt ALT";
				releasedId = "released ALT";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						
						//change the control button to default color, since its being interpreted as pressed
						int row = keyboard.keysLayout.ctrlLocation[0];
						int col = keyboard.keysLayout.ctrlLocation[1];
						keyboard.keyButtons[row][col].setBackground(null);
						keyboard.keyButtons[row][col].setForeground(null);
					}
				};
				releasedAction = new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						//set de default color for buttons
						keyButton.setBackground(null);
						keyButton.setForeground(null);
						//since its the same releaseId as the Alt we also change the Alt button to default color, so  it works for both
						keyboard.keyButtons[3][2].setBackground(null);
						keyboard.keyButtons[3][2].setForeground(null);
					}
				};
				break;
			}
			case("\u2191"):
			{
				keyText = "UP";
				releasedId = "released UP";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						
						int caretPosition = textArea.getCaretPosition();
						int row = 0;
						int column = 0;
						int firstRow = 0;
						try
						{
							row = textArea.getLineOfOffset(caretPosition);
							column = caretPosition - textArea.getLineStartOffset(row);
						} catch (BadLocationException e1)
						{
							e1.printStackTrace();
							textArea.setCaretPosition(0);
						}
						if(row > 0)
						{
							row--;
							if(column > textArea.getDocument().getDefaultRootElement().getElement(row).getEndOffset())
							{
								textArea.setCaretPosition(textArea.getDocument().getDefaultRootElement().getElement(row).getEndOffset());
							}
							else
							{
								textArea.setCaretPosition(textArea.getDocument().getDefaultRootElement().getElement(row).getStartOffset() + column);
							}
						}
					}
				};
				break;
			}
			case("\u2193"):
			{
				keyText = "DOWN";
				releasedId = "released DOWN";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						
						// make Down funncionality of textArea work
						int caretPosition = textArea.getCaretPosition();
						int row = 0;
						int column = 0;
						int lastRow = 0;
						try
						{
							row = textArea.getLineOfOffset(caretPosition);
							lastRow = textArea.getLineOfOffset(textArea.getText().length());
							column = caretPosition - textArea.getLineStartOffset(row);
						} catch (BadLocationException e1)
						{
							textArea.setCaretPosition(textArea.getText().length());
							e1.printStackTrace();
						}
						catch (Exception IllegalArgumentException)
						{
							
						}
						if(row < lastRow)
						{
							row++;
							textArea.setCaretPosition(textArea.getDocument().getDefaultRootElement().getElement(row).getStartOffset() + column);
						}
					}
				};
				break;
			}
			case("<"):
			{
				keyText = "LEFT";
				releasedId = "released LEFT";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						// since we disabled the textArea keybinding we have to perform it here the action there
						int position = textArea.getCaretPosition();
						if (position > 0)
						{
							textArea.setCaretPosition(--position);
						}
					}
				};
				break;
			}
			case(">"):
			{
				keyText = "RIGHT";
				releasedId = "released RIGHT";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						// since we disabled the textArea keybinding we have to perform it here the action there
						int position = textArea.getCaretPosition();
						if (position >= 0 && position < textArea.getText().length())
						{
							textArea.setCaretPosition(++position);
						}
					}
				};
				break;
			}
			case("Backspace"):
			{
				keyText = "BACK_SPACE";
				releasedId = "released BACK_SPACE";
				pressedAction =  new AbstractAction()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						keyButton.setBackground(Color.BLACK);
						keyButton.setForeground(Color.WHITE);
						
						//Make Backspace functionatilty work
						String text = textArea.getText();
						int length = text.length();
						int selectionStart = textArea.getSelectionStart();
						int selectionEnd   = textArea.getSelectionEnd();
						int caretPosition  = textArea.getCaretPosition();
						
						if(length > 0)
						{
							//if there's something selected  and is not the end of string
							if(selectionStart >= 0 && selectionStart != length && selectionStart != selectionEnd)
							{
								textArea.setText(text.substring(0, selectionStart) + text.substring(selectionEnd, length));
								textArea.setCaretPosition(selectionStart);
							}
							//if the carot is in the middle of string
							else if(caretPosition != length && caretPosition > 0)
							{
								int first  = caretPosition - 1;
								int second = caretPosition;
								textArea.setText(text.substring(0, first) + text.substring(second, length));
								textArea.setCaretPosition(first);
							}
							else
							{
								length--;
								textArea.setText(text.substring(0, length));
							}
						}
					}
				};
				break;
			}
			case("-"):
			{
				keyText = "MINUS";
				releasedId = "released MINUS";
				break;
			}
			case("="):
			{
				keyText = "EQUALS";
				releasedId = "released EQUALS";
				break;
			}
			case("´"):
			{
				keyText = "DEAD_ACUTE";
				releasedId = "released DEAD_ACUTE";
				break;
			}
			case("["):
			{
				keyText = "OPEN_BRACKET";
				releasedId = "released OPEN_BRACKET";
				break;
			}
			case("]"):
			{
				keyText = "CLOSE_BRACKET";
				releasedId = "released CLOSE_BRACKET";
				break;
			}
			case("~"):
			{
				
				keyText = "DEAD_TILDE";
				releasedId = "released DEAD_TILDE";
				break;
			}
			case(","):
			{
				keyText = "COMMA";
				releasedId = "released COMMA";
				break;
			}
			case("."):
			{
				keyText = "PERIOD";
				releasedId = "released PERIOD";
				break;
			}
			case(";"):
			{
				keyText = "SEMICOLON";
				releasedId = "released SEMICOLON";
				break;
			}
			case("Ç"):
			{
				keyText = "UNDEFINED";
				releasedId = "released UNDEFINED";
				break;
			}
		}

		im.put(KeyStroke.getKeyStroke(keyText), actionId);
		im.put(KeyStroke.getKeyStroke(releasedId), "released " + keyText);
		
		ap.put(actionId, pressedAction);
		ap.put("released " + keyText, releasedAction);
		
		
		// Handling modifiers 
		im.put(KeyStroke.getKeyStroke("shift " + keyText), "shift pressed " + keyText);
		im.put(KeyStroke.getKeyStroke("shift released " + keyText), "shift released " + keyText);
		
		im.put(KeyStroke.getKeyStroke("control " + keyText), "control pressed " + keyText);
		im.put(KeyStroke.getKeyStroke("control released " + keyText), "control released " + keyText);

		im.put(KeyStroke.getKeyStroke("alt " + keyText), "alt pressed " + keyText);
		im.put(KeyStroke.getKeyStroke("alt released " + keyText), "alt released " + keyText);
		
		im.put(KeyStroke.getKeyStroke("control alt " + keyText), "control pressed " + keyText);
		im.put(KeyStroke.getKeyStroke("control alt released " + keyText), "control released " + keyText);
		
		
		
		
		ap.put("shift pressed " + keyText, pressedAction);
		ap.put("shift released " + keyText, releasedAction);
		
		ap.put("control pressed " + keyText, pressedAction);
		ap.put("control released " + keyText, releasedAction);
		
		ap.put("alt pressed " + keyText, pressedAction);
		ap.put("alt released " + keyText, releasedAction);
		
		ap.put("control alt pressed " + keyText, pressedAction);
		ap.put("control alt released " + keyText, releasedAction);
		
		ap.put("control alt shift " + keyText, pressedAction);
		ap.put("control alt shift released " + keyText, releasedAction);
		
	}
}
