package notePad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ViewHelpOperate implements ActionListener {
	MainFrame MF;
	Desktop desktop;

	public ViewHelpOperate(MainFrame frame) {
		MF = frame;
		MF.state.addActionListener(this);
		MF.vhelp.addActionListener(this);
		MF.about.addActionListener(this);
		desktop = Desktop.getDesktop();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		Object source = e.getSource();
		if (source == MF.state) {
			if (MF.state.isSelected()) {
				MF.getContentPane().add(MF.ranks, BorderLayout.SOUTH);
			} else {
				MF.getContentPane().remove(1);
			}
			MF.setVisible(true);
			
		}

		if (source == MF.vhelp) {// 桌面API
			try {
				desktop.browse(new URI(
						"http://windows.microsoft.com/zh-cn/windows/notepad-faq#1TC=windows-7"));
			} catch (IOException | URISyntaxException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}

		if (source == MF.about) {
			String message = "           本程序为大一下学期期末作业\n             以Win7记事本为原型制作\n                                             Jh\n                                    2014.6.7";
			JOptionPane.showMessageDialog(MF, message, "关于",
					JOptionPane.DEFAULT_OPTION);
		}
	}

}
