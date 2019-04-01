package team1.util;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyBorderFactory {

	public static Border createMyBorder(String title) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		return titled;
	}

	public static Border createMyBorder(String title, int titleAlignement) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		TitledBorder titled = BorderFactory.createTitledBorder(loweredetched, title);
		titled.setTitleJustification(titleAlignement);
		return titled;
	}

}
