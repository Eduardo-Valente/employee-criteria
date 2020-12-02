package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Employee;

/*
 * Program shows a list of emails and the sum of 
 * the employees salaries based on lambda expressions
 */

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		System.out.print("Enter salary: ");
		double salary = sc.nextDouble();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Employee> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2]) ) );
				line = br.readLine();
			}

			double sum = list.stream().filter(e-> e.getName().charAt(0) == 'M')
									  .map(p -> p.getSalary()).reduce(0.0, (x, y) -> x + y); 

			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

			List<String> emails = list.stream().filter(e -> e.getSalary() > salary).map(e -> e.getEmail())
					.sorted(comp).collect(Collectors.toList());

			System.out.printf("Email of people whose salary is more than %,.2f:%n", salary);
			emails.forEach(System.out::println);
			
			System.out.printf("Sum of salary of people whose name starts with 'M': %,.2f%n", sum);

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();

	}

}
