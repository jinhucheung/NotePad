package notePad;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileOperate implements ActionListener {

	MainFrame MF;
	JFileChooser Chooser;
	JTextArea text;
	JMenuItem openfile;

	// 当先"打开"了文件后 按"保存"就不需弹出对话窗
	boolean openFlag;
	// int endOffSet; 用文本光标最后偏移量判断文本是否改变 不准确 用before after代替

	String before;
	String after;

	public FileOperate(MainFrame frame) {
		MF = frame;
		text = frame.text;
		before = "";
		Chooser = new JFileChooser();

		// 文件类型过滤器
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"文本文档(*.txt)", "txt", "TXT");
		// 去"所有类型"的选项
		// Chooser.setAcceptAllFileFilterUsed(false);
		Chooser.setFileFilter(filter);
		Chooser.setSelectedFile(new File("*.txt")); // 设置默认文件

		// 注册监听器
		frame.newFile.addActionListener(this);
		frame.openFile.addActionListener(this);
		frame.saveFile.addActionListener(this);
		frame.saveAsFile.addActionListener(this);
		frame.pageSet.addActionListener(this);
		frame.print.addActionListener(this);
		frame.exit.addActionListener(this);

		MF.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
	}

	public void open() {
		if (JFileChooser.APPROVE_OPTION == Chooser.showOpenDialog(MF)) {
			File f = Chooser.getSelectedFile();
			// path = Chooser.getSelectedFile().getAbsolutePath();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String s = "";
				String line = reader.readLine();
				while (line != null) {
					s += line;
					s += "\n";
					line = reader.readLine();
				}
				text.setText(s);
				reader.close();

				openFlag = true; // 已打开文件
				// endOffSet = text.getLineEndOffset(text.getLineCount() - 1);
				// // 获得当前打开文本的光标的最后偏移量

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	// "保存"与"另存为" 用openFlag控制
	public void save() {

		String path = null; // 存储文件的路径
		int n = 0; // n表示 当打开文件保存对话框时 按下按钮的值 确定-1 取消-0
		if (openFlag)
			path = Chooser.getSelectedFile().getAbsolutePath(); // 已打开文件的绝对路径
		else {
			n = Chooser.showSaveDialog(MF);
			path = Chooser.getSelectedFile().getAbsolutePath(); // 新路径
		}
		if (n != 1) {
			File f = new File(path);
			// System.out.println(path);
			try {
				// 一行一行地存储文档到指定文件
				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				text.append("\0"); // 在文件尾添加空格 防止文本缺失和错误获取不存在的位置
				for (int i = 0; i < text.getLineCount(); i++) {
					/*
					 * System.out.println(text.getLineStartOffset(i) + "  " +
					 * text.getLineEndOffset(i));
					 */
					String str = text.getText(
							text.getLineStartOffset(i),
							text.getLineEndOffset(i)
									- text.getLineStartOffset(i) - 1)
							+ "\r\n";
					writer.write(str);

				}
				writer.close();
			} catch (IOException | BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void print() {
		String message = "在您可以执行与打印有关的任务(例如页面设置或打印一个文档)之前，\n您必须已经安装打印机。您想现在安装打印机吗?";
		int n = JOptionPane.showConfirmDialog(MF, message, "对话-记事本",
				JOptionPane.OK_OPTION);
		if (n == 0) {
			String warn = "无法打开' 添加打印机 ', 本地后台处理程序服务没有运行";
			JOptionPane.showMessageDialog(MF, warn, "打印机",
					JOptionPane.CANCEL_OPTION);
		}
	}

	public void exit() {
		try {
			int n = 0;
			if (text.getText().length() > 0) {
				// 与之前的文本光标偏移量比较 判断当前文本光标偏移量是否改变 这里控制不太好
				// if (text.getLineEndOffset(text.getLineCount() - 1) !=
				// endOffSet)
				// 用after before 判断
				after = text.getText();
				if (!before.equals(after)) {
					n = JOptionPane.showConfirmDialog(MF, "是否保存已有文本", "记事本", 0);
					if (n == 0)
						if (openFlag) // "保存"
							save();
						else { // "另存为"
							openFlag = false;
							save();
						}
				}
			}
			if (n != -1) // 按下"否"返回 1 "是" 0
				System.exit(0);
		} catch (HeadlessException e1) {

			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == MF.newFile) {
			if (text.getText().length() > 0) {
				int n = 0;
				if (n == JOptionPane
						.showConfirmDialog(MF, "是否保存已有文本", "记事本", 0))
					save();
			}
			text.setText(null);

			before = "";
			openFlag = false;
			// endOffSet = 0;
			// 设"撤销"不能用
			MF.cancel.setEnabled(false);
			MF.popCancel.setEnabled(false);
		}

		if (source == MF.openFile) {
			if (text.getText().length() > 0) {
				int n = 0;
				if (n == JOptionPane
						.showConfirmDialog(MF, "是否保存已有文本", "记事本", 0))
					save();
			}
			open();
			before = text.getText();
		}

		if (source == MF.saveFile) {
			if (text.getText().length() > 0) {
				save();
				before = text.getText();
			}
		}

		if (source == MF.saveAsFile) {
			if (text.getText().length() > 0) {
				openFlag = false;
				save();
			}
		}

		if (source == MF.pageSet) {
			print();
		}

		if (source == MF.print) {
			print();
		}

		if (source == MF.exit) {
			exit();
		}
	}

}
