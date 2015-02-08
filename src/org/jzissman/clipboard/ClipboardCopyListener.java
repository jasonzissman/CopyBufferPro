package org.jzissman.clipboard;

import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClipboardCopyListener implements FlavorListener {

	private ClipboardHelper clipboardHelper = new ClipboardHelper();
	private final Logger logger = Logger.getLogger(ClipboardCopyListener.class.getName());
	private ClipboardDataManager clipboardDataManager = null;
	
	public ClipboardCopyListener(ClipboardDataManager clipboardDataManager){
		this.clipboardDataManager = clipboardDataManager;
	}
	
	public void onTextCopy(String copiedText) {
		clipboardDataManager.addToCopyBuffer(copiedText);
	}

	public void flavorsChanged(FlavorEvent e) {
		try {
			String text = clipboardHelper.getClipboardText();
			boolean textIsNotEmpty = text != null && !text.isEmpty() && !text.trim().isEmpty();
			boolean alreadyInCopyBuffer = clipboardDataManager.getAllCopiedTextEntries().contains(text);
			if (textIsNotEmpty && !alreadyInCopyBuffer) {
				onTextCopy(text);
			}
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Failed to get clipboard text.  Error: " + ex.getLocalizedMessage());
		}
	}
}
