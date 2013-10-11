import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;


public class DualPerceptron extends Perceptron {
	protected DualForm _dual = new DualForm();
	
	public DualPerceptron(){
		_dual.clear();
		while(_training_set.size() > _dual.getNumOfAlpha()){
			_dual.addAlpha();
		}
	}
	
	public void addTrainingData(TrainingData data){
		_training_set.add(data);
		_dual.addAlpha();
	}
	
	public void execute(){
		System.out.println("Dual");
		dual_bp();
	}
	
	public void clear(){
		_dual.clear();
		_training_set.clear();
		_dual = new DualForm();
	}
	
	private void dual_bp(){
		if(_training_set.size()==0){
			return;
		}
		
		final double max_2norm = _training_set.elementAt(getNormMaxIndex()).norm2();	

		for(int i=0;i<_training_set.size();i++){
			TrainingData data_i = _training_set.elementAt(i);
			
			double sum = _dual.getBias();
			for(int j=0;j<_training_set.size();j++){
				TrainingData data_j = _training_set.elementAt(j);
				
				sum += _dual.getAlpha(j)
						*data_j.getLabel()
						*data_j.getVectorInnerProduct(data_i);
			}
			sum *= data_i.getLabel();
			
			if(sum <= 0.0){
				_dual.incrementAlpha(i);
				_dual.setBias(_dual.getBias() + data_i.getLabel()*max_2norm);
			}
		}
	}
	
	public HyperPlane getHyperPlane(){
		HyperPlane ans = new HyperPlane();
		Vector<Double> w = new Vector<Double>();
		
		for(int dim=0;dim<ans.getDimension();dim++){
			double sum = 0.0;
			
			for(int i=0;i<_training_set.size();i++){
				sum += _dual.getAlpha(i)
						*_training_set.elementAt(i).getLabel()
						*_training_set.elementAt(i).getVector().elementAt(dim);
			}
			
			w.add(sum);
		}
		
		ans.set(w,_dual.getBias());
		return ans;
	}

	public void drawBound(Graphics g, int w, int h){
		HyperPlane normalized_plane = getHyperPlane().getNormalizedPlane();
		
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
		return getHyperPlane().getGeometricMargin(data);
	}
	
	public void drawDataPoint(Graphics g, int width, int height){
		
		System.out.println(getTrainingDataNum());

		for(int i=0;i<getTrainingDataNum();i++){
			final TrainingData data = getTrainingDataAt(i);
			
			double margin = getMargin(data);
			g.setColor(getMarginColor(margin));
			g.drawString(getLabelMark(data.getLabel(),_dual.getAlpha(i)), 
						 getRound(data.getVector().elementAt(0)*width)-3,
						 getRound(data.getVector().elementAt(1)*height)+3);
		}
	}
}
