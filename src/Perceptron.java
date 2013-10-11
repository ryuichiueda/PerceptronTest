import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public abstract class Perceptron {
	protected static Vector<TrainingData> _training_set = new Vector<TrainingData>();
	
	public abstract void clear();
	public abstract void execute();
	public abstract void addTrainingData(TrainingData data);
	public abstract void drawBound(Graphics g, int w, int h);
	public abstract double getMargin(TrainingData data);
	
	public void drawDataPoint(Graphics g, int width, int height){
		
		System.out.println(getTrainingDataNum());

		for(int i=0;i<getTrainingDataNum();i++){
			final TrainingData data = getTrainingDataAt(i);
			
			double margin = getMargin(data);
			g.setColor(getMarginColor(margin));
			g.drawString(getLabelMark(data.getLabel(),margin), 
						 getRound(data.getVector().elementAt(0)*width)-3,
						 getRound(data.getVector().elementAt(1)*height)+3);
		}
	}
	
	protected Color getMarginColor(double margin){
		return margin > 0.0 ? new Color(0,0,0) : new Color(255,0,0);
	}

	
	protected String getLabelMark(double label_value, double margin){
		String ans = new String(label_value > 0.0 ? "x " : "o ");
		ans += (double)(getRound(margin*1000))/1000;
		
		return ans;
	}
	
	public int getTrainingDataNum(){
		return _training_set.size();
	}
	
	public TrainingData getTrainingDataAt(int pos){
		if(pos >= getTrainingDataNum()){
			return null;
		}
		return _training_set.elementAt(pos);
	}

	protected int getNormMaxIndex(){
		double max_norm2 = 0.0;
		int ans = 0;
		int size = _training_set.size();
		for(int i=0;i<size;i++){
			double norm2 =  _training_set.elementAt(i).norm2();
			if(max_norm2 < norm2){
				max_norm2 = norm2;
				ans = i;
			}
		}
		return ans;
	}
	
	protected int getRound(double x){
		return (int)Math.round(x);
	}

}
