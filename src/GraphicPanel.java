import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;

public class GraphicPanel extends JPanel{
	private static final long serialVersionUID = 1701284289052638330L;
	private int _magnitude;
	private Perceptron _perceptrion;
	private double _label;
	
	public void setLabel(double d){
		_label = d;
	}
	
	public void setPerceptron(Perceptron p){
		_perceptrion = p;
	}
	
	public GraphicPanel(Perceptron perceptron){
		super();
		_perceptrion = perceptron;
		_magnitude = 1;
		_label = 1.0;

		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent arg0) {

			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {
				Point pos = relative_point(arg0);
				Vector<Double> position = new Vector<Double>();
				position.add((double)pos.x/getWidth());
				position.add((double)pos.y/getHeight());
				
				_perceptrion.addTrainingData(new TrainingData(position,_label));
			}
			public void mouseReleased(MouseEvent arg0) {
				repaint();
			}
		});
	}
	
	private Point relative_point(MouseEvent arg0){
		Point p = arg0.getLocationOnScreen();
		Point q = getLocationOnScreen();
		p.x -= q.x;
		p.y -= q.y;
		p.x /= _magnitude;
		p.y /= _magnitude;
		return p;
	}
	
	public void setMagnitude(int value){
		_magnitude = value > 0 ? value : 1;
	}
	
	public void paintComponent(Graphics g){
		g.setColor(new Color(255,255,255));
		g.fillRect(0,0,getWidth(),getHeight());
		_perceptrion.drawBound(g,getWidth(),getHeight());
		_perceptrion.drawDataPoint(g,getWidth(),getHeight());
	}
	
}
