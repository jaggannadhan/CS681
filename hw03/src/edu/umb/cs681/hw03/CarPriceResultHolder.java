package edu.umb.cs681.hw03;

import java.util.LinkedList;

public class CarPriceResultHolder {
	private int numCarExamined; 
    private double average;

    public CarPriceResultHolder() {
        this.numCarExamined = 0;
        this.average = 0;
    }

    public int getNumCarExamined() {
        return(this.numCarExamined);
    }

    public void setNumCarExamined(int numCarExamined) {
        this.numCarExamined = numCarExamined;
    }

    public double getAverage() {
        return this.average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public static void main(String[] args) {
        LinkedList<Car> cars = new LinkedList<>();
		Car.addCars(cars);
        CarPriceResultHolder resultHolder = new CarPriceResultHolder();

        double average = cars.stream().map( (Car car) -> car.getPrice() ).reduce(resultHolder, (subVal, curVal) -> {
            int carsExamined = subVal.getNumCarExamined();
            double curAvg = (subVal.average*carsExamined+curVal) / (carsExamined+1);

            subVal.setNumCarExamined(carsExamined + 1);
            subVal.setAverage( curAvg );
            return subVal;
        }, (finalResult, intermediateResult)->finalResult).getAverage();

        System.out.println("Average price: "+ average);
        System.out.println("Average price in result holder: "+resultHolder.getAverage());
        System.out.println("Number of cars examined in result holder: "+resultHolder.getNumCarExamined());
    }
}
