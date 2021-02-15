package com.antoiovi.tradecalc;

import java.io.Serializable;

public class Stock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name="Name";
	String code="Code";
	double price;
	int quantity;
	double totalprice=1.0;
	double buycost=0;
	double sellcost=0;
	double capital=10000.0f;
	double takeprice;
	double takeperc;
	double profit;
	double taxOnprofitperc=0;
	double taxOnprofit=0;
	double profitNet=0;
	double lostprice;
	double lostperc;
	double lost=0;
	double lostNet=0;


	public Stock() {
		super();
		price=1.0;
		quantity=1;
		takeprice=1.05;
		lostprice=0.98;
		capital=10000.0f;
		calc();
	}


	
	
	public void calc() {
		totalprice=price*quantity;
		profit=quantity*takeprice;
		takeperc=100*(takeprice-price)/price;
		taxOnprofit=((profit-totalprice)>0 ? taxOnprofitperc*(profit-totalprice)/100:0);
		profitNet= profit-totalprice-buycost-sellcost-taxOnprofit;
		lostperc=100*(price-lostprice)/price;
		lost=lostprice*quantity;
		lostNet=lost-totalprice-buycost-sellcost;
	}
	
	public double maxQuantity() {
		return (capital-buycost-sellcost)/price;
	}

	void log() {
		//System.out.println(String.format("[Stock]name[%s]code[%s]",this.name,this.code));
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public double getTotalprice() {
		return totalprice;
	}


	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}


	public double getBuycost() {
		return buycost;
	}


	public void setBuycost(double buycost) {
		this.buycost = buycost;
	}


	public double getSellcost() {
		return sellcost;
	}


	public void setSellcost(double sellcost) {
		this.sellcost = sellcost;
	}


	public double getTakeprice() {
		return takeprice;
	}


	public void setTakeprice(double takeprice) {
		this.takeprice = takeprice;
	}


	public double getTakeperc() {
		return takeperc;
	}


	public void setTakeperc(double takeperc) {
		this.takeperc = takeperc;
	}


	public double getProfit() {
		return profit;
	}


	public void setProfit(double profit) {
		this.profit = profit;
	}


	public double getTaxOnprofitperc() {
		return taxOnprofitperc;
	}


	public void setTaxOnprofitperc(double taxOnprofitperc) {
		this.taxOnprofitperc = taxOnprofitperc;
	}


	public double getTaxOnprofit() {
		return taxOnprofit;
	}


	public void setTaxOnprofit(double taxOnprofit) {
		this.taxOnprofit = taxOnprofit;
	}


	public double getProfitNet() {
		return profitNet;
	}


	public void setProfitNet(double profitNet) {
		this.profitNet = profitNet;
	}


	public double getLostprice() {
		return lostprice;
	}


	public void setLostprice(double lostprice) {
		this.lostprice = lostprice;
	}


	public double getLostperc() {
		return lostperc;
	}


	public void setLostperc(double lostperc) {
		this.lostperc = lostperc;
	}


	public double getLost() {
		return lost;
	}


	public void setLost(double lost) {
		this.lost = lost;
	}


	public double getLostNet() {
		return lostNet;
	}


	public void setLostNet(double lostNet) {
		this.lostNet = lostNet;
	}




	public double getCapital() {
		return capital;
	}




	public void setCapital(double capital) {
		this.capital = capital;
	}
	
	
	
}
