package notePad;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	JTextArea text;
	JMenuBar menubar;

	// 文件(F)
	JMenu file;
	JMenuItem newFile;
	JMenuItem openFile;
	JMenuItem saveFile;
	JMenuItem saveAsFile;
	JMenuItem pageSet;
	JMenuItem print;
	JMenuItem exit;

	// 编辑(E)
	JMenu edit;
	JMenuItem cancel;
	JMenuItem cut;
	JMenuItem copy;
	JMenuItem paste;
	JMenuItem del;
	JMenuItem find;
	JMenuItem findnext;
	JMenuItem replace;
	JMenuItem turn;
	JMenuItem select;
	JMenuItem date;

	// 格式(O)
	JMenu from;
	JCheckBoxMenuItem newLine; // 带复选框的菜单项
	JMenuItem font;
	JMenuItem color;

	// 查看(V) & 帮助(H)
	JMenu view;
	JCheckBoxMenuItem state;

	JMenu help;
	JMenuItem vhelp;
	JMenuItem about;

	// 实时行列
	int col;
	int row;
	JLabel ranks;

	JScrollPane ScrollPane;

	// 弹出菜单
	JPopupMenu popupMenu;
	JMenuItem popCancel;
	JMenuItem popCut;
	JMenuItem popCopy;
	JMenuItem popPaste;
	JMenuItem popDel;
	JMenuItem popSelect;

	
	
	public MainFrame() {
		setTitle("记事本");
		setSize(680, 400);
		setLocation(370, 150);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 图标
		Image image = Toolkit.getDefaultToolkit().getImage(
				this.getClass().getResource("notepad.png"));
		setIconImage(image);

		buildPane();
		setVisible(true);
		Operation();

	}

	
	public void buildPane() {
		ScrollPane = new JScrollPane();
		ScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		ScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		text = new JTextArea();

		menubar = new JMenuBar();
		ranks = new JLabel("第 " + row + " 行 , 第 " + col + " 列            ",
				JLabel.RIGHT);
		ScrollPane.setViewportView(text);

		this.getContentPane().add(ScrollPane, BorderLayout.CENTER);
		this.getContentPane().add(ranks, BorderLayout.SOUTH);
		setJMenuBar(menubar);

		// 菜单
		FileMenu();
		EditMenu();
		fromMenu();
		view_helpMenu();
		PopMenu();
	}

	public void Operation() {
		new FileOperate(this);
		new EditOperate(this);
		new FormatOperate(this);
		new ViewHelpOperate(this);
	}

	public void FileMenu() {
		file = new JMenu("文件(F)");
		newFile = new JMenuItem("新建(N)");
		openFile = new JMenuItem("打开(O)...");
		saveFile = new JMenuItem("保存(S)");
		saveAsFile = new JMenuItem("另存为(A)...");
		pageSet = new JMenuItem("页面设置(U)...");
		print = new JMenuItem("打印(P)...");
		exit = new JMenuItem("退出(E)");

		// 加入文件菜单
		file.add(newFile);
		file.add(openFile);
		file.add(openFile);
		file.add(saveFile);
		file.add(saveAsFile);
		file.addSeparator();
		file.add(pageSet);
		file.add(print);
		file.addSeparator();
		file.add(exit);

		// 设置菜单快捷键 ALt+F
		file.setMnemonic('F');

		// 设置菜单项的组合快捷键 用"Ctrl"修饰'N'等键 Ctrl+N
		newFile.setAccelerator(KeyStroke
				.getKeyStroke('N', InputEvent.CTRL_MASK));
		openFile.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_MASK));
		saveFile.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_MASK));
		print.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK));

		menubar.add(file);

	}

	public void EditMenu() {
		edit = new JMenu("编辑(E)");
		cancel = new JMenuItem("撤销(U)");
		cut = new JMenuItem("剪切(T)");
		copy = new JMenuItem("复制(C)");
		paste = new JMenuItem("粘贴(P)");
		del = new JMenuItem("删除(L)");
		find = new JMenuItem("查找(F)...");
		findnext = new JMenuItem("查找下一个(N)...");
		replace = new JMenuItem("替换(R)...");
		turn = new JMenuItem("转到(G)...");
		select = new JMenuItem("全选(A");
		date = new JMenuItem("时间/日期(D)");

		// 加入编辑菜单
		edit.add(cancel);
		edit.addSeparator();
		edit.add(cut);
		edit.add(copy);
		edit.add(paste);
		edit.add(del);
		edit.addSeparator();
		edit.add(find);
		edit.add(findnext);
		edit.add(replace);
		edit.add(turn);
		edit.addSeparator();
		edit.add(select);
		edit.add(date);

		// 设置快捷键
		edit.setMnemonic('E');
		cancel.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
		del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
				InputEvent.CTRL_MASK));
		find.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK));
		findnext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
				InputEvent.CTRL_MASK));
		replace.setAccelerator(KeyStroke
				.getKeyStroke('H', InputEvent.CTRL_MASK));
		turn.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.CTRL_MASK));
		select.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_MASK));
		date.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				InputEvent.CTRL_MASK));

		menubar.add(edit);
	}

	public void fromMenu() {
		from = new JMenu("格式(O)");
		newLine = new JCheckBoxMenuItem("自动换行(W)");
		font = new JMenuItem("字体(F)");
		color = new JMenuItem("颜色(C)");

		from.setMnemonic('O');
		from.add(newLine);
		from.add(font);
		from.add(color);

		menubar.add(from);
	}

	public void view_helpMenu() {
		// 查看菜单
		view = new JMenu("查看(V)");
		state = new JCheckBoxMenuItem("状态栏(S)");
		state.setSelected(true);

		view.add(state);
		view.setMnemonic('V');
		menubar.add(view);

		// 帮助菜单
		help = new JMenu("帮助(H)");
		vhelp = new JMenuItem("查看帮助(H)");
		about = new JMenuItem("关于记事本(A)");

		help.add(vhelp);
		help.add(about);
		help.setMnemonic('H');
		menubar.add(help);
	}

	// 弹出菜单
	public void PopMenu() {
		popupMenu = new JPopupMenu();
		popCancel = new JMenuItem("撤销(U)");
		popCut = new JMenuItem("剪切(T)");
		popCopy = new JMenuItem("复制(C)");
		popPaste = new JMenuItem("粘贴(P)");
		popDel = new JMenuItem("删除(D)");
		popSelect = new JMenuItem("全选(A)");

		popupMenu.add(popCancel);
		popupMenu.addSeparator();
		popupMenu.add(popCut);
		popupMenu.add(popCopy);
		popupMenu.add(popPaste);
		popupMenu.add(popDel);
		popupMenu.addSeparator();
		popupMenu.add(popSelect);

		text.setComponentPopupMenu(popupMenu); // 设popupMenu为文本区的弹出菜单
	}
}
