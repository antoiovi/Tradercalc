package com.antoiovi.tradecalc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
;

public class Panel_profit extends JPanel implements ChangeListener,ChangStockListener{
	static final int TAKEPRICE=0;
	static final int TAKEPERC=1;
	static final int PROFITSELL=2;
	static final int TAXES=3;
	static final int NETPROFIT=4;
	static final int LOSSPRICE=5;
	static final int LOSSPERC=6;
	static final int LOSSSELLED=7;
	static final int NETLOSS=8;
	static final Color TEXTCOLOR=Color.YELLOW;

	JComponent[] component=new JComponent[12];
	JLabel[] labels=new JLabel[12];
	String[] etich= {"Price take :","Take % :","Profit sell :","Taxes % on profit :", "Net profit:",
			"Price loss :","Loss % :","Loss sell:","Net loss :"};
	Stock stock;
	ChangedStockDistpatcher dispatcher;
	/**
	 * Create the panel.
	 */
	public Panel_profit(ChangedStockDistpatcher dispatcher ,Stock stock) {
		this.dispatcher=dispatcher;
		this.dispatcher.add(this);
		log("[Costructor]"+stock.getCode());
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setAlignmentX(Component.RIGHT_ALIGNMENT);
		setAlignmentY(Component.TOP_ALIGNMENT);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		JPanel panelA=new JPanel();
		JPanel panelB=new JPanel();		
		FlowLayout flowLayout = (FlowLayout) panelA.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		FlowLayout flowLayoutb = (FlowLayout) panelB.getLayout();
		flowLayoutb.setAlignment(FlowLayout.LEFT);
		
		JSpinner spinner_take = getSpinner(0.0001,1000.0, 0.0001,4);
		spinner_take.addChangeListener(this);
		component[TAKEPRICE]=spinner_take;
		
		JTextField textField_takeperc = new JTextField();
		textField_takeperc.setEditable(false);
		textField_takeperc.setColumns(10);
		textField_takeperc.setBackground(TEXTCOLOR);
		component[TAKEPERC]=textField_takeperc;
		
		JTextField textField_profit = new JTextField();
		textField_profit.setEditable(false);
		textField_profit.setColumns(10);
		textField_profit.setBackground(TEXTCOLOR);
		component[PROFITSELL]=textField_profit;
		
		JSpinner spinner_taxes = getSpinner(0.0,50.0, 0.5,1);
		spinner_taxes.addChangeListener(this);
		component[TAXES]=spinner_taxes;
		JTextField textField_netprofit = new JTextField();
		textField_netprofit.setEditable(false);
		textField_netprofit.setColumns(10);
		textField_netprofit.setBackground(TEXTCOLOR);
		component[NETPROFIT]=textField_netprofit;
		
		JSpinner spinner_loss = getSpinner(0.001,1000.0, 0.0001,4);
		spinner_loss.addChangeListener(this);
		component[LOSSPRICE]=spinner_loss;
		JTextField textField_lossperc = new JTextField();
		textField_lossperc.setEditable(false);
		textField_lossperc.setColumns(10);
		textField_lossperc.setBackground(TEXTCOLOR);
		component[LOSSPERC]=textField_lossperc;
		JTextField textField_loss_selled = new JTextField();
		textField_loss_selled.setEditable(false);
		textField_loss_selled.setColumns(10);
		textField_loss_selled.setBackground(TEXTCOLOR);
		component[LOSSSELLED]=textField_loss_selled;
		JTextField textField_netloss = new JTextField();
		textField_netloss.setEditable(false);
		textField_netloss.setColumns(10);
		textField_netloss.setBackground(TEXTCOLOR);
		component[NETLOSS]=textField_netloss;
		
		for(int i=0;i<=8;i++) {
			JLabel lblNewLabel = new JLabel(etich[i]);
			labels[i]=lblNewLabel;
			JPanel panel;
			panel= (i<=4 ? panelA : panelB);
			panel.add(lblNewLabel);
			panel.add(component[i]);

		}
		add(panelA);
		add(panelB);
		this.setStock(stock);

	}
	
	
	
	
	public Stock getStock() {
		return stock;
	}



private boolean changed=false;
	public void setStock(Stock stock) {
		this.stock = stock;
		log("srtStoc :"+stock.getCode());
		stock.calc();
		
		changed=true;
		((JSpinner)component[TAKEPRICE]).setValue(stock.takeprice);

		changed=true;
		((JSpinner)component[LOSSPRICE]).setValue(stock.lostprice);

		changed=true;
		((JSpinner)component[TAXES]).setValue(stock.taxOnprofitperc);

		((JTextField)component[TAKEPERC]).setText(String.format("%,.2f %%", stock.takeperc));
		((JTextField)component[PROFITSELL]).setText(String.format("%,.4f", stock.profit));
		((JTextField)component[NETPROFIT]).setText(String.format("%,.4f", stock.profitNet));
		((JTextField)component[LOSSPERC]).setText(String.format("%,.2f %%", stock.lostperc));
		((JTextField)component[LOSSSELLED]).setText(String.format("%,.4f", stock.lost));
		((JTextField)component[NETLOSS]).setText(String.format("%,.4f", stock.lostNet));
		changed=false;

	}


	@Override
	public void changedStock(Stock stock) {
		this.setStock(stock);
		log("Panelprofit :CHANGED STOCK ");
		
	}

	@Override
	public void modifiedStock(Stock stock) {
		// TODO Auto-generated method stub
		
	}


	public void recalc() {
		stock.calc();
		
		((JTextField)component[TAKEPERC]).setText(String.format("%,.2f %%", stock.takeperc));
		((JTextField)component[PROFITSELL]).setText(String.format("%,.4f", stock.profit));
		((JTextField)component[NETPROFIT]).setText(String.format("%,.4f", stock.profitNet));
		((JTextField)component[LOSSPERC]).setText(String.format("%,.2f %%", stock.lostperc));
		((JTextField)component[LOSSSELLED]).setText(String.format("%,.4f", stock.lost));
		((JTextField)component[NETLOSS]).setText(String.format("%,.4f", stock.lostNet));

		//System.out.println(String.format(
	    //Locale.FRANCE, "%,.2f", d));     
		
	}
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource()==component[TAKEPRICE]) {
			stock.setTakeprice((Double)( (JSpinner) arg0.getSource()).getValue());
			
		}else if(arg0.getSource()==component[TAXES]) {
			stock.setTaxOnprofitperc((Double)( (JSpinner) arg0.getSource()).getValue());
			
		}		
		else if(arg0.getSource()==component[LOSSPRICE]) {
			stock.setLostprice((Double)( (JSpinner) arg0.getSource()).getValue());
			
		}	
		dispatcher.dispatchRecalc();
		//dispatcher.dispatchStockModified(stock);
		if(changed!=true)
			dispatcher.dispatchStockModified(stock);
		changed=false;
	}
	
	private JSpinner getSpinner(double min,double max,double stepSize, int digits)
    {
        double value = 1.000;
        SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);
        JSpinner spinner = new JSpinner(model);
        JSpinner.NumberEditor editor = (JSpinner.NumberEditor)spinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(digits);
        editor.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
        Dimension d = spinner.getPreferredSize();
        d.width = 85;
        spinner.setPreferredSize(d);
        return spinner;
    }

	void log(String s) {
		//System.out.println("[Panel_Profit]"+s);

	}




	@Override
	public void incrementStep(double incerment) {
		// TODO Auto-generated method stub
		JSpinner spinner_take =	(JSpinner)component[TAKEPRICE];
		JSpinner spinner_loss =	(JSpinner)component[LOSSPRICE];
		SpinnerNumberModel model=(SpinnerNumberModel)spinner_take.getModel();
		model.setStepSize(incerment);
		model=(SpinnerNumberModel)spinner_loss.getModel();
		model.setStepSize(incerment);
	}






}
