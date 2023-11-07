package edu.umb.cs681.hw02;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Random;
import java.time.Period;

public class Person {
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private List<Dose> doses;

	public Person(String firstName, String lastName, LocalDate dob, List<Dose> doses) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.doses = doses;
	}

	public String getName() {
		return this.firstName + this.lastName;
	}

	int calculateAge(LocalDate dob) {  
		LocalDate curDate = LocalDate.now();  
		if( (dob == null) || (curDate == null)) return 0;
		if ((dob != null) && (curDate != null)) {  
			return Period.between(dob, curDate).getYears();  
		} else {  
			return 0;  
		}  
	}

	public int getAge() {
		return this.calculateAge(this.dob);
	}

	public AgeCat getAgeCat() {
		int age = calculateAge(this.dob);
		if(age <= 30) return AgeCat.YOUNG;
		else if(age > 30 && age <= 45) return AgeCat.MID;
		else return AgeCat.OLD;
	}

	public void setDoses(LinkedList<Dose> doses) {
		this.doses = doses;
	}

	public List<Dose> getDoses() {
		return this.doses;
	}

	public int getVacCount() {
		return this.doses.size();
	}

	public static void getVaccRateByCategory(LinkedList<Person> people) {
		Map<AgeCat, List<Person>> groupedByCat = people.stream().collect(Collectors.groupingBy((Person p) -> p.getAgeCat()));
		List<Person> oldPeople = groupedByCat.get(AgeCat.OLD);
		System.out.println("Number of OLD people: "+oldPeople.size() );

		List<Person> youngPeople = groupedByCat.get(AgeCat.YOUNG);
		System.out.println("Number of YOUNG people: "+ youngPeople.size() );

		List<Person> middleAggedPeople = groupedByCat.get(AgeCat.MID);
		System.out.println("Number of MID-aged people: "+middleAggedPeople.size() +"\n\n");

		long vacCountOfOldPeople = oldPeople.stream().filter((Person p) -> p.getVacCount() >= 3).count();
		float vacRateOfOldPeople = (float)vacCountOfOldPeople/people.size() * 100;
		System.out.println("Fully vaccinated rate of OLD people: "+vacRateOfOldPeople);

		long vacCountOfYoungPeople = youngPeople.stream().filter((Person p) -> p.getVacCount() >= 3).count();
		float vacRateOfYoungPeople = (float)vacCountOfYoungPeople/people.size() * 100;
		System.out.println("Fully vaccinated rate of YOUNG people: "+vacRateOfYoungPeople);

		long vacCountOfMidAgedPeople = middleAggedPeople.stream().filter((Person p) -> p.getVacCount() >= 3).count();
		float vacRateOfMidAgedPeople = (float)vacCountOfMidAgedPeople/people.size() * 100;
		System.out.println("Fully vaccinated rate of MID-aged people: "+vacRateOfMidAgedPeople+"\n\n");
	}

	public static void getAvgVaccByCategory(LinkedList<Person> people) {
		Map<AgeCat, List<Person>> groupedByCat = people.stream().collect(Collectors.groupingBy((Person p) -> p.getAgeCat()));
		List<Person> oldPeople = groupedByCat.get(AgeCat.OLD);
		List<Person> youngPeople = groupedByCat.get(AgeCat.YOUNG);
		List<Person> middleAggedPeople = groupedByCat.get(AgeCat.MID);

		int avgInOldGroup = (int)oldPeople.stream().mapToDouble((Person p) -> p.getVacCount()).average().getAsDouble();
		System.out.println("Avg vaccination rate in OLD group: "+ avgInOldGroup);

		int avgInYoungGroup = (int)youngPeople.stream().mapToDouble((Person p) -> p.getVacCount()).average().getAsDouble();
		System.out.println("Avg vaccination rate in YOUNG group: "+ avgInYoungGroup);

		int avgInMidAgedGroup = (int)middleAggedPeople.stream().mapToDouble((Person p) -> p.getVacCount()).average().getAsDouble();
		System.out.println("Avg vaccination rate in MID-aged group: "+ avgInMidAgedGroup+"\n\n");

	}

	public static void getAvgAgeOfNotVacc(LinkedList<Person> people) {
		Map<Boolean, List<Person>> vaccinationPartition = people.stream().collect(Collectors.partitioningBy((Person p) -> p.getVacCount() == 0));
		List<Person> peopleNeverVaccinated = vaccinationPartition.get(true);
		int avgInNeverVaccinated = (int)peopleNeverVaccinated.stream().mapToDouble((Person p) -> p.getAge()).average().getAsDouble();
		System.out.println("Avg age in people not vaccinated: "+ avgInNeverVaccinated);
	}

	public static List<Dose> getVac() {
		List<Dose> doses = new LinkedList<>();

		doses.add(new Dose("hepB", "1121", LocalDate.parse("2020-03-01"), "Boston"));
		doses.add(new Dose("Corona", "1122", LocalDate.parse("2021-05-01"), "California"));
		doses.add(new Dose("CommonFlu", "1123", LocalDate.parse("2019-07-01"), "Houston"));
		doses.add(new Dose("Influenza", "1124", LocalDate.parse("2023-03-01"), "Providence"));
		doses.add(new Dose("Tetanus", "1125", LocalDate.parse("2022-03-01"), "Torronto"));
		doses.add(new Dose("Polio", "1126", LocalDate.parse("2018-03-01"), "Seattle"));
		doses.add(new Dose("hepA", "1127", LocalDate.parse("2015-03-01"), "DC"));

		return doses;
	}

	public static void addPeople(LinkedList<Person> people) {
		List<Dose> doses = getVac();
		String[] names = { "John", "Constantine", "Jessica", "Alba", "Olivia", "Rodrigo", "Dua", "Lipa", "Christiano", 
		"Ronaldo", "Mathew", "Perry", "Chuck", "Lore", "David", "Bekkham", "Leonardo", "Dicaprio", "Taylor", "Swift", "Christina", 
		"Perri", "Christ", "Hemsworth", "Scarlett", "Johanson", "Ana", "De Armas", "Selina", "Gomez", "Lupin", "Diaz", "James", "Taylor" };

		for(int i=0; i < 1000; i++) {
			Random rand = new Random();

			String firstName = Arrays.asList(names).get(rand.nextInt(34));
			String lastName = Arrays.asList(names).get(rand.nextInt(34));
			int year = rand.nextInt(2023-1950+1) + 1950;
			String month = String.valueOf(rand.nextInt(12-1+1) + 1);
			String day = String.valueOf(rand.nextInt(28-1+1) + 1);

			if(Integer.valueOf(month) < 10) 
				month = "0"+month;
			if(Integer.valueOf(day) < 10) 
				day = "0"+day;

			String date = year+"-"+month+"-"+day;

			int doseCount = rand.nextInt(7);
			List<Dose> myDose = new LinkedList<Dose>();

			for(int j=0; j<doseCount; j++) {
				myDose.add(doses.get(rand.nextInt(6)));
			}
			
			people.add(new Person(firstName, lastName, LocalDate.parse(date), myDose));
		}
	}

	public static void main(String args[]) {
		LinkedList<Person> people = new LinkedList<Person>();		

		addPeople(people);
		System.out.println("People size: "+people.size());
		getVaccRateByCategory(people);
		
		people = new LinkedList<Person>();	;
		addPeople(people);
		System.out.println("People size: "+people.size());
		getAvgVaccByCategory(people);

		people = new LinkedList<Person>();	;
		addPeople(people);
		System.out.println("People size: "+people.size());
		getAvgAgeOfNotVacc(people);
	}
}
