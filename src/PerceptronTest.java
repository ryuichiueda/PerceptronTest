import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JComboBox;

public class PerceptronTest extends JApplet implements ActionListener{

	private static final long serialVersionUID = 3248436352518736444L;
	
	private Perceptron _perceptron;// = new DualKarnelPerceptron();
	private static GraphicPanel _graphic_window;
	private static ControlPanel _control_panel;
	private String [] _type = {"Gauss SVM","dual kernel","dual form","main form"};
	private JComboBox _type_cbox;
	
	private void changePerceptron(){
		String type_name = (String)_type_cbox.getSelectedItem();
		if(type_name.equals(_type[0])){
			_perceptron = new KernelSVM();
		}	
		else if(type_name.equals(_type[1])){
			_perceptron = new DualKernelPerceptron();
		}
		else if(type_name.equals(_type[2])){
			_perceptron = new DualPerceptron();	
		}
		else{	
			_perceptron = new NormalPerceptron();
		}
		_graphic_window.setPerceptron(_perceptron);
		_control_panel.setPerceptron(_perceptron);
	}
	
	/**
	 * @param args
	 */
	public PerceptronTest(){
		_type_cbox = new JComboBox(_type);
		_perceptron = new KernelSVM();
		_graphic_window = new GraphicPanel(_perceptron);
		_control_panel = new ControlPanel(_perceptron,_graphic_window,_type_cbox);		
	}
	
	public void init(){
		
		setSize(600,500);
		setVisible(true);
		
		_graphic_window.setBounds(0, 0, 400,400);
		_control_panel.setBounds(_graphic_window.getWidth()+10, 0,150,160);
		
		setLayout(null);
		add(_graphic_window,null);
		add(_control_panel,null);
		
		_type_cbox.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				changePerceptron();
			}
		});
	}

	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

}
