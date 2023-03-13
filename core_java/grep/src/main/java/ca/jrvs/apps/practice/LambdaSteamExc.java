package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaSteamExc {

  Stream<String> createStrStream(String ... strings);

  Stream<String> toUpperCase(String ... strings);

  Stream<String> filter(Stream<String> stringStream, String pattern);

  IntStream createIntStream(int[] arr);

  <E> List<E> toList(Stream<E> stream);

  List<Integer> toList(IntStream intStream);

  IntStream createIntStream(int start, int end);

  IntStream getOdd(IntStream intStream);

  void printMessages(String[] messages, Consumer<String> printer);

}


