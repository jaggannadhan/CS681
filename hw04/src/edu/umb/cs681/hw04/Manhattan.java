package edu.umb.cs681.hw04;
import java.util.List;
import java.util.stream.IntStream;

public class Manhattan implements DistanceMetric {
    public double distance(List<Double> p1, List<Double> p2) throws Exception{
		// if(p1.size() != p2.size()) {
		// 	throw new Exception("List sizes do not match");
		// }

		// double sumOfSquared = 0.0;
		// for(int i=0; i < p1.size(); i++) {
		// 	sumOfSquared += (p1.get(i) - p2.get(i) );
		// }
		// return sumOfSquared;		

		double manhattanDifference = IntStream.range(0, p1.size()).mapToDouble(i -> p1.get(i)-p2.get(i)).map(n->Math.abs(n)).sum();
        return manhattanDifference; 
	}
}
