import java.util.Vector;


public class DualForm {
	private Vector<Double> _alpha = new Vector<Double>();
	private double _b = 0.0;
	

	public void clear(){
		_alpha.clear();
		_b = 0.0;
	}
	
	public void addAlpha(){
		_alpha.add(0.0);
	}
	
	public int getNumOfAlpha(){
		return _alpha.size();
	}
	
	public double getBias(){
		return _b;
	}
	
	public double getAlpha(int index){
		return _alpha.elementAt(index);
	}
	
	public void setAlpha(int index,double value){
		_alpha.setElementAt(value, index);
	}
	
	public void incrementAlpha(int index){
		_alpha.setElementAt(_alpha.elementAt(index)+1.0, index);
	}
	
	public void setBias(double b){
		_b = b;
	}
}
