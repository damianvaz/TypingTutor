package KeyboardLayouts;

import java.awt.Insets;

public class BRLayout extends GenericLayout
{
	public BRLayout()
	{
		String keysLayout[][] = 
		{
				{ "'", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace" },
				{ "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "´", "[", "Enter",
				  "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Ç", "~", "]" },
				{ "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", ";", "\u2191" },
				{"Ctrl", "Super", "Alt", " ", "Alt Gr", "<", "\u2193", ">" }
		};
		ctrlLocation = new int[] { 3, 0 };
		super.setKeys(keysLayout);
		enterIpadx = 17;
	}
}
