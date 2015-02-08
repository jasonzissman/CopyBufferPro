package org.jzissman.app;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;

import org.jzissman.clipboard.ClipboardDataManager;
import org.jzissman.clipboard.ClipboardHelper;
import org.jzissman.gui.MenuGui;

public class Starter {
	private static final ClipboardHelper clipboardHelper = new ClipboardHelper();
	private static final ClipboardDataManager clipboardDataManager = new ClipboardDataManager();
	private static MenuGui menuGui;
	private static ControlShiftVListener hotKeyListener;
	private static boolean KEEP_APP_ALIVE = true;
	private static TrayIcon trayIcon = null;

	// TODO - GUI enhancements - hover highlight text, line delimiters on menu

	public static void main(String[] args) throws Exception {
		setUpCopyListener();
		setUpOnCtrlShiftVListener();
		createSystemTrayIcon();
		launchThreadToKeepAppRunning();
	}

	protected static void setUpCopyListener() {
		clipboardHelper.setupClipboardCopyHandler(clipboardDataManager);
	}

	private static void setUpOnCtrlShiftVListener() {
		KeyPressCallback callback = new KeyPressCallback() {

			@Override
			public void execute() {
				menuGui = new MenuGui();
				menuGui.show(clipboardDataManager.getAllCopiedTextEntries());
			}

		};
		hotKeyListener = new ControlShiftVListener(callback);
	}

	private static void createSystemTrayIcon() {

		if (!SystemTray.isSupported()) {
			return;
		}
		
		final PopupMenu popup = new PopupMenu();
		URL url = Starter.class.getResource("copyBufferPro.png");
		Image image = new ImageIcon(url).getImage();
		trayIcon = new TrayIcon(image, "Copy Buffer Pro");
		SystemTray tray = SystemTray.getSystemTray();

		MenuItem aboutItem = new MenuItem("About");
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				exitProgram();
			}
		});

		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);
		trayIcon.setImageAutoSize(true);
		
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}
	}
	
	private static void removeSystemTrayIcon() {
		
		if (!SystemTray.isSupported() || trayIcon == null) {
			return;
		}
		
		SystemTray.getSystemTray().remove(trayIcon);
	}	
	
	public static void exitProgram() { 
		KEEP_APP_ALIVE = false;
	}

	private static void launchThreadToKeepAppRunning() {
		new Thread(new Runnable() {
			public void run() {
				while (KEEP_APP_ALIVE) {
					try {
						Thread.sleep(50);
						clipboardHelper.takeClipboardOwnership();
					} catch (InterruptedException e) {
					}
				}
				removeSystemTrayIcon();
				System.exit(0);
			}
		}).start();
	}
}
