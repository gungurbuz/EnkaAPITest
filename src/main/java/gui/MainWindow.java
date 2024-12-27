package gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import me.kazury.enkanetworkapi.enka.EnkaNetworkAPI;

import java.util.List;

public class MainWindow extends BasicWindow {
	private static TextBox UIDBox;
	EnkaNetworkAPI api = app.App.api;
	
	public MainWindow() {
		setHints(List.of(Hint.CENTERED, Hint.EXPANDED));
		Panel mainPanel = new Panel();
		Label instructionLabel = new Label("Enter Genshin UID:");
		Button enterButton = new Button("Enter", () -> GUIConnector.getGui().addWindow(new UserWindow(getUID())));
		UIDBox = new TextBox(new TerminalSize(12,1));
		mainPanel.addComponent(instructionLabel);
		mainPanel.addComponent(UIDBox.withBorder(Borders.singleLineBevel()));
		mainPanel.addComponent(enterButton.withBorder(Borders.singleLineBevel()));
		setComponent(mainPanel);
	}
	
	public static String getUID(){
		return UIDBox.getText();
	}
}
