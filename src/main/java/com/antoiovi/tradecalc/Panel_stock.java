package com.antoiovi.tradecalc;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Panel_stock extends JPanel implements ChangeListener,ChangStockListener,ActionListener{
static final int NAME=0;
static final int CODE=1;
static final int INCREMENT=2;

static final int PRICE=3;
static final int NSTOCK=4;
static final int TOTALPRICE=5;
static final int BUYCOST=6;
static final int SELLCOST=7;
static final int CAPITAL=8;
static final int NSTOCK_MAX=9;

static final Color TEXTCOLOR=Color.YELLOW;
JComponent[] component=new JComponent[12];
JLabel[] labels=new JLabel[12];
String[] etich= {"Name","Code","Increment","Price","N. stocks", "Total price", "Buy cost","Sell cost","Capital","Max stocks"};

Stock stock;
ChangedStockDistpatcher dispatcher;


	/**
	 * Create the panel.
	 */
	public Panel_stock(ChangedStockDistpatcher dispatcher ,Stock stock) {
		this.dispatcher=dispatcher;
		this.dispatcher.add(this);
		log("[Costructor]"+stock.getCode());
		stock.log();
		// stock=new Stock();
		// setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setLayout(new BorderLayout());
		Box box_panel=Box.createVerticalBox();
		add(box_panel,BorderLayout.CENTER);
		
		JPanel panelA=new JPanel();
		JPanel panelB=new JPanel();		
		JPanel panelC=new JPanel();		
		panelC.setBorder(new TitledBorder(null, "Quantity calculator", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		FlowLayout flowLayout = (FlowLayout) panelA.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		FlowLayout flowLayoutb = (FlowLayout) panelB.getLayout();
		flowLayoutb.setAlignment(FlowLayout.LEFT);
		
		FlowLayout flowLayoutc = (FlowLayout) panelC.getLayout();
		flowLayoutc.setAlignment(FlowLayout.LEFT);
		
		
		JTextField textField_name = new JTextField();
		textField_name.setColumns(50);
		textField_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				
				String s=((JTextField)e.getComponent()).getText();
				log("[focusLost]"+s);
				setNameStock( s);
			}
		});
		component[NAME]=textField_name;
		JTextField textField_code = new JTextField();
		textField_code.setColumns(10);
		textField_code.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
					String s=((JTextField)e.getComponent()).getText();
					log("[focusLost]"+s);
					setCodeStock( s);
			}
		});
		
		component[CODE]=textField_code;
		JTextField textField_totalprice = new JTextField();
		textField_totalprice.setEditable(false);
		textField_totalprice.setColumns(10);
		textField_totalprice.setBackground(TEXTCOLOR);
		component[TOTALPRICE]=textField_totalprice;
		
		JComboBox combo_increment=new JComboBox();
		combo_increment.setModel(new DefaultComboBoxModel( new Double[] {0.0001,0.001,0.01,0.1,1.0,10.0,100.0}));
		combo_increment.addActionListener(this);
		component[INCREMENT]=combo_increment;

		
		JSpinner spinner_price = getSpinner(0.001,1000.0, 0.0001,4);
		spinner_price.addChangeListener(this);
		component[PRICE]=spinner_price;
		JSpinner spinner_nstocks = new JSpinner();
		spinner_nstocks.setPreferredSize(new Dimension(100, 20));
		spinner_nstocks.setModel(new SpinnerNumberModel(1,1 ,null,1));
		spinner_nstocks.addChangeListener(this);
		component[NSTOCK]=spinner_nstocks;
		JSpinner spinner_buycost = new JSpinner();
		spinner_buycost.setPreferredSize(new Dimension(100, 20));
		spinner_buycost.setModel(new SpinnerNumberModel(1.0,0.0 ,null,0.5));
		spinner_buycost.addChangeListener(this);
		component[BUYCOST]=spinner_buycost;
		JSpinner spinner_sellcost = new JSpinner();
		spinner_sellcost.setPreferredSize(new Dimension(100, 20));
		spinner_sellcost.setModel(new SpinnerNumberModel(1.0,0.0 ,null,0.5));
		spinner_sellcost.addChangeListener(this);
		component[SELLCOST]=spinner_sellcost;
		
		JSpinner spinner_capital = new JSpinner();
		spinner_capital.setPreferredSize(new Dimension(100, 20));
		spinner_capital.setModel(new SpinnerNumberModel(10000.0,0.0 ,null,500.0));
		spinner_capital.addChangeListener(this);
		component[CAPITAL]=spinner_capital;
		
		JTextField textField_maxquantity = new JTextField();
		textField_maxquantity.setEditable(false);
		textField_maxquantity.setColumns(10);
		textField_maxquantity.setBackground(TEXTCOLOR);
		component[NSTOCK_MAX]=textField_maxquantity;
		
		for(int i=0;i<=9;i++) {
			JLabel lblNewLabel = new JLabel(etich[i]);
			labels[i]=lblNewLabel;
			JPanel panel;
			panel= (i<=CODE ? panelA : panelB);
			panel=(i>=CAPITAL? panelC:panel);
			panel.add(lblNewLabel);
			panel.add(component[i]);

		}
		box_panel.add(panelA);
		box_panel.add(panelB);
		box_panel.add(panelC);

		this.setStock(stock);	
		
		// textField_code.setText("Ciao");
		//((JTextField)component[NAME]).setText(stock.getCode());
		log("[Costructor]"+String.valueOf(stock.getCode()));
		
		recalc();

	}
	
	private final void setNameStock(String s) {
		this.stock.setName(s);
	}
	private final void setCodeStock(String s) {
		stock.setCode(s);
	}
public Stock getStock() {
		return stock;
	}

private boolean changed=false;

	public void setStock(Stock stock) {
		this.stock = stock;
		log("[setStock]-"+"[stock.getCapital]"+String.valueOf(stock.getCapital()));
		// stock.log();

		((JTextField)component[NAME]).setText(stock.getName());
		((JTextField)component[CODE]).setText(stock.getCode());
		//stock.calc();
		changed=true;
		((JSpinner)component[PRICE]).setValue(stock.getPrice());	
		changed=true;
		((JSpinner)component[NSTOCK]).setValue(stock.getQuantity());	
		changed=true;
		((JSpinner)component[BUYCOST]).setValue(stock.getBuycost());	
		changed=true;
		((JSpinner)component[SELLCOST]).setValue(stock.getSellcost());	
		((JTextField)component[TOTALPRICE]).setText(String.format("%,.4f", stock.getTotalprice()));
		changed=true;
		((JSpinner)component[CAPITAL]).setValue(stock.getCapital());	
		((JTextField)component[NSTOCK_MAX]).setText(String.format("%,.1f", stock.maxQuantity()));
		
		log("[setStock]--");
		//this.stock.log();
		changed=false;

	}

public void recalc() {
	
	stock.calc();
	stock.setName(((JTextField)component[NAME]).getText());
	stock.setCode(((JTextField)component[CODE]).getText());

	//System.out.println(String.format(
    //Locale.FRANCE, "%,.2f", d));     
	((JTextField)component[TOTALPRICE]).setText(String.format("%,.4f", stock.totalprice));
	((JTextField)component[NSTOCK_MAX]).setText(String.format("%,.1f", stock.maxQuantity()));

}

@Override
public void changedStock(Stock stock) {
	// New stock 
	//log("Panel stock :CHANGED STOCK ");
		this.setStock(stock);

}


	public void stateChanged(ChangeEvent arg0) {
		log("[stateChanged]"+String.valueOf(changed));
		// To implement spinner change values
		if (arg0.getSource()==component[PRICE]) {
			JSpinner spinner = (JSpinner) arg0.getSource();
			double price=(Double)spinner.getValue();
			stock.price=price;
		}else if(arg0.getSource()==component[NSTOCK]) {
			
			int nstock=(Integer)( (JSpinner) arg0.getSource()).getValue();
			stock.quantity=nstock;
		
		}else if(arg0.getSource()==component[BUYCOST]) {
				stock.buycost=(Double)( (JSpinner) arg0.getSource()).getValue();
				
		
		}else if(arg0.getSource()==component[SELLCOST]) {
				JSpinner spinner = (JSpinner) arg0.getSource();
				stock.sellcost=(Double)spinner.getValue();
		}else if(arg0.getSource()==component[CAPITAL]) {
			log("[CAPITALSPINNER]");
			JSpinner spinner = (JSpinner) arg0.getSource();
			stock.capital=(Double)spinner.getValue();
	}
		dispatcher.dispatchRecalc();
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
		//System.out.println("[Panel_stock]"+s);

	}

	@Override
	public void modifiedStock(Stock stock) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Action listener to intercept change of combo box
		if (arg0.getSource()==component[INCREMENT]) {
			double step=(Double)((JComboBox) arg0.getSource()).getSelectedItem();
			JSpinner spinner=(JSpinner)component[PRICE];
			SpinnerNumberModel model=(SpinnerNumberModel)spinner.getModel();
			model.setStepSize(step);

		}
		
		
	}
}
