import java.awt.Color;
import java.awt.Graphics;


public class NormalPerceptron extends Perceptron {
	
	private HyperPlane _plane = new HyperPlane();
	
	public NormalPerceptron(){
		super();
	}
	
	public void execute(){
		System.out.println("normal");
		bp();
	}
	
	private void bp(){
		if(_training_set.size()==0){
			System.out.println("no data");
			return;
		}

		System.out.println(_plane.print());
		
		final double max_2norm = _training_set.elementAt(getNormMaxIndex()).norm2();	
			
		for(int i=0;i<_training_set.size();i++){	
			
			if(_plane.getFunctionMargin(_training_set.elementAt(i)) <= 0.0){
				_plane.move(_training_set.elementAt(i), 0.1,max_2norm);		
			}	
		}
	}
	
	public void clear(){
		_training_set.clear();
		_plane = new HyperPlane();
	}
	
	public void addTrainingData(TrainingData data){
		_training_set.add(data);
	}
	
	public void drawBound(Graphics g, int w, int h){
		HyperPlane normalized_plane = _plane.getNormalizedPlane();
		
		System.out.println("normalized: " + normalized_plane.print());
		double vx = normalized_plane.getVector().elementAt(0);
		double vy = normalized_plane.getVector().elementAt(1);
		double b = normalized_plane.getDistance();

		if(vx*vx + vy*vy < 0.0001){
			return;			
		}
		
		g.setColor(new Color(255,0,0));
		g.drawLine(0,0, getRound(-b*vx*w),getRound(-b*vy*h));
		
		double plane_direction = Math.atan2(vy, vx) + Math.PI/2;
		
		g.setColor(new Color(0,0,0));
		g.drawLine(getRound(-b*vx*w +10*w*Math.cos(plane_direction)),
				   getRound(-b*vy*h +10*h*Math.sin(plane_direction)),
				   getRound(-b*vx*w -10*w*Math.cos(plane_direction)),
				   getRound(-b*vy*h -10*h*Math.sin(plane_direction)));
	}
	

	@Override
	public double getMargin(TrainingData data) {
		return _plane.getGeometricMargin(data);
	}
}
