package app;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import gui.GUIConnector;
import gui.MainWindow;
import me.kazury.enkanetworkapi.enka.EnkaNetworkAPI;


public class App {
	
	public static EnkaNetworkAPI api;
	
	public static EnkaNetworkAPI getApi() {
		return api;
	}
	
	static WindowBasedTextGUI gui = GUIConnector.getGui();
	public static void main(String[] args) {
		api = new EnkaNetworkAPI().build();
		gui.addWindowAndWait(new MainWindow());
	}
}
