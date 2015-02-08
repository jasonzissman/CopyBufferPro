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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jzissman.clipboard.ClipboardHelper;

public class MenuGui {

	private JFrame frame = null;
	private List<String> allCopiedTextEntries = null;
	
	public void closeFrame() {
		if (frame != null) {
			frame.dispose();
		}
	}
	
	public void show(List<String> allCopiedTextEntries) {
		this.allCopiedTextEntries = allCopiedTextEntries;
		JList list = createMenuList();
		frame = createMenuFrame(list);
		frame.toFront();
		frame.setVisible(true);
	}

	private JLabel createEmptyMenuLabel() {
		JLabel label = new JLabel("Your copy buffer is empty!", SwingConstants.CENTER);
		label.setBorder(new EmptyBorder(10, 10, 10, 10));
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		label.setFont(font);
		return label;
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
		frame.setSize(300, 30 + allCopiedTextEntries.size() * 25);
		
		if (allCopiedTextEntries.size() == 0){
			frame.add(createEmptyMenuLabel());
		} else {
			frame.add(list);
		}

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

	// TODO - GUI enhancements - hover highlight text, line delimiters on menu
	// TODO - GUI enhancements - message if nothing copied
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	private JList createMenuList() {
		
		String[] formattedSelectedTextArray = formatCopiedText(allCopiedTextEntries);
		
		JList list = new JList(formattedSelectedTextArray);
		Font font = new Font("Tahoma", Font.PLAIN, 14);
		list.setFont(font);
		list.setFixedCellHeight(25);
		list.setSelectedIndex(0);
		list.setBackground(new Color(250,250,250));
		list.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		MouseListener mouseListener = createClickListener(list);
		list.addMouseListener(mouseListener);
		
		KeyListener keyListener = createKeyListener(list);
		list.addKeyListener(keyListener);
		
		return list;
	}

	private KeyListener createKeyListener(final JList list) {
		KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
					handleMenuItemSelected(list.getSelectedIndex());
				} else if (keyCode == KeyEvent.VK_ESCAPE){
					closeFrame();
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
