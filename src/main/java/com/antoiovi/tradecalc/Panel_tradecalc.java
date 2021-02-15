package com.antoiovi.tradecalc;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Panel_tradecalc extends JPanel {

	private JPanel contentPane;
	private Stock currentsock;
	List<Stock> stocks;
	private ChangedStockDistpatcher dispatcher;

	private ListIterator<Stock> stocksIterator;
	
	FileManager fileManager=new FileManager();
	
	static final int NSTOCKS=5;
	private int currentindex = 0;
	private JLabel lblIndex;
	
	/**
	 * Create the panel.
	 */
	public Panel_tradecalc() {
		JPanel panel =this;
		
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		// panel_e.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		this.craeteStocks();
		
		//Stock stock=stocksIterator.next();
		// log("Primo stock: "+stock.getCode());
		dispatcher = new ChangedStockDistpatcher();
		currentsock=stocksIterator.next();
		
		Panel_stock panel_stock = new Panel_stock(dispatcher,currentsock);
		
		panel.add(panel_stock);
		
		Panel_profit panel_profit = new Panel_profit(dispatcher,currentsock);
		panel.add(panel_profit);
		panel_profit.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		Panel_calc panel_3 = new Panel_calc();
		panel.add(panel_3);
		
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		JButton btnLeft = new JButton("<<");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log("\n prev");
				//currentsock.log();

				try {

					if (stocksIterator.hasPrevious()) {
						if (currentindex == stocksIterator.previousIndex())
							stocksIterator.previous();
						log(String.format("\t has previous previousindex= %d", stocksIterator.previousIndex()));
						currentindex = stocksIterator.previousIndex();
						currentsock = stocksIterator.previous();
						//currentsock.log();
						dispatcher.dispatchStockChanged(currentsock);

						log(String.format("\t\t previousindex= %d", stocksIterator.previousIndex()));
						log(String.format("\t\t nextindex= %d", stocksIterator.nextIndex()));
						lblIndex.setText(String.valueOf(currentindex+1));

						// stocksIterator.next();

					} else {
						// stocksIterator.next();
					}
				} catch (NoSuchElementException e) {
					stocksIterator.next();
				}
			}
		});
		
		panel_2.add(btnLeft);
		JButton btnRight = new JButton(">>");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log("\n next");
				//log(currentsock);
				try {
					if(stocksIterator.hasNext()) {
					if(currentindex==stocksIterator.nextIndex())
							stocksIterator.next();
						log(String.format("\t has next nextindex= %d",stocksIterator.nextIndex()));
						currentindex=stocksIterator.nextIndex();
						currentsock=stocksIterator.next();
						
						dispatcher.dispatchStockChanged(currentsock);
						
						//log(currentsock);
						log(String.format("\t\t nextindex= %d",stocksIterator.nextIndex()));
						log(String.format("\t\t previousindex= %d",stocksIterator.previousIndex()));
						lblIndex.setText(String.valueOf(currentindex+1));

					}else {
						
					}
				} catch (NoSuchElementException e) {
					stocksIterator.previous();
				}
			}
			

			});
		
		lblIndex = new JLabel("1");

		panel_2.add(lblIndex);
		panel_2.add(btnRight);
		
	}

	
	public ChangedStockDistpatcher getDispatcher() {
		return dispatcher;
	}


	void craeteStocks() {
		FileManager fm=new FileManager();
		stocks =fm.OpenFile();
		
		if (stocks==null) {
			stocks=new ArrayList<Stock>();
			for(int i=0;i<NSTOCKS;i++) {
				Stock stock=new Stock();
				stock.setCode("A-"+String.valueOf(i));
				log("[Createstocks]"+stock.getCapital());
				stocks.add(stock);
				stocksIterator = stocks.listIterator();
			}
			}else {
				stocks=new ArrayList<Stock>(stocks);
			}
		
			stocksIterator = stocks.listIterator();
		
	}
	
	public void saveFile() {
		FileManager fm=new FileManager();
		fm.SaveFile(stocks);
	}
	public void openFile() {
		FileManager fm=new FileManager();
		stocks =fm.OpenFile();
		stocksIterator = stocks.listIterator();
		dispatcher.dispatchStockChanged(stocksIterator.next());
	}

	void log(String s) {
	//System.out.println("[Tradecalc]"+s);

}
	void log(Stock s) {
		//System.out.print("[Tradecalc]");
		//s.log();

	}
}
