package KeyboardLayouts;
import java.awt.Insets;

import javax.swing.JButton;

public abstract class GenericLayout
{
	private static String keys[][] = new String[5][];
	private static JButton[][] keyButtons = new JButton[5][];
	public static int ctrlLocation[];
	public void setKeys(String keysArray[][])
	{
		keys = keysArray;
	}
	public static String[][] getKeys()
	{
		return keys;
	}
	
	public static Insets insets = new Insets(5, 5, 0, 0);
	public static Insets altGrInset = new Insets(5, 5, 0, 65);
	public static Insets upperArrowKeyInsets = new Insets(5, 32, 0, 0);
	
	public static int backspaceIpadx           = 10;
	public static int tabIpadx                 = 22;
	public static int capsIpadx                = 14;
	public static int enterIpadx               = 40;
	public static int shiftIpadx               = 45;
	public static int keyLeftToUpperArrowIpadx = 8;   // it varies depending on the Layout, on US Keyboard Layout is /
	public static int ctrlIpadx                = 7;
	public static int superIpadx               = 2;
	public static int altIpadx                 = 25;
	public static int spaceIpadx               = 233;
	public static int altGrIpadx               = 2;
	
	public static int capsGridx = 0;
	public static int capsGridy = 1;
	
}
