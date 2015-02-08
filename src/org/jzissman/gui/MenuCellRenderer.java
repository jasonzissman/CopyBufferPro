package org.jzissman.gui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class MenuCellRenderer implements ListCellRenderer {

	// TODO - create horizontal delimiters in between each item
	// TODO - highlight any items that is being hovered
	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		final JLabel renderedLabel = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//		renderedLabel.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent e) {
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent e) {
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent e) {
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent e) {
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent e) {
//			}
//		});
//		
		return renderedLabel;
	}
}
