package frc.core.util.function;

import java.util.function.BiConsumer;

public class For {
  public static <T> void forWithCounter(Iterable<T> source, BiConsumer<Integer, T> consumer) {
    int i = 0;
    for (T item : source) {
      consumer.accept(i, item);
      i++;
    }
  }
}