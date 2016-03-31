package math;

import java.util.Vector;

public class MyMath {
	
	public static double normalizeAngle(double angle){
		return Math.signum(angle)*(Math.abs(angle) - 2*Math.PI*Math.floor(Math.abs(angle)/(2*Math.PI)));
	}
	
	public static double scalarProduct(Vector<Double> vec0, Vector<Double> vec1){
		double res = 0;
		for(int i = 0; i<vec0.size();i++){
			res+=vec0.get(i)*vec1.get(i);	
		}
		return res;
	}
	
	public static double vectorAbs(Vector<Double> vec){
		double res = 0;
		for(int i = 0; i<vec.size();i++){
			res+=Math.pow(vec.get(i),2);	
		}
		return Math.sqrt(res);
	}
	
	public static Vector<Double> normalizeVector(Vector<Double> vec){
		double abs = vectorAbs(vec);
		if (abs != 0){
			Vector<Double> res = new Vector<Double>();
			for(int i = 0; i < vec.size(); i++){
				res.add(vec.get(i)/abs);
			}
			return res;
		}
		else{
			return vec;
		}
	}
	
	public static double distance(Vector<Double> vec0, Vector<Double> vec1){
		Vector<Double> res = new Vector<Double>();
		for(int i = 0; i<vec0.size(); i++){
			res.add(vec0.get(i)-vec1.get(i));
		}
		return vectorAbs(res);
	}
}
