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

import entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre com o caminho do arquivo: ");
		String path = sc.nextLine();
		List<Sale> sales = null;
		
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			sales = new ArrayList<>();
			
			String line = br.readLine();
			while(line != null) {
				String[] fields = line.split(",");
				sales.add(new Sale(Integer.parseInt(fields[0]),
						Integer.parseInt(fields[1]),
						fields[2],
						Integer.parseInt(fields[3]),
						Double.parseDouble(fields[4])
						));
				line = br.readLine();
			}
			
		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
			System.exit(1);
		}
		sc.close();
		
		Comparator<Sale> comp = (s1, s2) -> s1.averagePrice().compareTo(s2.averagePrice());
		
		List<Sale> sortedSales = sales.stream()
				.filter(p -> p.getYear() == 2016)
				.map(p -> p).sorted(comp.reversed())
				.limit(5)
				.collect(Collectors.toList());
		
		Double loganTotal = sales.stream()
				.filter(p -> p.getSeller().equals("Logan")
				&& ( p.getMonth() == 1 || p.getMonth() == 7))
				.map(p -> p.getTotal())
				.reduce(0.0, (x,y) -> x + y);
				
		System.out.println("Cinco primeiras vendas de 2016 de maior preço médio");
		sortedSales.forEach(System.out::println);
		System.out.println();
		System.out.println("Valor total vendido pelo vendedor Logan nos meses 1 e 7 = " + String.format("%.2f", loganTotal));		
	}

}
