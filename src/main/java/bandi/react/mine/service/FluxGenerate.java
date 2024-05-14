package bandi.react.mine.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.math.BigInteger;

/*
    4.4. Programmatically creating a sequence
    In this section, we introduce the creation of a Flux or a Mono by programmatically defining its associated events
    (onNext, onError, and onComplete).
    All these methods share the fact that they expose an API to trigger the events that we call a sink.
    There are actually a few sink variants, which we’ll get to shortly.
*/
@Slf4j
public class FluxGenerate {

    /*
    The simplest form of programmatic creation of a Flux is through the generate method
     > Flux를 프로그래밍 방식으로 가장 간단하게 생성하는 메서드
    Synchronous generate
    main thread execute
    */

    public static void main(String[] args) throws Exception {
        System.out.println("start");
//        new FluxGenerate().generate01();
//        new FluxGenerate().generate02();
        new FluxGenerate().generate02obj();
        System.out.println("end");

        /*while(true) {
            Thread.sleep(5000);
        }*/
    }

    public void generate01() throws Exception {
        /*
        Programmatically create a Flux by generating signals one-by-one via a consumer callback.
         > consumer 콜백을 통해 한 번에 한 개씩 신호를 생성하여 Flux를 프로그래밍 방식으로 생성합니다.

        params
            Consumer<SynchronousSink<T>> generator
        */
        Integer[] number = new Integer[]{0};
        Flux.generate(m -> {
            m.next(number[0]++);

            if (number[0] == 10) {
                m.complete();
            }

            if (number[0] == 9) {
                m.error(new RuntimeException());
            }

            this.sleep(1000l);
        })
        .log()
        .subscribe();
    }

    public void generate02() throws Exception {
        /*
        Programmatically create a Flux by generating signals one-by-one via a consumer callback and some state.
         > 콜백(callback) 및 상태(state)를 사용하여 신호를 하나씩 생성하여 Flux를 프로그래밍 방식으로 생성한다.

        params
            Callable<S> stateSupplier,
            BiFunction<S,SynchronousSink<T>,S> generator
        */

        // fibonacci 출력
        Long[] fibonacci = new Long[]{0l};
        Flux.generate(
            () -> 1l, // 초기시작값 정의
            (state, sink) -> {
                this.sleep(1000l);

                sink.next(state);

                long sum = fibonacci[0] + state;
                fibonacci[0] = state;
                return sum;
            })
        .log()
        .subscribe();
    }

    private BigInteger fibonacci = new BigInteger("0");
    public void generate02obj() throws Exception {
        // fibonacci 출력
        Flux.generate(
                        () -> new BigInteger("1"), // 초기시작값 정의
                        (state, sink) -> {
                            this.sleep(1000l);

                            sink.next(state.toString());

                            BigInteger sum = new BigInteger(fibonacci.toString()).add(state);
                            fibonacci = state;
                            return sum;
                        })
                .log()
                .subscribe();
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
