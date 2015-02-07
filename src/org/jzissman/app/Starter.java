package org.jzissman.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jzissman.clipboard.ClipboardDataManager;
import org.jzissman.clipboard.ClipboardHelper;
import org.jzissman.gui.MenuGui;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class Starter {

	private static final Logger logger = Logger.getLogger(Starter.class.getName());
	private static final ClipboardHelper clipboardHelper = new ClipboardHelper();
	private static final ClipboardDataManager clipboardDataManager = new ClipboardDataManager();	
	private static MenuGui menu;
	private static ControlShiftVListener hotKeyListener;
	private static boolean KEEP_APP_ALIVE = true;
	
	public static void main(String[] args) throws Exception {
		logger.log(Level.INFO, "Starting CopyRock app");
		setUpCopyListener();
		setUpOnPasteListener();
		launchThreadToKeepAppRunning();
	}

	protected static void setUpCopyListener() {
		clipboardHelper.setupClipboardCopyHandler(clipboardDataManager);
	}

	private static void setUpOnPasteListener() {
		KeyPressCallback callback = new KeyPressCallback(){

			@Override
			public void execute() {
				menu = new MenuGui(clipboardDataManager.getAllCopiedTextEntries());
			}
			
		};
		hotKeyListener = new ControlShiftVListener(callback);
	}

	private static void launchThreadToKeepAppRunning() {
		new Thread(new Runnable(){
			public void run() {
				while(KEEP_APP_ALIVE){
					try {
						Thread.sleep(50);
						clipboardHelper.takeClipboardOwnership();
					} catch (InterruptedException e) {
					}
				}
			}
			
		}).start();
	}
}
