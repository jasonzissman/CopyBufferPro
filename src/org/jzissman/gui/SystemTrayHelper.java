package org.jzissman.gui;

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

import org.jzissman.app.CopyBufferPro;

public class SystemTrayHelper {
	
	private TrayIcon trayIcon = null;	
	
	public void setupSystemTrayIcon() {

		if (!SystemTray.isSupported()) {
			return;
		}

		PopupMenu popup = createPopupMenu();
		trayIcon = createTrayIcon(popup);
		
		try {
			SystemTray tray = SystemTray.getSystemTray();
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}
	}

	protected TrayIcon createTrayIcon(PopupMenu popup) {
		URL url = CopyBufferPro.class.getResource("copyBufferPro.png");
		Image image = new ImageIcon(url).getImage();
		TrayIcon tempTrayIcon = new TrayIcon(image, "Copy Buffer Pro");
		tempTrayIcon.setPopupMenu(popup);
		tempTrayIcon.setImageAutoSize(true);
		return tempTrayIcon;
	}

	protected PopupMenu createPopupMenu() {
		MenuItem exitItem = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CopyBufferPro.exitProgram();
			}
		});

		PopupMenu popup = new PopupMenu();
		popup.add(exitItem);
		return popup;
	}
	
	public void removeSystemTrayIcon() {
		
		if (!SystemTray.isSupported() || trayIcon == null) {
			return;
		}
		
		SystemTray.getSystemTray().remove(trayIcon);
	}	
}
