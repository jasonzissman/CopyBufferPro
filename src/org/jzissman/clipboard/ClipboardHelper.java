package org.jzissman.clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardHelper implements ClipboardOwner {
	
	private static final Clipboard SYSTEM_CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
	private final Logger logger = Logger.getLogger(ClipboardHelper.class.getName()); 
	
	public String getClipboardText() throws UnsupportedFlavorException, IOException {
		
		String data = "";
		
		Transferable contents = SYSTEM_CLIPBOARD.getContents(null);
		if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			data = (String) contents.getTransferData(DataFlavor.stringFlavor);
		}
		
		return data;
	}
	
	public void setupClipboardCopyHandler(ClipboardDataManager clipboardDataManager) {
		SYSTEM_CLIPBOARD.addFlavorListener(new ClipboardCopyListener(clipboardDataManager));
	}
	
	public void takeClipboardOwnership() {
		try {
			Transferable contents = SYSTEM_CLIPBOARD.getContents(null);
			SYSTEM_CLIPBOARD.setContents(contents, this);
		} catch (Exception e){
			// It's ok, we'll try again later
		}
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// We'll get it backs oon!
	}
}
