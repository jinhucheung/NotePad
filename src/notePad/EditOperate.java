package notePad;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;

public class EditOperate implements ActionListener {
	MainFrame MF;
	JTextArea text;

	Clipboard clipboard;// 剪贴板
	// StringSelection contents;

	// "撤销"操作用的字符串 前字符串和后字符串
	String before;
	String after;

	java.util.Date dat;

	Find_Replace FR;
	boolean findRepalceFlag;

	public EditOperate(MainFrame frame) {
		MF = frame;
		text = MF.text;
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();// 其实 到这里
																		// 程序已经得到了系统剪切板的功能

		setdisEnabled();
		addListener();

		text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {

				getRanks();// 实时行列数
				setEnabeld();
			}
		});
		/*
		 * 自己写的自动换行 直接用JTextArea的setLineWrap(true) 效果较好 text.addKeyListener(new
		 * KeyAdapter() {
		 * 
		 * @Override public void keyPressed(KeyEvent e) { // TODO 自动生成的方法存根 if
		 * (MF.newLine.isSelected()) { System.out.println(MF.col); if (MF.col >=
		 * 92) { text.append("\r\n"); } } } });
		 */
	}

	public void addListener() {
		MF.cancel.addActionListener(this);
		MF.cut.addActionListener(this);
		MF.copy.addActionListener(this);
		MF.paste.addActionListener(this);
		MF.del.addActionListener(this);
		MF.select.addActionListener(this);
		MF.date.addActionListener(this);

		MF.find.addActionListener(this);
		MF.findnext.addActionListener(this);
		MF.replace.addActionListener(this);
		MF.turn.addActionListener(this);

		// 弹出菜单
		MF.popCancel.addActionListener(this);
		MF.popCut.addActionListener(this);
		MF.popCopy.addActionListener(this);
		MF.popPaste.addActionListener(this);
		MF.popDel.addActionListener(this);
		MF.popSelect.addActionListener(this);
	}

	// 根据剪贴板中的字符内容 判断是否可"粘贴"
	public boolean isPaste() {
		String str = null;
		try {
			str = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (IOException | UnsupportedFlavorException e) {
			// 剪贴板内容为空时 抛出
			//e.printStackTrace();
			System.out.println("当前系统剪切板无字符内容");
		}
		return str != null && str != null;
	}

	// "编辑"菜单的可用性设置
	public void setdisEnabled() {
		// 初始状态
		MF.cancel.setEnabled(false);
		MF.cut.setEnabled(false);
		MF.copy.setEnabled(false);
		MF.del.setEnabled(false);
		MF.find.setEnabled(false);
		MF.findnext.setEnabled(false);
		if (!isPaste()) {
			MF.paste.setEnabled(false);
			MF.popPaste.setEnabled(false);
		}
		MF.popCancel.setEnabled(false);
		MF.popCut.setEnabled(false);
		MF.popCopy.setEnabled(false);
		MF.popDel.setEnabled(false);
	}

	public void setEnabeld() {
		MF.cancel.setEnabled(true);

		MF.popCancel.setEnabled(true);

		if (isPaste()) {
			MF.paste.setEnabled(true);
			MF.popPaste.setEnabled(true);
		} else {
			MF.paste.setEnabled(false);
			MF.popPaste.setEnabled(false);
		}
		if (text.getText().length() > 0) {
			MF.find.setEnabled(true);

			if (text.getSelectedText() != null) {
				MF.cut.setEnabled(true);
				MF.copy.setEnabled(true);
				MF.del.setEnabled(true);

				MF.popCut.setEnabled(true);
				MF.popCopy.setEnabled(true);
				MF.popDel.setEnabled(true);

			} else {
				MF.cut.setEnabled(false);
				MF.copy.setEnabled(false);
				MF.del.setEnabled(false);

				MF.popCut.setEnabled(false);
				MF.popCopy.setEnabled(false);
				MF.popDel.setEnabled(false);

			}
		} else {
			MF.find.setEnabled(false);
			MF.findnext.setEnabled(false);
		}

	}

	// 撤销 这功能的实现 写得不太好
	public void Cancel() {
		after = text.getText();
		text.setText(before); // 设置之前的文本

		text.selectAll();

		/*
		 * int pos = text.getCaretPosition(); text.insert(before, pos); try {
		 * text.select(pos, text.getLineEndOffset(text.getLineOfOffset(pos))); }
		 * catch (BadLocationException e) { e.printStackTrace(); }
		 */
		String temp = before;
		before = after;
		after = temp;

	}

	// 转到指定行
	public void Turn() {

		String str = JOptionPane.showInputDialog(MF, "行号(L):", "转到指定行",
				JOptionPane.DEFAULT_OPTION);
		int LineNum = 1;
		try {
			LineNum = Integer.valueOf(str);
		} catch (NumberFormatException e1) {
			System.out.println("正确输入");
		}
		int textLine = text.getLineCount();
		if (LineNum > textLine)
			for (int i = textLine; i < LineNum; i++)
				text.append("\r\n");
		else
			try {
				text.setCaretPosition(text.getLineStartOffset(LineNum - 1));
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
	}

	// 获取实时的行列
	public void getRanks() {
		int pos = text.getCaretPosition();
		MF.col = pos - text.getText().substring(0, pos).lastIndexOf("\n");
		
		try {
			MF.row = text.getLineOfOffset(text.getCaretPosition()) + 1;
			MF.ranks.setText("第 " + MF.row + " 行 , 第 " + MF.col
					+ " 列            ");
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem) e.getSource();
		if (source == MF.cancel || source == MF.popCancel) {
			Cancel();
		}
		// JTextComponent的剪切,复制,粘贴方法 JTextArea已实现
		if (source == MF.cut || source == MF.popCut) {
			text.cut();
		}
		if (source == MF.copy || source == MF.popCopy) {
			text.copy();
		}
		if (source == MF.paste || source == MF.popPaste) {
			text.paste();
		}

		if (source == MF.del || source == MF.popDel) {
			text.replaceSelection(null);
		}
		if (source == MF.select || source == MF.popSelect) {
			text.selectAll();
		}
		if (source == MF.date) {
			dat = new java.util.Date();
			text.append(dat.toString());
		}

		if (source == MF.find || source == MF.replace) {
			if (!findRepalceFlag) {
				FR = new Find_Replace(MF);
				MF.findnext.setEnabled(true); // 在Find_Replace实例后 才可用
				findRepalceFlag = true;
			} else
				FR.setVisible(true);
		}
		if (source == MF.turn) {
			Turn();

		}
	}

}
