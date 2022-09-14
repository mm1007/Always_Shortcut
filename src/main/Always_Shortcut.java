package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

public class Always_Shortcut {

	public static File Shortcut_file = new File(System.getProperty("user.home") + "/Desktop/ショートカット");
	public static ArrayList<ShortcutData> Shortcut_List = new ArrayList<ShortcutData>();

	static JFrame jf = new JFrame();
	static Canvas canvas = new Canvas();

	public static void main(String[] args) {
		reload_shortcut(Shortcut_file);
		set_frame(jf);
		canvas.tm.start();
	}

	public static void set_frame(JFrame jf) {
		jf.setBounds(0, 0, 720, 720);
		jf.setVisible(true);
		jf.add(canvas);
	}

	public static void reload_shortcut(File shortcut_file) {
		JFileChooser JFC = new JFileChooser();
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				// TODO 自動生成されたメソッド・スタブ
				if (name.indexOf(".lnk") != -1)
					return true;
				return false;
			}

		};
		File[] list = shortcut_file.listFiles(filter);

		Shortcut_List.clear();
		for (File select : list) {
			ImageIcon icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(select, 64, 64);
			if (UIManager.getIcon("FileView.fileIcon") != null) {
				System.out.println("しね");
				icon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(select);
			}
			Shortcut_List.add(new ShortcutData(select, icon.getImage()));
			System.out.println(select);
		}
	}

}

class ShortcutData {

	File ShortcutFile;
	Image img;

	public ShortcutData(File ShortcutFile, Image img) {
		this.ShortcutFile = ShortcutFile;
		this.img = img;
	}

}

class Canvas extends JPanel implements ActionListener {

	Image offImage;
	Graphics offGraphics;
	Timer tm = new Timer(10, this);

	public void paintComponent(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		offImage = createImage(Always_Shortcut.jf.getWidth(), Always_Shortcut.jf.getHeight());
		offGraphics = offImage.getGraphics();

		int i = 0;
		int j = 0;
		for (int t = 0, k = Always_Shortcut.Shortcut_List.size(); k > t; t++) {
			//System.out.println(t);
			if (i / 7 == 1) {
				i = 0;
				j++;
			} else {
				i++;
			}
			offGraphics.drawImage(Always_Shortcut.Shortcut_List.get(t).img, i * 90 + 5, j * 90 + 5, 64, 64, null);
		}

		g.drawImage(offImage, 0, 0, null);
	}

	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		repaint();
	}
}