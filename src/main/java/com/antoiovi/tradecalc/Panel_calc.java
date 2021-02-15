package com.antoiovi.tradecalc;

import java.awt.Color;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.ArrayUtils;

import javax.swing.border.LineBorder;

public class Panel_calc extends JPanel implements ChangeListener,ActionListener {

static final int N_VALS=3;

static final Color TEXTCOLOR=Color.YELLOW;
JComponent[] component=new JComponent[12];
String[] labels_calc_text= {"Value","Increment","Percent","Value percent"};
JLabel[] labels_calc=new JLabel[4];

JComboBox[] combo_increment = new JComboBox[N_VALS];
JSpinner[] spinners_val=new JSpinner[N_VALS];
JSpinner[] spinners_decstep=new JSpinner[N_VALS];
JSpinner[] spinners_perc=new JSpinner[N_VALS];

JTextField[] textField_val_perc=new JTextField[N_VALS];
private JTextField textField;

Calc_perc[] calc_perc=new Calc_perc[N_VALS];

	/**
	 * Create the panel.
	 */
	public Panel_calc() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
	
		Box box_val=Box.createVerticalBox();
		Box box_increment=Box.createVerticalBox();

		Box box_perc=Box.createVerticalBox();
		Box box_val_perc=Box.createVerticalBox();
		for(int i=0;i<4;i++) {
		labels_calc[i]=new JLabel(labels_calc_text[i]);
		}
		box_val.add(labels_calc[0]);
		box_increment.add(labels_calc[1]);
		box_perc.add(labels_calc[2]);
		box_val_perc.add(labels_calc[3]);

		//	SpinnerNumberModel(value,minimum,maximum,stepSize)
		for(int i=0;i<N_VALS;i++) {
			calc_perc[i]=new Calc_perc();
			spinners_val[i]=this.getSpinner(0.0,100000.0,0.001, 3);
			spinners_val[i].addChangeListener(this);
			box_val.add(spinners_val[i]);
			
			combo_increment[i]=new JComboBox();
			combo_increment[i].setModel(new DefaultComboBoxModel( new Double[] {0.001,0.01,0.1,1.0,10.0,100.0,1000.0}));
			combo_increment[i].addActionListener(this);
			spinners_decstep[i]=new JSpinner(new SpinnerListModel(new Double[] {0.001,0.01,0.1,1.0,10.0,100.0,1000.0}));
			spinners_decstep[i].addChangeListener(this);
			box_increment.add(combo_increment[i]);
			
			spinners_perc[i]=this.getSpinner(0.0,100.0,0.01, 2);
			spinners_perc[i].addChangeListener(this);
			box_perc.add(spinners_perc[i]);

			textField_val_perc[i] = new JTextField();
			textField_val_perc[i].setColumns(10);
			textField_val_perc[i].setEditable(false);
			textField_val_perc[i].setBackground(TEXTCOLOR);
			box_val_perc.add(textField_val_perc[i]);
			
			combo_increment[i].setSelectedIndex(3);

		}
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.setBorder(new TitledBorder(null, "Value percentage calculator", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel);
		panel.add(box_increment);
		panel.add(box_val);
		panel.add(box_perc);
		panel.add(box_val_perc);
		
		
		
	}

	
	
	
	class Calc_perc{
		double valore=1.0;
		double perc=10.0;
		double valore_perc=0.1;
		
		public Calc_perc() {
			super();
			
		}
		public double getVal() {
			return valore;
		}
		public void setVal(double val) {
			this.valore = val;
			this.valore_perc=this.perc*this.valore/100;
		}
		
		public void setPerc(double perc) {
			this.perc = perc;
			this.valore_perc=this.perc*this.valore/100;
		}
		
		public double getVal_perc() {
			return valore_perc;
		}
		
	}
	
	private JSpinner getSpinner(double min,double max,double stepSize, int digits)
    {
        double value = 1.000;
        //   SpinnerNumberModel(value,minimum,maximum,stepSize)
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(Arrays.asList(spinners_val).contains(arg0.getSource()) ) {
			int i=Arrays.asList(spinners_val).indexOf(arg0.getSource());
			log(String.valueOf(i));

			double v=(Double)((JSpinner) arg0.getSource()).getValue();
			//log(String.valueOf(v));
			calc_perc[i].setVal(v);
			double x=calc_perc[i].getVal_perc();
			textField_val_perc[i].setText(String.format("%,.4f",x ));
			
		}else if(Arrays.asList(spinners_perc).contains(arg0.getSource()) ) {
			int i=Arrays.asList(spinners_perc).indexOf(arg0.getSource());
			double p=(Double)((JSpinner) arg0.getSource()).getValue();
			calc_perc[i].setPerc(p);
			double x=calc_perc[i].getVal_perc();
			textField_val_perc[i].setText(String.format("%,.4f",x ));

		}else if(Arrays.asList(spinners_decstep).contains(arg0.getSource()) ) {
			int i=Arrays.asList(spinners_decstep).indexOf(arg0.getSource());
			double step=(Double)((JSpinner) arg0.getSource()).getValue();
			SpinnerNumberModel model=(SpinnerNumberModel)spinners_val[i].getModel();
			model.setStepSize(step);
		}
					
	}
	void log(String s) {
		//System.out.println("[Panel_calc]"+s);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// Intercept combo box
		if(Arrays.asList(combo_increment).contains(arg0.getSource()) ) {
			int i=Arrays.asList(combo_increment).indexOf(arg0.getSource());
			double step=(Double)((JComboBox) arg0.getSource()).getSelectedItem();
			SpinnerNumberModel model=(SpinnerNumberModel)spinners_val[i].getModel();
			model.setStepSize(step);
		}
		
	}
}
