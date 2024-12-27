package gui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class GUIConnector {
	
	private static final WindowBasedTextGUI gui;
	
	static {
		try {
			Terminal term = new DefaultTerminalFactory().createTerminal();
			Screen screen = new TerminalScreen(term);
			gui = new MultiWindowTextGUI(screen);
			screen.startScreen();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static synchronized WindowBasedTextGUI getGui() {
		return gui;
		
	}
	
}


