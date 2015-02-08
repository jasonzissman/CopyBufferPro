package org.jzissman.app;

import org.jzissman.clipboard.ClipboardDataManager;
import org.jzissman.clipboard.ClipboardHelper;
import org.jzissman.gui.MenuGui;
import org.jzissman.gui.SystemTrayHelper;

public class CopyBufferPro {
	private static final ClipboardHelper clipboardHelper = new ClipboardHelper();
	private static final ClipboardDataManager clipboardDataManager = new ClipboardDataManager();
	private static SystemTrayHelper systemTrayHelper = new SystemTrayHelper();
	private static MenuGui menuGui;
	private static ControlShiftVListener hotKeyListener;
	private static boolean KEEP_APP_RUNNING = true;
	
	public static void main(String[] args) throws Exception {
		clipboardHelper.setupClipboardCopyHandler(clipboardDataManager);
		setUpOnCtrlShiftVListener();
		systemTrayHelper.setupSystemTrayIcon();
		launchThreadToKeepAppRunning();
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

	public static void exitProgram() { 
		KEEP_APP_RUNNING = false;
	}

	private static void launchThreadToKeepAppRunning() {
		new Thread(new Runnable() {
			public void run() {
				while (KEEP_APP_RUNNING) {
					try {
						Thread.sleep(50);
						clipboardHelper.takeClipboardOwnership();
					} catch (InterruptedException e) {
					}
				}
				systemTrayHelper.removeSystemTrayIcon();
				System.exit(0);
			}
		}).start();
	}
}
