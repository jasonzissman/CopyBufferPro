package org.jzissman.gui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.border.EmptyBorder;

import org.jzissman.clipboard.ClipboardHelper;

public class MenuGui {

	private static final Logger logger = Logger.getLogger(MenuGui.class.getName());
	private List<String> allCopiedTextEntries;
	private JFrame frame = null;
	
	public MenuGui(List<String> allCopiedTextEntries) {
		this.allCopiedTextEntries = allCopiedTextEntries;
	}

	public void closeFrame() {
		if (frame != null) {
			frame.dispose();
		}
	}
	
	public void show() {
		String[] selectedTextArray = formatCopiedText(allCopiedTextEntries);
		JList list = createMenuList(selectedTextArray);
		frame = createMenuFrame(list);
		frame.toFront();
		frame.setVisible(true);
	}

	private void handleMenuItemSelected(int selectedIndex) {
		closeFrame();
		String selectedItem = allCopiedTextEntries.get(selectedIndex);
    	new ClipboardHelper().setClipcboardText(selectedItem);
    	try {
			Robot r = new Robot();
		    r.keyPress(KeyEvent.VK_CONTROL);
		    r.keyPress(KeyEvent.VK_V);
		    r.keyRelease(KeyEvent.VK_CONTROL);
		    r.keyRelease(KeyEvent.VK_V);
		} catch (AWTException e1) {
		}
	}

	private JFrame createMenuFrame(JList list) {
		frame = new JFrame("Made by Jason Zissman");
		frame.setUndecorated(true);
		frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180,180,180)));
		frame.setForeground(new Color(70,70,70));
//		frame.setBackground(new Color(225,225,225,200));
		frame.add(list);
		frame.setSize(300, 30 + allCopiedTextEntries.size() * 25);
		int x = MouseInfo.getPointerInfo().getLocation().x;
		int y = MouseInfo.getPointerInfo().getLocation().y;
		frame.setLocation(x, y);
		
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
	        	closeFrame();
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		return frame;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private JList createMenuList(String[] selectedTextArray) {
		
		JList list = new JList(selectedTextArray);
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		list.setFont(font);
		list.setFixedCellHeight(25);
		list.setSelectedIndex(0);
		list.setBackground(new Color(250,250,250));
		list.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		MouseListener mouseListener = createClickListener(list);
		list.addMouseListener(mouseListener);
		
		KeyListener keyListener = createEnterKeyListener(list);
		list.addKeyListener(keyListener);
		
		return list;
	}

	private KeyListener createEnterKeyListener(final JList list) {
		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					handleMenuItemSelected(list.getSelectedIndex());
				}
			}
		};
		return keyListener;
	}
	
	private MouseListener createClickListener(final JList list) {
		MouseListener mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		    	handleMenuItemSelected(list.getSelectedIndex());
	        }
		};
		return mouseListener;
	}

	private String[] formatCopiedText(List<String> originalEntries) {
		List<String> formattedTextEntries = new ArrayList<String>();
		for (String text : originalEntries) {
			if (text.length() > 30){
				text = text.substring(0,40) + "...";
			}
			formattedTextEntries.add(text);
		}
		return formattedTextEntries.toArray(new String[formattedTextEntries.size()]);
	}

}
