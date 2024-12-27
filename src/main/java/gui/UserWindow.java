package gui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.table.Table;
import me.kazury.enkanetworkapi.genshin.data.GenshinUserCharacter;
import me.kazury.enkanetworkapi.genshin.data.GenshinUserInformation;

import java.util.List;

public class UserWindow extends BasicWindow {
	
	GenshinUserInformation userInfo;
	
	public UserWindow(String NewUid) {
		setHints(List.of(Hint.CENTERED, Hint.EXPANDED));
		Table<String> charsTable = new Table<>("ID", "Character Name", "Character Level", "Constellation");
		charsTable.setSelectAction(() -> {
			int charID = Integer.parseInt(charsTable.getTableModel().getCell(0, charsTable.getSelectedRow()));
			GenshinUserCharacter selectedChar = null;
			for (GenshinUserCharacter character : userInfo.getCharacters()) {
				if (character.getId() == charID) {
					selectedChar = character;
					break;
				}
			}
			GUIConnector.getGui().addWindowAndWait(new CharacterWindow(selectedChar, NewUid));
		});
		app.App.getApi().fetchGenshinUser(NewUid, user -> {
			
			userInfo = user.toGenshinUser();
			
			for (GenshinUserCharacter character : userInfo.getCharacters()) {
				charsTable.getTableModel().addRow(String.valueOf(character.getId()), character.getGameData().getName(), String.valueOf(character.getCurrentLevel()), String.valueOf(character.getConstellation()));
			}
			
		});
		Panel charsPanel = new Panel();
		charsPanel.addComponent(charsTable.withBorder(Borders.singleLineBevel()));
		setComponent(charsPanel);
	}
	
}
