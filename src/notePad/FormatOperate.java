package notePad;

import javax.swing.*;
import javax.swing.event.*;

import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class FormatOperate implements ActionListener, ListSelectionListener {
	MainFrame MF;
	JTextArea text;

	// 字体
	JDialog fontDialog;
	JTextField FontT;
	JTextField Shapes;
	JTextField Size;

	JList listFont;
	JList listShapes;
	JList listSize;

	JTextField demo;
	String demoStr;
	JComboBox scripts;

	JButton Yes;
	JButton No;

	Font font;
	int style;
	int size;
	boolean SizeFlag;
	List<Integer> SizeNum;
	List<String> SizeStr;

	public FormatOperate(MainFrame frame) {
		MF = frame;
		text = MF.text;
		buildDialog();
		addListener();
	}

	// 设字体的对话框
	public void buildDialog() {
		fontDialog = new JDialog(MF, "字体");
		fontDialog.setSize(400, 250);
		fontDialog.setLocation(500, 200);
		init();
		DialogPane(fontDialog.getContentPane());
		// fontDialog.setVisible(true);
	}

	public void init() {
		// 获得系统字体
		String[] fontName = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		listFont = new JList<>(fontName);

		// 字体风格
		String[] fontShapes = { "常规", "粗体", "倾斜", "粗体  倾斜" };
		listShapes = new JList<>(fontShapes);
		String[] fontArray = { "8", "9", "10", "11", "12", "14", "16", "18",
				"20", "22", "24", "26", "28", "36", "48", "72", "初号", "小号",
				"一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五",
				"六号", "小六", "七号", "八号" };
		listSize = new JList<>(fontArray);
		// 文本框
		listShapes.setSelectedIndex(0);
		listSize.setSelectedIndex(0);

		FontT = new JTextField("宋体");
		Shapes = new JTextField("常规");
		Size = new JTextField("8");
		FontT.setEditable(false);
		Shapes.setEditable(false);

		// 示例
		demoStr = "你好,记事本 ";
		demo = new JTextField(demoStr);
		demo.setBorder(BorderFactory.createTitledBorder("示例"));
		demo.setEditable(false);
		demo.setHorizontalAlignment(JTextField.CENTER); // 文本居中
		font = new Font("Myfont", Font.PLAIN, 18);
		demo.setFont(font);

		Yes = new JButton("确定");
		No = new JButton("取消");

		String[] scriptName = { "中文", "英文", "数字" };
		scripts = new JComboBox<>(scriptName);
		// scripts.setBorder(BorderFactory.createTitledBorder("脚本(R):"));

		// 所有字体大小对应的数值
		Integer[] sizeValue = { 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26,
				28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 10, 9,
				8, 7, 6, 5 };

		// 保存这List中
		SizeNum = Arrays.asList(sizeValue);
		SizeStr = Arrays.asList(fontArray);
	}

	public void DialogPane(Container pane) {
		// 字体
		Box fontBox = Box.createVerticalBox();
		fontBox.setBorder(BorderFactory.createTitledBorder("字体(F):"));
		fontBox.add(FontT);
		fontBox.add(new JScrollPane(listFont));

		// 字形
		Box shapesBox = Box.createVerticalBox();
		shapesBox.setBorder(BorderFactory.createTitledBorder("字形(Y):"));
		shapesBox.add(Shapes);
		shapesBox.add(new JScrollPane(listShapes));

		// 大小
		Box sizeBox = Box.createVerticalBox();
		sizeBox.setBorder(BorderFactory.createTitledBorder("大小(S):"));
		sizeBox.add(Size);
		sizeBox.add(new JScrollPane(listSize));

		// 按钮
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Yes);
		buttonBox.add(Box.createHorizontalStrut(5));
		buttonBox.add(No);

		// buttonBox+scripts
		Box groupBox = Box.createVerticalBox();
		groupBox.add(scripts);
		groupBox.add(Box.createVerticalStrut(10));
		groupBox.add(buttonBox);

		// 字体Box fontBox+shapeBox+sizeBox
		Box MFontBox = Box.createHorizontalBox();
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(fontBox);
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(shapesBox);
		MFontBox.add(Box.createHorizontalStrut(5));
		MFontBox.add(sizeBox);
		MFontBox.add(Box.createHorizontalStrut(5));

		// 示例Box
		Box demoBox = Box.createHorizontalBox();
		demoBox.add(Box.createHorizontalStrut(5));
		demoBox.add(demo);
		demoBox.add(Box.createHorizontalStrut(60));
		demoBox.add(groupBox);
		demoBox.add(Box.createHorizontalStrut(5));
		// demoBox.setSize(100, 100);

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.add(Box.createVerticalStrut(5));
		pane.add(MFontBox);
		pane.add(demoBox);
		pane.add(Box.createVerticalStrut(5));
	}

	public void addListener() {
		MF.newLine.addActionListener(this);
		MF.color.addActionListener(this);
		MF.font.addActionListener(this);

		listFont.addListSelectionListener(this);
		listShapes.addListSelectionListener(this);
		listSize.addListSelectionListener(this);
		Yes.addActionListener(this);
		No.addActionListener(this);

		scripts.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				switch ((String) e.getItem()) {
				case "中文":
					demoStr = "你好,记事本 ";
					break;
				case "英文":
					demoStr = "Hello,NotePad";
					break;
				case "数字":
					demoStr = "4096";
					break;
				}
				demo.setText(demoStr);
			}
		});

		Size.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SizeFlag = false;
				size = 18; // 定义初始大小
				setSize();
				font = new Font(FontT.getText(), style, size);
				demo.setFont(font);
			}
		});
	}

	public void setStyle() {
		Shapes.setText((String) listShapes.getSelectedValue());
		String name = Shapes.getText();
		switch (name) {
		case "常规":

			style = Font.PLAIN;
			break;
		case "粗体":

			style = Font.BOLD;
			break;
		case "倾斜":

			style = Font.ITALIC;
			break;
		case "粗体  倾斜":
			style = Font.BOLD + Font.ITALIC;
			break;
		}
	}

	public void setSize() {
		
		if (SizeFlag)
			Size.setText((String) listSize.getSelectedValue());
		String sizeN = Size.getText();
		try {
			size = Integer.valueOf(sizeN);
		} catch (NumberFormatException e1) {
			System.out.println("a");
			for (int i = 16; i < SizeStr.size(); i++) {
				if (sizeN.equals(SizeStr.get(i))) {
					size = SizeNum.get(i);
					break;
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		if (source == MF.newLine) {
			if (MF.newLine.isSelected()) {
				MF.getContentPane().remove(1);
				MF.state.setSelected(false);
				MF.state.setEnabled(false);
				text.setLineWrap(true);
			} else {
				MF.getContentPane().add(MF.ranks, BorderLayout.SOUTH);
				MF.state.setSelected(true);
				MF.state.setEnabled(true);
				text.setLineWrap(false);
			}
			MF.setVisible(true);
		}

		if (source == MF.font) {
			fontDialog.setVisible(true);
		}

		if (source == MF.color) {
			Color color = Color.black;
			color = JColorChooser.showDialog(MF, "颜色", Color.black);
			text.setForeground(color);
		}
		if (source == Yes) {
			text.setFont(font);
			fontDialog.setVisible(false);
		}
		if (source == No) {
			fontDialog.setVisible(false);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		size = 18;

		if (source == listFont) {
			FontT.setText((String) listFont.getSelectedValue());
		}
		if (source == listShapes) {
			setStyle();

		}
		if (source == listSize) {
			SizeFlag = true;
			setSize();
		}

		font = new Font(FontT.getText(), style, size);
		demo.setFont(font);
	}
}
