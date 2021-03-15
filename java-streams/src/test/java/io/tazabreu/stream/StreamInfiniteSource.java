package io.tazabreu.stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

public class StreamInfiniteSource {

    Logger logger = LoggerFactory.getLogger(StreamInfiniteSource.class);

    @Test
    public void limitingInfiniteGenerationExample() {
        List<Double> limitedNumbers = Stream.generate(Math::random)
                .limit(5)
                .peek(number -> logger.info(number.toString()))
                .collect(Collectors.toList());

        assertThat(limitedNumbers.size(), Matchers.is(5));
    }

    @Test
    public void iterateExample() {
        Stream<Integer> evenNumStream = Stream.iterate(2, i -> i * 2);

        List<Integer> collect = evenNumStream
                .limit(5)
                .collect(Collectors.toList());

        assertThat(collect, Matchers.containsInRelativeOrder(2, 4, 8, 16, 32));
    }
}
