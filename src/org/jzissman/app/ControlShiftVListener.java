package org.jzissman.app;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class ControlShiftVListener implements HotkeyListener {
	
	private static final int HOTKEY_INDEX = 1;
	private KeyPressCallback callback;
	
	public ControlShiftVListener(KeyPressCallback callback){
		this.callback = callback;
		JIntellitype.getInstance().registerHotKey(HOTKEY_INDEX, JIntellitype.MOD_CONTROL + JIntellitype.MOD_SHIFT, (int)'V');
		JIntellitype.getInstance().addHotKeyListener(this);
	}

	@Override
	public void onHotKey(int hotkeyIndex) {
		if (hotkeyIndex == HOTKEY_INDEX){
			callback.execute();
		}
	}
}
