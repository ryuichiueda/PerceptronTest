import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;


public class DualKernelPerceptron extends DualPerceptron {
	
	private double _delta = 0.2;
	
	public DualKernelPerceptron() {
		super();
	}
	
	private void dual_karnel_bp(){
		if(_training_set.size()==0){
			return;
		}
		
		final double max_2norm = _training_set.elementAt(getNormMaxIndex()).norm2();	

		for(int i=0;i<_training_set.size();i++){
			TrainingData data = _training_set.elementAt(i);
			
			if(getFunctionMargin(data) <= 0.0){
				_dual.incrementAlpha(i);
				_dual.setBias(_dual.getBias() + data.getLabel()*max_2norm);
			}
		}
	}
	
	public void execute(){
		System.out.println("Dual Karnel");
		dual_karnel_bp();
	}
	
	protected double decisionFunction(TrainingData data){
		double ans = _dual.getBias();
		for(int i=0;i<_training_set.size();i++){
			TrainingData data_i = _training_set.elementAt(i);
			
			if(_dual.getAlpha(i) < 0.001){
				continue;
			}
			
			ans += _dual.getAlpha(i)
					*data_i.getLabel()
					*gaussKarnel(data_i.getVector(), data.getVector());
		}
		
		return ans;			
	}
	
	public double getFunctionMargin(TrainingData data){
		return decisionFunction(data)*data.getLabel();		
	}
	
	protected double gaussKarnel(Vector<Double> v,Vector<Double> w){
		
		double diff2 = 0.0;
		for(int i=0;i<v.size();i++){
			diff2 += (v.elementAt(i) - w.elementAt(i))*(v.elementAt(i) - w.elementAt(i));
		}
		
		return Math.exp(-diff2/(2*_delta*_delta));
	}

	@Override
	public void drawBound(Graphics g, int w, int h) {
		for(int x=0;x<w;x++){
			for(int y=0;y<h;y++){
				Vector<Double> v = new Vector<Double>();
				v.add((double)x/w);
				v.add((double)y/h);
				TrainingData data = new TrainingData(v,0.0);
				
				if(decisionFunction(data) > 0.0){
					g.setColor(new Color(200,200,255));
				}
				else{
					g.setColor(new Color(255,200,200));
				}
				g.drawRect(x, y, 1, 1);
			}
		}
	}

	@Override
	public double getMargin(TrainingData data) {
		return getFunctionMargin(data);
	}
}

