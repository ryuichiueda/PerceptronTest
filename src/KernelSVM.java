import java.awt.Graphics;
import java.util.Vector;


public class KernelSVM extends DualKernelPerceptron {
	private double _c;
	
	public KernelSVM(){
		super();
		_c = 10000.0;
	}
	
	private int getMaxSupportVectorIndex(){
		double max = 0.0;
		int ans = 0;
		for(int i=0;i<_dual.getNumOfAlpha();i++){
			double value = _dual.getAlpha(i)*_training_set.elementAt(i).getLabel();
			if(value > max){
				max = value;
				ans = i;
			}
		}
		
		return ans;
	}
	
	private double getBias(){
		int sv_index = getMaxSupportVectorIndex();
		double ans = _training_set.elementAt(sv_index).getLabel();
		
		for(int i=0;i<_dual.getNumOfAlpha();i++){
			ans -= _training_set.elementAt(i).getLabel()*_dual.getAlpha(i)
					*gaussKarnel(_training_set.elementAt(sv_index).getVector(), 
							_training_set.elementAt(i).getVector());
		}
		
		return ans;
	}
	
	private void SMO(){
		System.out.println("Gauss SVM");
				
		for(int i=0;i<_training_set.size();i++){
			if(KKTSatisfied(i)){
				continue;
			}
			
			
	//		System.out.println("compare");
	//		CompareTwoTrainingData(i,selectComparedIndex(i));

			
		//	Random rnd = new Random();
			//int j = rnd.nextInt(_dual.getNumOfAlpha());
			for(int j=0;j<_training_set.size();j++)
			if(i != j){
				CompareTwoTrainingData(i,j);
			}
		}
		_dual.setBias(getBias());
	}
	
	private boolean KKTSatisfied(int index){
		double margin = getMargin(_training_set.elementAt(index));
		double alpha = _dual.getAlpha(index);
		
		if(alpha < 0.00000001){
			return margin >= 1.0;
		}
		
		if(alpha < _c){
			return Math.abs(margin - 1.0) < 0.0000001;
		}
		
		return margin <= 1.0;
	}
	/*
	private int selectComparedIndex(int index){
		int ans = 0;
		double max = 0.0;
		for(int i=0;i<_dual.getNumOfAlpha();i++){
			double diff = Math.abs(DifferenceFunction(i)-DifferenceFunction(index));
			if(diff > max){
				ans = i;
				max = diff;
			}
		}
		
		return ans;
	}*/
	
	private void CompareTwoTrainingData(int i, int j){
		double new_alpha_j = _dual.getAlpha(j)
				+ _training_set.elementAt(j).getLabel()
				*(DifferenceFunction(i)-DifferenceFunction(j))/kappa(i,j);
		
		new_alpha_j = applyBound(new_alpha_j, i, j);
		
		double new_alpha_i = _dual.getAlpha(i)
				+ _training_set.elementAt(i).getLabel()*_training_set.elementAt(j).getLabel()*
				(_dual.getAlpha(j) - new_alpha_j);

		_dual.setAlpha(i, new_alpha_i);
		_dual.setAlpha(j, new_alpha_j);		
	}
	
	private double applyBound(double alpha, int i, int j){
		
		double supreme = getSup(i,j);
		if(alpha > supreme){
			return supreme;
		}
		double inferior = getInf(i,j);
		if(alpha < inferior){
			return inferior;
		}
		
		return alpha;
	}
	
	private double getInf(int i,int j){
		if(_training_set.elementAt(i).getLabel()*_training_set.elementAt(j).getLabel() < 0.5){//different
			return Math.max(0.0,_dual.getAlpha(j)-_dual.getAlpha(i));
		}
		
		return Math.max(0.0,_dual.getAlpha(i)+_dual.getAlpha(j) - _c);
	}
	
	private double getSup(int i,int j){
		if(_training_set.elementAt(i).getLabel()*_training_set.elementAt(j).getLabel() < 0.5){//different
			return Math.min(_c,_c - _dual.getAlpha(i)+_dual.getAlpha(j));
		}
		
		return Math.min(_c, _dual.getAlpha(i)+_dual.getAlpha(j));		
	}
	
	public void execute(){
		SMO();
	}
	
	private double DifferenceFunction(int i){
		TrainingData data = _training_set.elementAt(i);
		return decisionFunction(data) - data.getLabel();
	}

	private double kappa(int i,int j){
		Vector<Double> v = _training_set.elementAt(i).getVector();
		Vector<Double> w = _training_set.elementAt(j).getVector();
		
		return gaussKarnel(v,v)+gaussKarnel(w,w)-2*gaussKarnel(v,w);
	}
	
	@Override
	public void drawBound(Graphics g, int w, int h) {
		super.drawBound(g,w,h);
	}
}
