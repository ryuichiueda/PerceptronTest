import java.util.Vector;


public class HyperPlane {
	private final int _dimension = 2;
	private Vector<Double> _w;
	private double _b;
	
	public HyperPlane(){
		_w = new Vector<Double>();
		_w.add(0.0);
		_w.add(0.0);
		_b = 0.0;
	}
	
	public int getDimension(){
		return _dimension;
	}
	
	public Vector<Double> getVector(){
		return _w;
	}
	public double getDistance(){
		return _b;
	}
	
	public String print(){
		return _w.elementAt(0) + "x + "  + _w.elementAt(1) + "y + " + _b;
	}

	public void move(TrainingData data,double ratio,double max_2norm){
		for(int i=0;i<_w.size();i++){
			_w.setElementAt(
					_w.elementAt(i) + ratio*data.getLabel()*data.getVector().elementAt(i),
					i);
		}
		
		_b += ratio*data.getLabel()*max_2norm;
	}

	public void set(Vector<Double> w,double b){
		_w = w;
		_b = b;
	}
	
	public HyperPlane getNormalizedPlane(){
		HyperPlane ans = new HyperPlane();
		double w_norm = norm();
		if(w_norm < 0.001){
			return ans;
		}
		for(int i=0;i<_dimension;i++){
			ans._w.setElementAt(_w.elementAt(i)/w_norm, i);
		}	
		ans._b = _b/w_norm;
		
		return ans;
	}
	
	private double norm2(){
		double ans = 0.0;
		for(int i=0;i<_dimension;i++){
			ans += _w.elementAt(i)*_w.elementAt(i);
		}	
		
		return ans;
	}
	
	private double norm(){
		return Math.sqrt(norm2());
	}
	
	public double getFunctionMargin(TrainingData data){
		double inner_product = 0.0;
		for(int i=0;i<data.getVector().size();i++){
			inner_product += _w.elementAt(i)*data.getVector().elementAt(i);
		}
		
		return data.getLabel()*(inner_product + _b);
	}
	
	public double getGeometricMargin(TrainingData data){
		double ans = getFunctionMargin(data);
		
		return ans/norm();
	}
}
