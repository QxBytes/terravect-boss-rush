package com.qxbytes.log;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.qxbytes.Screen;
/**
 * 
 * @author QxBytes
 *
 */
public class GLog extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea pane = new JTextArea();
	private JScrollPane scroll = new JScrollPane();
	public GLog(boolean showing) {
		super("Logger");
		this.setSize(300, 200);
		//this.setVisible(showing);
		this.setLayout(new GridLayout(1,0));
		
		scroll.setViewportView(pane);
		
		this.add(scroll);
		
		this.addMessage("Initialized");
	}
	public void addMessage(String x) {
		pane.setText(new Date().toString() + ":" + x + "\n" + pane.getText());
	}
	public void addMessage(String x, int delay) {
		if (Screen.runtime % delay == 0)
		pane.setText(new Date().toString() + ":" + x + "\n" + pane.getText());
	}
	
}
