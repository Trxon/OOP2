package teorijske_vezbe.tv03_izvori_finalne_operacije;

import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Redukcije {

	public static void main(String[] args) {

//		System.out.println();
//		System.out.println("forEach()");
//		IntStream.range(0, 10)
//			.forEach(System.out::println);

//		System.out.println();
//		System.out.println("xxxxxMatch()");
//		boolean b1 = IntStream.range(0, 10)
//			.allMatch(i -> i < 5);
//		System.out.println(b1);
//		boolean b2 = IntStream.range(0, 10)
//			.anyMatch(i -> i < 5);
//		System.out.println(b2);
//		boolean b3 = IntStream.range(0, 10)
//			.noneMatch(i -> i < 5);
//		System.out.println(b3);

//		System.out.println();
//		System.out.println("findXxxxx()");
//		Optional<String> s1 = Stream.of("Pera", "Mika", "Djoka", "Ana", "Maja")
//			.findFirst();
//		System.out.println(s1);
//		Optional<String> s2 = Stream.of("Pera", "Mika", "Djoka", "Ana", "Maja")
//			.findAny();
//		System.out.println(s2);
//		Optional<String> s3 = Stream.of("Pera", "Mika", "Djoka", "Ana", "Maja")
//			.min(Comparator.comparing(String::length));
//		System.out.println(s3);
//		Optional<String> s4 = Stream.of("Pera", "Mika", "Djoka", "Ana", "Maja")
//			.max(Comparator.comparing(String::length));
//		System.out.println(s4);
//
//		System.out.println();
//		System.out.println("count(), sum(), average()...");
//		long br = IntStream.range(0, 10)
//			.count();
//		System.out.println(br);
//		long suma = IntStream.range(0, 10)
//			.sum();
//		System.out.println(suma);
//		OptionalDouble prosek = IntStream.range(0, 10)
//			.average();
//		System.out.println(prosek);
//		IntSummaryStatistics stat = IntStream.range(0, 10)
//			.summaryStatistics();
//		System.out.println(stat);

//		System.out.println();
//		System.out.println("toArray() & collect()");
//		int[] a1 = IntStream.range(0, 10)
//			.toArray();
//		System.out.println(Arrays.toString(a1));
//		Object[] a2 = Stream.of(LocalDate.now(), "ABC", 17)
//			.toArray();
//		System.out.println(Arrays.toString(a2));
//		String[] a3 = Stream.of("a", "b", "c")
//			.toArray(n -> new String[n]);
//		System.out.println(Arrays.toString(a3));
//		List<Object> l1 = Stream.of(LocalDate.now(), "ABC", 17)
//			.collect(Collectors.toList());
//		System.out.println(l1);
//		List<String> l2 = Stream.of("a", "b", "c")
//			.collect(Collectors.toList());
//		System.out.println(l2);

		// Collectors.counting();
		// Collectors.summingDouble(mapper);
		// Collectors.summingInt(mapper);
		// Collectors.summingLong(mapper);
		// Collectors.averagingDouble(mapper);
		// Collectors.averagingInt(mapper);
		// Collectors.averagingLong(mapper);
		// Collectors.summarizingDouble(mapper);
		// Collectors.summarizingInt(mapper);
		// Collectors.summarizingLong(mapper);
		// Collectors.minBy(comparator);
		// Collectors.maxBy(comparator);

		// Collectors.toList();
		// Collectors.toSet();
		// Collectors.toCollection(collectionFactory);
		// Collectors.toMap(keyMapper, valueMapper);
		// Collectors.toMap(keyMapper, valueMapper, mergeFunction);
		// Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapFactory);
		// Collectors.toUnmodifiableList();
		// Collectors.toUnmodifiableSet();
		// Collectors.toUnmodifiableMap(keyMapper, valueMapper);
		// Collectors.toUnmodifiableMap(keyMapper, valueMapper, mergeFunction);

		// Collectors.joining();
		// Collectors.joining(delimiter);
		// Collectors.joining(delimiter, prefix, suffix);

		// Collectors.filtering(predicate, downstream);
		// Collectors.mapping(mapper, downstream);
		// Collectors.flatMapping(mapper, downstream);

		// Collectors.groupingBy(classifier);
		// Collectors.groupingBy(classifier, downstream);
		// Collectors.groupingBy(classifier, mapFactory, downstream);
		// Collectors.partitioningBy(predicate);
		// Collectors.partitioningBy(predicate, downstream);

		// Collectors.reducing(op);
		// Collectors.reducing(identity, op);
		// Collectors.reducing(identity, mapper, op);

		// Collectors.collectingAndThen(downstream, finisher);

		// Collector.of(supplier, accumulator, combiner, characteristics)
		// Collector.of(supplier, accumulator, combiner, finisher, characteristics)

//		System.out.println();
//		System.out.println("reduce()");
//		OptionalInt x1 = IntStream.range(0, 10)
//			.reduce((a, b) -> a + b);
//		System.out.println(x1);
//		int x2 = IntStream.range(0, 10)
//			.reduce(100, (a, b) -> a + b);
//		System.out.println(x2);

	}
}
