package cc.kevinlee.modernjava.e08_03_stream_03;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * @author Kevin Lee
 * @since 2015-08-22
 */
public class StreamExamples3 {
    //Intermediate Operation Method
    // 중간 단계 연산
    // Stream을 리턴하기 때문에 계속 Method Channing을 통해서 무엇을 해야할지 스트림에게 지시가 가능하다.
    // filter, map, ,
    //Terminal Operation method
    // 최종 연산
    //
    // findFirst, Collect,

    public static void main(String[] args) {
        System.out.println("collect(toList()) : " +
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(toList())
        );

        System.out.println("collect(toSet()) : " +
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(toSet())
        );

        System.out.println("collect(joining()) : " +
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(joining())
        );

        System.out.println("collect(joining(\", \")) : " +
                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(joining(", "))
        );

        System.out.println("collect(joining(\", \", \"[\", \"]\")) : " +

                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .collect(joining(", ", "[", "]")) //[#6, #6, #10, #10]
        );

        System.out.println("distinct().collect(joining(\", \", \"[\", \"]\")) : " +

                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .distinct()
                        .collect(joining(", ", "[", "]"))
        );

        System.out.println("distinct().collect(toList()) : " +

                Stream.of(1, 3, 3, 5, 5)
                        .filter(i -> i > 2)
                        .map(i -> i * 2)
                        .map(i -> "#" + i)
                        .distinct()
                        .collect(toList()) //[#6, #10]
        );

        // primitive 타입은 generic 지원하지 않음
        // int 로 보이지만, AutoBoxing이 일어나서 integer로 바뀐다.
        // 3이 int(primitive) 이기 때문에, primitive로 비교한다.
        /*System.out.println(
                Stream.of(1,2,3,4,5)
                        .filter(i -> i == 3)
                        .findFirst()
        );*/


        // Equality가 아니라 Identity를 확인
        // Equality : equals()를 이용해 확인
        // Identity : == 이용해 메모리 레퍼런스 확인

        // Java의 AutoBoxing UnBoxing은 Integer.valueOf(i)메소드는 Integer 를 리턴
        final Integer integer3 = 3;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5)
                        .filter(i -> i == integer3)
                        .findFirst()
        );

        //동일 메모리 레퍼런스, 캐쉬가 되기 때문에
        final Integer integer127 = 127;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 127)
                        .filter(i -> i == integer127)
                        .findFirst()
        );

        // 다른 메모리 레퍼런스
        final Integer integer128 = 128;
        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i == integer128)
                        .findFirst()
        );

        System.out.println(
                Stream.of(1, 2, 3, 4, 5, 128)
                        .filter(i -> i.equals(integer128))
                        .findFirst()
        );

        // 등호는 primitive에만 적용되기 때문에, Auto Unboxing이 일어난다.
        System.out.println(".filter(i -> i > integer3).count() : " +
                Stream.of(1, 2, 3, 4, 5)
                        .filter(i -> i > integer3)
                        .count()
        );

        final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println("for (Integer i : numbers): ");
        //Extract Iterator
        for (Integer i : numbers) {
            System.out.print("i = " + i + " ");
        }

        //Stream 은 Internal Iterator
        System.out.println("forEach(i -> System.out.println(i)) : ");
        Stream.of(1, 2, 3, 4, 5)
                .forEach(i -> System.out.print(i + " "));
    }
}
