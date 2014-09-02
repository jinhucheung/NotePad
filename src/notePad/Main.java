package notePad;

import javax.swing.*;


/**
 * This is a notePad program
 * @version 2014-6-7
 * @author ZJH
 */

public class Main {

	public static void main(String args[]) {
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Windows观感
			// 这种观感JColorChooser出不来  且保存后有个乱码
		UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		new MainFrame();
	}

}
