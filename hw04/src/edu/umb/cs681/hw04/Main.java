package edu.umb.cs681.hw04;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    
    public static void revisedEuclidian(List<List<Double>> points) throws Exception{
        List<List<Double>> euclidian = Distance.matrix(points);
        System.out.println(euclidian);
    }

    public static void revisedManhattan(List<List<Double>> points) throws Exception {
        List<List<Double>> manhattan = Distance.matrix(points, new Manhattan());
        System.out.println(manhattan);
    }

    public static List<List<Double>> getPoints() {
        Random rand = new Random();
        List<List<Double>> points = new ArrayList<>();

        for(int i=0; i<1000; i++) {
            List<Double> dimension = new ArrayList<>(); 
            for(int j=0; j<100; j++) {
                dimension.add(rand.nextDouble());
            }
            points.add(dimension);
        }

        return points;
    }

    public static void main(String[] args) {
        List<List<Double>> points = getPoints();

        try {
            revisedEuclidian(points);
            revisedManhattan(points);
        } catch(Exception e) {
            System.out.println(e);
        }
        
    }
}

