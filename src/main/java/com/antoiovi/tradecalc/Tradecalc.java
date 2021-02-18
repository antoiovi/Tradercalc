package com.antoiovi.tradecalc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.awt.Color;


import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import com.antoiovi.tradecalc.*;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class Tradecalc extends JFrame implements ChangStockListener {

	private JPanel contentPane;
	private Stock currentsock;
	List<Stock> stocks;

	FileManager fileManager=new FileManager();
	private Panel_tradecalc panel_tradecalc;
	private JButton btnReloadFile;
	private JButton btnSaveFile;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tradecalc frame = new Tradecalc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("serial")
	public Tradecalc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);//(x,y,w,h)
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_tradecalc = new Panel_tradecalc();
		panel_tradecalc.getDispatcher().add(this);
		contentPane.add(panel_tradecalc,BorderLayout.CENTER);

		
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		
		btnSaveFile = new JButton("Save file");
		btnSaveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_tradecalc.saveFile();
				btnSaveFile.setEnabled(false);
			}
		});
		btnSaveFile.setEnabled(false);
		panel_1.add(btnSaveFile);
		
		btnReloadFile = new JButton("Reload file");
		btnReloadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnSaveFile.isEnabled()) {
		        int input = JOptionPane.showConfirmDialog(null, 
		                "Reloading file changed will be lost ! Do you want to proceed?", "Yes to reload and ignore changes..."
		                ,JOptionPane.YES_NO_OPTION);
		        if(input==0) {
					panel_tradecalc.openFile();
		        	btnSaveFile.setEnabled(false);
		        	}
				}else {
		    // 0=yes, 1=no, 2=cancel
				panel_tradecalc.openFile();
				}
			}
		});
		panel_1.add(btnReloadFile);
		
	}


	@Override
	public void modifiedStock(Stock stock) {
		btnSaveFile.setEnabled(true);		
	}
	@Override
	public void recalc() {
		// Do nothing
		
	}

	@Override
	public void changedStock(Stock stock) {
		// Do nothing
	}
	void log(String s) {
		//System.out.println("[Tradecalc]"+s);

	}
		void log(Stock s) {
			//System.out.print("[Tradecalc]");
			//s.log();

		}

		@Override
		public void incrementStep(double incerment) {
			// TODO Auto-generated method stub
			
		}
}
