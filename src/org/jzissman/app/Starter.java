package org.jzissman.app;

import org.jzissman.clipboard.ClipboardDataManager;
import org.jzissman.clipboard.ClipboardHelper;
import org.jzissman.gui.MenuGui;

public class Starter {
	private static final ClipboardHelper clipboardHelper = new ClipboardHelper();
	private static final ClipboardDataManager clipboardDataManager = new ClipboardDataManager();	
	private static MenuGui menuGui;
	private static ControlShiftVListener hotKeyListener;
	private static boolean KEEP_APP_ALIVE = true;

	public static void main(String[] args) throws Exception {
		setUpCopyListener();
		setUpOnCtrlShiftVListener();
		launchThreadToKeepAppRunning();
	}
	
	protected static void setUpCopyListener() {
		clipboardHelper.setupClipboardCopyHandler(clipboardDataManager);
	}
	private static void setUpOnCtrlShiftVListener() {
		KeyPressCallback callback = new KeyPressCallback(){

			@Override
			public void execute() {
				menuGui = new MenuGui();
				menuGui.show(clipboardDataManager.getAllCopiedTextEntries());
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
