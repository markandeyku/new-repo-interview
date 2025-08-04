package test.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamApiOperation {
    public static void main(String[] args) throws IOException {
        /**
         * 1. Stream creation
         * 2. Intermediate Operation
         * 3. Terminal Operation
         */

        /**
         * All creation operations are:
         * Arrays, collections, generators, empty, range   , iterate, of, stream, ofNullable
         *
         */

        //using Stream.of():
        Stream<Integer> stream = Stream.of(1,2,3,4,5);
        stream.forEach(System.out::println);

        //Arrays.stream():

        Stream<Integer> stream1 = Stream.of(new Integer[]{1,2,3,4,5});
        stream1.forEach(System.out::println);

        //Stream.iterate():

        Stream<Integer> stream2 = Stream.iterate(5, i->i+5);
        stream2.limit(10).forEach(System.out::println);

        //Stream.generate():

        Stream<Double> stream3 = Stream.generate(Math::random);
        stream3.limit(2).forEach(System.out::println);


        //Files.lines():
        Path path = Paths.get("/Users/mark123/IdeaProjects/markandeykumar/src/test/stream/streamcreation");

        Stream<String> s = Files.lines(path);

        s.forEach(System.out::println);

        //Files.lines().parallel():
         Stream<String> streaqm = Files.lines(path).parallel();

        streaqm.forEach(System.out::println);

        //Files.walk():

        Stream<Path> streaadwem = Files.walk(path);

        streaadwem.forEach(System.out::println);
    }
}
