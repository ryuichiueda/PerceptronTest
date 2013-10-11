import java.util.Vector;


public class TrainingData {
	private Vector<Double> _x = new Vector<Double>();
	private double _y;
	private int _vector_size;
	
	public TrainingData(Vector<Double> x,double y){
		_x = x;
		_y = y;
		_vector_size = _x.size();
	}
	
	
	public Vector<Double> getVector(){
		return _x;
	}
	
	public double getLabel(){
		return _y;
	}
	
	public double norm2(){
		double ans = 0.0;
		for(int i=0;i<_vector_size;i++){
			ans += _x.elementAt(i)*_x.elementAt(i);
		}	
		
		return ans;
	}
	
	public double norm(){
		return Math.sqrt(norm2());
	}
	
	public double getVectorInnerProduct(TrainingData rhs){
		double ans = 0.0;
		for(int i=0;i<_vector_size;i++){
			ans += _x.elementAt(i)*rhs.getVector().elementAt(i);
		}		
		return ans;
	}

}
