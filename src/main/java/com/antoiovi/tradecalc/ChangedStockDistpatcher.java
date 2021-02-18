package com.antoiovi.tradecalc;

import java.util.ArrayList;
import java.util.List;

public class ChangedStockDistpatcher {
	
	List<ChangStockListener> stocklistener=new ArrayList<ChangStockListener>();


	/**
	 * Execute recalc() to each class taht implements ChangeStockListener
	 */
	public void dispatchRecalc() {
		for (ChangStockListener s: stocklistener) {
			s.recalc();
		}
	}
	public void dispatchStockChanged(Stock stock) {
		for (ChangStockListener s: stocklistener) {
			s.changedStock(stock);
		}
	}
	
	public void dispatchStockModified(Stock stock) {
		for (ChangStockListener s: stocklistener) {
			s.modifiedStock(stock);
		}
	}
	public void dispatchIncrementSpinnerChanged(double spinner_increment) {
		for (ChangStockListener s: stocklistener) {
			s.incrementStep(spinner_increment);
		}
	}
	
	/**
	 * Add a class that implements ChangeStockListener to the list stocklistener
	 * @param arg0
	 * @return
	 */
	public boolean add(ChangStockListener arg0) {
		return stocklistener.add(arg0);
	}

}
