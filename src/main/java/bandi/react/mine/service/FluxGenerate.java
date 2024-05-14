package bandi.react.mine.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

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
    Synchronous generate
    main thread execute
    */

    public static void main(String[] args) throws Exception {
        System.out.println("start");
//        new FluxGenerate().generate01();
        new FluxGenerate().generate02();
        System.out.println("end");

        /*while(true) {
            Thread.sleep(5000);
        }*/
    }

    public void generate01() throws Exception {
        Integer[] number = new Integer[]{0};

        /*
        Programmatically create a Flux by generating signals one-by-one via a consumer callback and some state.
        consumer 콜백 및 일부 상태를 사용하여 신호를 하나씩 생성하여 Flux를 프로그래밍 방식으로 생성합니다.
        */
        Flux.generate(m -> {
            m.next(number[0]++);

            if (number[0] == 10) {
                m.complete();
            }

            if (number[0] == 9) {
                m.error(new Exception());
            }

            this.sleep(200l);
        })
//        .log()
        .subscribe(m -> log.info("next : {}", m));
    }

    public void generate02() throws Exception {
        Flux.generate(
                        () -> 0,
                        (state, sink) -> {
                            try {
                                Thread.sleep(100l);
                            } catch (Exception e) {}
                            sink.next("3 x " + state + " = " + 3*state);
                            if (state == 10)
                                sink.complete();
                            return state + 1;
                        }).log()
                .subscribe();
    }

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
