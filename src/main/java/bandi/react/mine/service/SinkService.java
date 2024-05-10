package bandi.react.mine.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
@Service
public class SinkService implements InitializingBean {
//    private List<Integer> numberList = Lists.newArrayList();

    Sinks.Many<String> unicastSink;
    public Flux<String> fluxView;

    public static void main(String[] args) throws Exception {
        new SinkService().afterPropertiesSet();
    }

    @GetMapping("/events")
    public Flux<ServerSentEvent<String>> events() {
        return fluxView.map(event -> ServerSentEvent.builder(event).build());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        this.fluxView = unicastSink.asFlux();

        this.simulateEvents();

        /*
        Integer[] number = new Integer[] {0, 1};
        Flux.interval(Duration.ofSeconds(5))
                .doOnNext(n -> {
                    int result = number[0] + number[1];
                    numberList.add(result);

                    number[0] = number[1];
                    number[1] = result;
                }).subscribe();
        */

        /*
        while(true) {
            Thread.sleep(5000);
            log.info("numberList : {}", numberList);
        }*/
    }

    // 이벤트 시뮬레이션 메서드
    private void simulateEvents() {
        Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> "Event " + sequence)
                .subscribe(this.unicastSink::tryEmitNext);
    }
}
