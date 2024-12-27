package gui;

import app.App;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import me.kazury.enkanetworkapi.enka.EnkaNetworkAPI;
import me.kazury.enkanetworkapi.genshin.data.GenshinUserCharacter;
import me.kazury.enkanetworkapi.genshin.data.GenshinUserInformation;
import me.kazury.enkanetworkapi.genshin.data.GenshinUserWeapon;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.googlecode.lanterna.gui2.LinearLayout.createLayoutData;
import static java.util.Objects.isNull;

public class CharacterWindow extends BasicWindow {
	EnkaNetworkAPI api = App.api;
	@NotNull
	static GenshinUserWeapon userWeapon;
	
	public CharacterWindow(GenshinUserCharacter selectedChar, String userUID) {
		GenshinUserCharacter currentChar = selectedChar;
		int charID = selectedChar.getId();
		setHints(List.of(Hint.CENTERED, Hint.EXPANDED));
		
		Panel mainPanel = new Panel(new GridLayout(3)); // Set up the grid layout
		// Left side of main panel, includes character level, talent levels and cons
		Panel charInfo = new Panel(new LinearLayout(Direction.VERTICAL));
		charInfo.setLayoutData(createLayoutData(LinearLayout.Alignment.Center));
		Label charName = new Label(currentChar.getGameData().getName());
		charInfo.addComponent(charName.withBorder(Borders.singleLineBevel()));
		Label charLevel = new Label(Objects.toString(currentChar.getCurrentLevel()) + "/" + Objects.toString(currentChar.getMaxLevel()));
		charInfo.addComponent(charLevel.withBorder(Borders.singleLineBevel("Character Level")));
		Table<Integer> talentLevelsTable = new Table<>("NA", "ES", "EB");
		talentLevelsTable.getTableModel().addRow(currentChar.getTalentLevels().getNormalAttackLevel(), currentChar.getTalentLevels().getElementalSkillLevel(), currentChar.getTalentLevels().getElementalBurstLevel());
		charInfo.addComponent(talentLevelsTable.withBorder(Borders.singleLineBevel("Talent Levels")));
		Label charCons = new Label(Objects.toString(currentChar.getConstellation()) + "/" + "6");
		charInfo.addComponent(charCons.withBorder(Borders.singleLineBevel("Character Constellations")));
		mainPanel.addComponent(charInfo.withBorder(Borders.singleLineBevel())); // Add the panel to the left side of the screen
		// Middle of the main panel, includes character and weapon stats
		reFetchWeapon(charID, userUID);
		
		
		Panel charStats = new Panel(new LinearLayout(Direction.VERTICAL));
		Panel weaponInfo = new Panel(new GridLayout(3));
		Label weaponName = new Label(userWeapon.getName());
		weaponInfo.addComponent(weaponName.withBorder(Borders.singleLineBevel("Weapon Name")));
		Label weaponType = new Label(weaponTypeCheck(currentChar.getGameData().getWeaponType()));
		weaponInfo.addComponent(weaponType.withBorder(Borders.singleLineBevel("Weapon Type")));
		Label weaponRarity = new Label(Objects.toString(userWeapon.getStar()) + "-star");
		weaponInfo.addComponent(weaponRarity.withBorder(Borders.singleLineBevel("Rarity")));
		Label weaponRefinement = new Label("R" + Objects.toString(userWeapon.getWeaponRefinement()));
		final List<GenshinUserWeapon.WeaponStat> stats = userWeapon.getStats();
		Label weaponAttack = new Label(stats.getFirst().getFormattedValue());
		Label weaponStat = new Label (stats.get(1).getFormattedValue());
		weaponInfo.addComponent(weaponRefinement).withBorder(Borders.singleLineBevel());
		weaponInfo.addComponent(weaponAttack).withBorder(Borders.singleLineBevel("Base ATK"));
		weaponInfo.addComponent(weaponStat).withBorder(Borders.singleLineBevel(stats.get(1).getStat()));
		mainPanel.addComponent(charStats).withBorder(Borders.singleLineBevel());
		
		//Right side of the main panel, includes artifact data
		mainPanel.addComponent(new EmptySpace(new TerminalSize(10, 10)));
		setComponent(mainPanel);
	}
	
	private static void reFetchWeapon(int charID, String userUID) {
		App.api.fetchGenshinUser(userUID, (user) -> {
			final GenshinUserInformation info = user.toGenshinUser();
			
			for (GenshinUserCharacter character : info.getCharacters()) {
				if (character.getId()== charID){
					setWeapon(character.getEquippedWeapon());
				}
			}
		});
	}
	
	public static String weaponTypeCheck(String weaponData) {
		return switch (weaponData) {
			case "WEAPON_SWORD_ONE_HAND" -> "Sword";
			case "WEAPON_CATALYST" -> "Catalyst";
			case "WEAPON_BOW" -> "Bow";
			case "WEAPON_POLE" -> "Polearm";
			case "WEAPON_CLAYMORE" -> "Claymore";
			default -> throw new IllegalStateException("Unexpected value: " + weaponData);
		};
		
		
	}
	
	private static void setWeapon(GenshinUserWeapon newWeapon){
		userWeapon = newWeapon;
	}
	
}