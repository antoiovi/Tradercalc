package com.antoiovi.tradecalc;

public interface ChangStockListener {

	public void recalc();
	// Changed cuurent stock
	public void changedStock(Stock stock);
	// Modified current stock
	public void modifiedStock(Stock stock);
	
	public void incrementStep(double incerment);
}
