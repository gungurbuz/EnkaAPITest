package gui;

import com.googlecode.lanterna.gui2.AnimatedLabel;
import com.googlecode.lanterna.gui2.BasicWindow;

public class LoadingWindow extends BasicWindow {
	public LoadingWindow(){
		super("Loading");
		setComponent(AnimatedLabel.createClassicSpinningLine());
	}
}
