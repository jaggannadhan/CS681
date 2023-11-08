package edu.umb.cs681.hw04;
import java.util.List;

public interface DistanceMetric {
	 public abstract double distance(List<Double> p1, List<Double> p2) throws Exception;
}
