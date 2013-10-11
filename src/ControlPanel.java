import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 3291623499859809350L;
	private Perceptron _perceptron;
	private final GraphicPanel _graphic_window;

	private JRadioButton _positive_btn;
	private JRadioButton _negative_btn;
	private JButton _one_step_btn;
	private JButton _ten_step_btn;
	private JButton _clear_btn;
	private JComboBox _type_cbox;
	
	public void setPerceptron(Perceptron p){
		_perceptron = p;
	}

	
	private void giveLabelName(){
		_positive_btn = new JRadioButton("x:  1",true);
		_negative_btn = new JRadioButton("o: -1");
		_one_step_btn = new JButton("one step");
		_ten_step_btn = new JButton("ten steps");
		_clear_btn = new JButton("clear");
		_graphic_window.setLabel(1.0);

		_type_cbox.setPreferredSize(new Dimension(100, 20));
	}
	
	private void addButtons(){
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(_type_cbox);		
		add(_positive_btn);		
		add(_negative_btn);
		add(_one_step_btn);
		add(_ten_step_btn);
		add(_clear_btn);
		ButtonGroup group = new ButtonGroup();
		group.add(_positive_btn);
		group.add(_negative_btn);

	}
	
	public ControlPanel(Perceptron perceptron, GraphicPanel graphic_window, JComboBox combo){
		_perceptron = perceptron;
		_graphic_window = graphic_window;
		_type_cbox = combo;
		
		giveLabelName();
		addButtons();

		
		_one_step_btn.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				_perceptron.execute();
				_graphic_window.repaint();
			}

			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		_ten_step_btn.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				
				for(int i=0;i<10;i++){
					_perceptron.execute();
				}
				_graphic_window.repaint();
			}

			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		_positive_btn.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {}


			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				if(_positive_btn.isSelected()){
					_graphic_window.setLabel(1.0);
				}
				else{
					_graphic_window.setLabel(-1.0);
				}	
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		
		_negative_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {setLabel();}});
	
		_clear_btn.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				_perceptron.clear();
				_graphic_window.repaint();
			}
			public void mouseReleased(MouseEvent e) {setLabel();}});
	}
	
	private void setLabel(){
		if(_positive_btn.isSelected()){
			_graphic_window.setLabel(1.0);
		}
		else{
			_graphic_window.setLabel(-1.0);
		}	
	}
	
	public boolean isSetPositive(){
		return _positive_btn.isSelected();
	}
}
