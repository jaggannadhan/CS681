package edu.umb.cs681.hw01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Car {
	private String make, model; 
	private int year, mileage, dominationCount;
	private float price;

	public Car(String make, String model, int mileage, int year, float price) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;	
		this.price = price;	
		this.dominationCount = 0;	
	}

	public int getMileage() {
		return this.mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String[] carToStringArray() {
		return new String[] {this.make, this.model, Integer.toString(this.year), String.valueOf(this.price)};
	}

	public static String carObjectToString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (Object obj : arr)
			sb.append(obj.toString()).append(",");
		return sb.substring(0, sb.length() - 1);
	}

	public int getDominationCount() {
		return this.dominationCount;
	}
	
	public void setDominationCount(LinkedList<Car> usedCars) {
		for (Car car : usedCars) {
			if(this.getMileage() >= car.getMileage() && this.getPrice() <= car.getPrice() && this.getYear() <= car.getYear()) {
				if(this.getMileage() > car.getMileage() || this.getPrice() < car.getPrice() || this.getYear() > car.getYear()) {
					this.dominationCount++;
				}
			}
		}
	}

	public static void priceComparator(LinkedList<Car> usedCars) {
		// Sorting cars based on price
		LinkedList<Car> sortedCars = new LinkedList<>(usedCars);
		Collections.sort(sortedCars, Comparator.comparing((Car car)-> car.getPrice()));
		String sortedByPrice = sortedCars.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println("Cars sorted by price: \n"+ sortedByPrice + "\n\n");

		// Separating cars to High and Low groups based on price threshold $50000
		Map<Boolean, List<Car>> priceGroup = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getPrice() > 50000));
		
		LinkedList<Car> highGroup = new LinkedList<>(priceGroup.get(true));
		String highPriceGroup = highGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>High priced cars<<<< \n"+ highPriceGroup+"\n");

		LinkedList<Car> lowGroup = new LinkedList<>(priceGroup.get(false));
		String lowPriceGroup = lowGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>Low priced cars<<<< \n"+ lowPriceGroup+ "\n\n");
		
		// Getting the Avg, highest and lowest values in High group
		double avgInHighGroup = highGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).average().getAsDouble();
		System.out.println("Average price in high group: "+ avgInHighGroup);

		double lowInHighGroup = highGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).min().getAsDouble();
		System.out.println("Lowest price in high group: "+ lowInHighGroup);

		double highInHighGroup = highGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).max().getAsDouble();
		System.out.println("Highest price in high group: "+ highInHighGroup+ "\n\n");

		// Getting the Avg, highest and lowest values in Low group
		double avgInLowGroup = lowGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).average().getAsDouble();
		System.out.println("Average price in low group: "+ avgInLowGroup);

		double lowInLowGroup = lowGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).min().getAsDouble();
		System.out.println("Lowest price in low group: "+ lowInLowGroup);

		double highInLowGroup = lowGroup.stream().mapToDouble((Car car) -> (double) car.getPrice()).max().getAsDouble();
		System.out.println("Highest price in low group: "+ highInLowGroup+ "\n\n");

		// Number of cars in High and Low group
		Map<Boolean, Long> priceGroupCount = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getPrice() > 50000, Collectors.counting()));
		Long highGroupCount = priceGroupCount.get(true);
		Long lowGroupCount = priceGroupCount.get(false);

		System.out.println("High price group count: "+ highGroupCount);
		System.out.println("Low price group count: "+ lowGroupCount+"\n\n");
	}

	public static void yearComparator(LinkedList<Car> usedCars) {
		// Sort cars based on year
		LinkedList<Car> sortedCars = new LinkedList<>(usedCars);
		Collections.sort(sortedCars, Comparator.comparing((Car car)-> car.getYear()));
		String sortedByYear = sortedCars.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println("Cars sorted by year: \n"+ sortedByYear + "\n\n");

		// Separating cars to High and Low groups based on year 2001 as threshold 
		Map<Boolean, List<Car>> yearGroup = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getYear() > 2005));
		
		LinkedList<Car> highGroup = new LinkedList<>(yearGroup.get(true));
		String latestGroup = highGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>Latest cars<<<< \n"+ latestGroup+"\n");

		LinkedList<Car> lowGroup = new LinkedList<>(yearGroup.get(false));
		String oldGroup = lowGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>Old cars<<<< \n"+ oldGroup+ "\n\n");


		// Getting the Avg, highest and lowest values in High group
		int avgInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getYear()).average().getAsDouble();
		System.out.println("Mid-released car in high group: "+ avgInHighGroup);

		int lowInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getYear()).min().getAsDouble();
		System.out.println("Oldest car in high group: "+ lowInHighGroup);

		int highInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getYear()).max().getAsDouble();
		System.out.println("Latest car in high group: "+ highInHighGroup+ "\n\n");

		// Getting the Avg, highest and lowest values in Low group
		int avgInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getYear()).average().getAsDouble();
		System.out.println("Mid-released car in low group: "+ avgInLowGroup);

		int lowInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getYear()).min().getAsDouble();
		System.out.println("Oldest car in low group: "+ lowInLowGroup);

		int highInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getYear()).max().getAsDouble();
		System.out.println("Latest car in in low group: "+ highInLowGroup+ "\n\n");

		// Number of cars in High and Low group
		Map<Boolean, Long> priceGroupCount = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getYear() > 2005, Collectors.counting()));
		Long highGroupCount = priceGroupCount.get(true);
		Long lowGroupCount = priceGroupCount.get(false);

		System.out.println("Latest car(high) group count: "+ highGroupCount);
		System.out.println("Oldest car(low) group count: "+ lowGroupCount+"\n\n");
		
	}

	public static void mileageComparator(LinkedList<Car> usedCars) {
		LinkedList<Car> sortedCars = new LinkedList<>(usedCars);
		Collections.sort(sortedCars, Comparator.comparing((Car car)-> car.getMileage()));
		String sortedByMileage = sortedCars.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println("Cars sorted by mileage: \n"+ sortedByMileage + "\n\n");

		// Separating cars to High and Low groups based on mileage 5 as threshold 
		Map<Boolean, List<Car>> yearGroup = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getMileage() > 5));
		
		LinkedList<Car> highGroup = new LinkedList<>(yearGroup.get(true));
		String highMileageGroup = highGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>High mileage cars<<<< \n"+ highMileageGroup+"\n");

		LinkedList<Car> lowGroup = new LinkedList<>(yearGroup.get(false));
		String lowMileageGroup = lowGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>Low mileage cars<<<< \n"+ lowMileageGroup+ "\n\n");


		// Getting the Avg, highest and lowest values in High group
		int avgInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getMileage()).average().getAsDouble();
		System.out.println("Avg mileage car in high group: "+ avgInHighGroup);

		int lowInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getMileage()).min().getAsDouble();
		System.out.println("Low mileage car in high group: "+ lowInHighGroup);

		int highInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getMileage()).max().getAsDouble();
		System.out.println("High mileage car in high group: "+ highInHighGroup+ "\n\n");

		// Getting the Avg, highest and lowest values in Low group
		int avgInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getMileage()).average().getAsDouble();
		System.out.println("Avg mileage car in low group: "+ avgInLowGroup);

		int lowInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getMileage()).min().getAsDouble();
		System.out.println("Low mileage car in low group: "+ lowInLowGroup);

		int highInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getMileage()).max().getAsDouble();
		System.out.println("High mileage car in in low group: "+ highInLowGroup+ "\n\n");

		// Number of cars in High and Low group
		Map<Boolean, Long> priceGroupCount = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getMileage() > 5, Collectors.counting()));
		Long highGroupCount = priceGroupCount.get(true);
		Long lowGroupCount = priceGroupCount.get(false);

		System.out.println("High mileage car group count: "+ highGroupCount);
		System.out.println("Low mileage car group count: "+ lowGroupCount+"\n\n");
	}

	public static void dominationCountComparator(LinkedList<Car> usedCars) {
		LinkedList<Car> sortedCars = new LinkedList<>(usedCars);
		Collections.sort(sortedCars, Comparator.comparing((Car car)-> car.getDominationCount()));
		String sortedDomiationCount = sortedCars.stream().map((Car car) -> carObjectToString(car.carToStringArray()) + "," + car.getDominationCount() ).collect(Collectors.joining("\n"));
		System.out.println("Cars sorted by domination count: \n"+ sortedDomiationCount + "\n\n");

		// Separating cars to High and Low groups based on domination count 3 as threshold 
		Map<Boolean, List<Car>> dominationGroup = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getDominationCount() > 3));
		
		LinkedList<Car> highGroup = new LinkedList<>(dominationGroup.get(true));
		String highDominationGroup = highGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>High Domination cars<<<< \n"+ highDominationGroup+"\n");

		LinkedList<Car> lowGroup = new LinkedList<>(dominationGroup.get(false));
		String lowDominationGroup = lowGroup.stream().map((Car car) -> carObjectToString(car.carToStringArray())).collect(Collectors.joining("\n"));
		System.out.println(">>>>Low Domination cars<<<< \n"+ lowDominationGroup+ "\n\n");


		// Getting the Avg, highest and lowest values in High group
		int avgInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).average().getAsDouble();
		System.out.println("Avg domination count of car in high group: "+ avgInHighGroup);

		int lowInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).min().getAsDouble();
		System.out.println("Low domination count of car in high group: "+ lowInHighGroup);

		int highInHighGroup = (int)highGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).max().getAsDouble();
		System.out.println("High domination count of car in high group: "+ highInHighGroup+ "\n\n");

		// Getting the Avg, highest and lowest values in Low group
		int avgInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).average().getAsDouble();
		System.out.println("Avg domination count of car in low group: "+ avgInLowGroup);

		int lowInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).min().getAsDouble();
		System.out.println("Low domination count of car in low group: "+ lowInLowGroup);

		int highInLowGroup = (int)lowGroup.stream().mapToDouble((Car car) -> car.getDominationCount()).max().getAsDouble();
		System.out.println("High domination count of car in in low group: "+ highInLowGroup+ "\n\n");

		// Number of cars in High and Low group
		Map<Boolean, Long> dominationGroupCount = usedCars.stream().collect(Collectors.partitioningBy( (Car car) -> car.getDominationCount() > 3, Collectors.counting()));
		Long highGroupCount = dominationGroupCount.get(true);
		Long lowGroupCount = dominationGroupCount.get(false);

		System.out.println("High domination car group count: "+ highGroupCount);
		System.out.println("Low domination car group count: "+ lowGroupCount+"\n\n");
	}

	public static void addCars(LinkedList<Car> cars) {
        cars.add(new Car("Maserati", "M32", 12, 2022, 250000.0f));
		cars.add(new Car("Corvette", "CM15", 4, 2020, 100000.0f));
		cars.add(new Car("Dodge", "Challeger", 2, 2019, 90000.0f));
		cars.add(new Car("Honda", "Civic", 6, 2012, 70000.0f));
		cars.add(new Car("Shelby", "Cobra", 5, 1989, 65000.0f));
		cars.add(new Car("Mini Cooper", "Convertible", 4, 2017, 45750.0f));
		cars.add(new Car("BMW", "8 Seies", 5, 2024, 90000.0f));
		cars.add(new Car("Ford", "Mustang GT", 3, 1987, 55000.0f));
		cars.add(new Car("Alfa Romeo", "4C", 7, 2020, 67150.0f));
		cars.add(new Car("Nissan", "Frontier", 11, 2016, 46610.0f));
		cars.add(new Car("GMC", "Canyon", 4, 2023, 36900.0f));
		cars.add(new Car("Honda", "Ridgeline", 8, 2023, 38800.0f));
		cars.add(new Car("Jeep", "Gladiator", 9, 2024, 37560.0f));
		cars.add(new Car("Dodge", "Caravan", 9, 1992, 47874.0f));
		cars.add(new Car("BMW", "5-Series", 9, 1996, 71290.0f));
		cars.add(new Car("Lamborghini", "Diablo", 9, 2001, 68200.0f));
	}

	public static void main(String args[]) {
		LinkedList<Car> cars = new LinkedList<>();
		addCars(cars);

		priceComparator(cars);
		yearComparator(cars);
		mileageComparator(cars);

		cars.forEach(car -> car.setDominationCount(cars));
		dominationCountComparator(cars);
	}
}
