package org.jzissman.clipboard;

import java.util.ArrayList;
import java.util.List;

public class ClipboardDataManager {
	private final int MAX_COPY_BUFFER_SIZE = 10;
	private List<String> copyBuffer = new ArrayList<String>();

	public void addToCopyBuffer(String text){
		copyBuffer.add(0, text);
		while (copyBuffer.size() > MAX_COPY_BUFFER_SIZE){
			copyBuffer.remove(MAX_COPY_BUFFER_SIZE);
		}
	}
	
	public List<String> getAllCopiedTextEntries() {
		return copyBuffer;
	}

	public String getLastCopiedText() {
		String retVal = "";
		if (copyBuffer.size() > 0){
			retVal = copyBuffer.get(0);
		}
		return retVal;
	}
}
