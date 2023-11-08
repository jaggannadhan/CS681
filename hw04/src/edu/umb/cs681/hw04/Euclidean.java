package edu.umb.cs681.hw04;
import java.util.List;
import java.util.stream.IntStream;

public class Euclidean implements DistanceMetric {
	public double distance(List<Double> p1, List<Double> p2) throws Exception {
		
		// if(p1.size() != p2.size()) {
		// 	throw new Exception("List sizes do not match");
		// }

		// double sumOfSquared = 0.0;
		// for(int i=0; i < p1.size(); i++) {
		// 	sumOfSquared += Math.pow( p1.get(i)-p2.get(i), 2 );
		// }
		// return Math.sqrt(sumOfSquared);		

		double sumOfSquared = IntStream.range(0, p1.size()).mapToDouble(i -> p1.get(i)-p2.get(i)).map(n -> n*n).sum();
        return Math.sqrt(sumOfSquared); 
	}
}
