package KeyboardLayouts;

public class USLayout extends GenericLayout
{
	public USLayout()
	{
		final String keysLayout[][] = 
		{
				{ "`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace" },
				{ "Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\" },
				{ "Caps", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "'", "Enter" },
				{ "Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "\u2191" },
				{"Ctrl", "Super", "Alt", " ", "Alt Gr", "<", "\u2193", ">" }
		};
		ctrlLocation = new int[] { 4, 0 };
		this.enterIpadx = 41;
		super.setKeys(keysLayout);
	}
}
