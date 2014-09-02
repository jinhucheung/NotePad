package notePad;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.event.*;

@SuppressWarnings("serial")
public class Find_Replace extends JDialog implements ActionListener {
	MainFrame MF;
	JTextArea text;

	JTextField findText;
	JTextField replaceText;

	JButton findNextB;
	JButton findAllB;
	JButton replaceB;
	JButton replaceAllB;
	JButton No;

	JCheckBox Upper_lower;
	JRadioButton up;
	JRadioButton down;

	boolean up_lowFlag;
	int RadioSign; // down为0 up为1

	int findPos; // 搜索到文本时 光标的位置
	boolean findFlag; // 标记是否找到
	String find;

	public Find_Replace(MainFrame frame) {
		super(frame, "查找和替换");
		MF = frame;
		text = MF.text;
		setSize(480, 200);
		setLocation(460, 250);
		buildPane(getContentPane());
		setDisenable();
		setVisible(true);
		addListener();
		up_lowFlag = true;//区分大小写
	}

	public void buildPane(Container pane) {

		Box textBox1 = Box.createHorizontalBox();
		Box textBox2 = Box.createHorizontalBox();

		Box SelectBox = Box.createHorizontalBox();
		Box ButtonBox = Box.createHorizontalBox();

		// 文本框面板
		findText = new JTextField();
		replaceText = new JTextField();
		JLabel findLabel = new JLabel("查找内容(N):", JLabel.CENTER);
		JLabel replaceLabel = new JLabel("替换为(P):", JLabel.CENTER);

		textBox1.add(Box.createHorizontalStrut(15));
		textBox1.add(findLabel);
		textBox1.add(Box.createHorizontalStrut(5));
		textBox1.add(findText);
		textBox1.add(Box.createHorizontalStrut(15));

		textBox2.add(Box.createHorizontalStrut(15));
		textBox2.add(replaceLabel);
		textBox2.add(Box.createHorizontalStrut(5));
		textBox2.add(replaceText);
		textBox2.add(Box.createHorizontalStrut(15));

		// 选择框面板
		Upper_lower = new JCheckBox("区分大小写(C)");
		up = new JRadioButton("向上(U)");
		down = new JRadioButton("向下(D)");
		down.setSelected(true);
		ButtonGroup group = new ButtonGroup();
		group.add(up);
		group.add(down);

		Box Radiobox = Box.createHorizontalBox();
		Radiobox.setBorder(BorderFactory.createTitledBorder("方向"));
		Radiobox.add(down);
		Radiobox.add(Box.createHorizontalStrut(5));
		Radiobox.add(up);

		SelectBox.add(Upper_lower);
		SelectBox.add(Box.createHorizontalStrut(5));
		SelectBox.add(Radiobox);

		// 按钮面板
		findNextB = new JButton("查找下一个");
		findAllB = new JButton("查找全部");
		replaceB = new JButton("替换");
		replaceAllB = new JButton("全部替换");
		No = new JButton("取消");

		ButtonBox.add(findNextB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(findAllB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(replaceB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(replaceAllB);
		ButtonBox.add(Box.createHorizontalStrut(5));
		ButtonBox.add(No);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(Box.createVerticalStrut(10));
		pane.add(textBox1);
		pane.add(Box.createVerticalStrut(5));
		pane.add(textBox2);
		pane.add(Box.createVerticalStrut(5));
		pane.add(SelectBox);
		pane.add(Box.createVerticalStrut(5));
		pane.add(ButtonBox);
		pane.add(Box.createVerticalStrut(5));

	}

	public void addListener() {
		findNextB.addActionListener(this);
		findAllB.addActionListener(this);
		replaceB.addActionListener(this);
		replaceAllB.addActionListener(this);
		No.addActionListener(this);

		MF.findnext.addActionListener(this);
		// 选择框
		Upper_lower.addActionListener(this);
		up.addActionListener(this);
		down.addActionListener(this);

		CaretListener CareListen = new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO 自动生成的方法存根
				setEnable();
			}
		};

		findText.addCaretListener(CareListen);
		replaceText.addCaretListener(CareListen);

	}

	// 初始化按钮状态
	public void setDisenable() {
		findNextB.setEnabled(false);
		findAllB.setEnabled(false);
		replaceB.setEnabled(false);
		replaceAllB.setEnabled(false);

		Upper_lower.setSelected(true);
	}

	// 当文本框有文本时 激活按钮状态
	public void setEnable() {
		if (findText.getText().length() > 0) {
			findNextB.setEnabled(true);
			findAllB.setEnabled(true);
			if (replaceText.getText().length() > 0) {
				replaceB.setEnabled(true);
				replaceAllB.setEnabled(true);
			} else {
				replaceB.setEnabled(false);
				replaceAllB.setEnabled(false);
			}
		} else
			setDisenable();

	}

	public void seek() {

		find = findText.getText();
		int length = find.length();
		// 向下查找
		if (RadioSign == 0) {
			findFlag = false;
			findPos = text.getCaretPosition();
			for (int pos = findPos; pos < text.getText().length(); pos++) {
				text.select(pos, pos + length); // 光标选中字符

				String textCopy = text.getSelectedText();
				// 区分大小写 up_lowFlag=true 表区分
			
				if (!up_lowFlag) {
					find = find.toLowerCase();
					textCopy = textCopy.toLowerCase();
				}

				if (find.equals(textCopy)) {
					findPos = text.getCaretPosition();
					// System.out.println("find");
					findFlag = true;
					break;
				}
				if (!findFlag) {
					// System.out.println("No find ");
					text.setCaretPosition(++findPos);  //这里是让光标移动到末位
				}
			}
		}

		if (RadioSign == 1) {
			// 当光标选中时 找到了字符串 但光标又移动了 使文本区无光标选中的表示 所以 下一次查找时 再让光标移位
			if (findFlag) {
				text.setCaretPosition(findPos);
				// System.out.println("up" + findPos);
			}
			findFlag = false;
			findPos = text.getCaretPosition();
			for (int pos = findPos; pos > 0; --pos) {
				text.select(pos - length, pos);

				String textCopy = text.getSelectedText();
				// 区分大小写 up_lowFlag=true 表区分
				if (!up_lowFlag) {
					find = find.toLowerCase();
					textCopy = textCopy.toLowerCase();
				}
				if (find.equals(textCopy)) {
					findPos -= length;
					findFlag = true;
					break;
				}
				if (!findFlag) {
					// System.out.println("No find ");
					text.setCaretPosition(--findPos);
				}
			}
		}

		// 提示对话框
		if (!findFlag)
			JOptionPane.showMessageDialog(MF, "找不到 " + find, "记事本",
					JOptionPane.CANCEL_OPTION);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == Upper_lower) {
			if (Upper_lower.isSelected())
				up_lowFlag = true; // 区分
			else
				up_lowFlag = false;// 不区分
		}

		if (source == down) {
			RadioSign = 0; // 向下
		}
		if (source == up) {
			RadioSign = 1;// 向上
		}

		if (source == findNextB || source == findAllB) {
			seek();
		}
		if (source == replaceB) {
			seek();
			if (findFlag) {
				text.replaceSelection(replaceText.getText());
			}
		}
		if (source == replaceAllB) {
			text.setCaretPosition(0);
			int before = RadioSign;
			RadioSign = 0;
			while (text.getCaretPosition() < text.getText().length()) {
				seek();
				if (findFlag)
					text.replaceSelection(replaceText.getText());
			}
			RadioSign = before;
		}
		if (source == No) {
			dispose();
		}

		if (source == MF.findnext) {
			if (findText.getText().length() > 0) {
				seek();
			} else
				setVisible(true);
		}
	}
}

