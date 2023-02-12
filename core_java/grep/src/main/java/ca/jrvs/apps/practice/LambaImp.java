package ca.jrvs.apps.practice;

import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambaImp implements LambdaSteamExc{

  public Stream<String> createStrStream(String... strings) {
    Stream<String> stream = Stream.of(strings);
    return stream;
  }

  public Stream<String> toUpperCase(String... strings) {
    Stream<String> stream = Stream.of(strings);
    stream.map(string -> string.toUpperCase());
    return stream;
  }

  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    Pattern patternP = Pattern.compile(pattern);

    return stringStream.filter(patternP.asPredicate());
  }

  public IntStream createIntStream(int[] arr) {
    IntStream stream = IntStream.of(arr);
    return stream;

  }

  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  public List<Integer> toList(IntStream intStream) {
    return intStream.boxed().collect(Collectors.toList());
  }

  public IntStream createIntStream(int start, int end) {
    return IntStream.range(start,end);
  }

  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(n -> n%2!=0);
  }

  public void printMessages(String[] messages, Consumer<String> printer) {

  }

}
