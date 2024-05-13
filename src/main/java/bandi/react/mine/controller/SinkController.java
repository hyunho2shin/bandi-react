package bandi.react.mine.controller;

import bandi.react.mine.service.SinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class SinkController {

    private SinkService sinkService;

    // @Autowired > 생성자가 하나만 있을경우에 생략가능
    public SinkController(SinkService sinkService) {
        this.sinkService = sinkService;
    }

    @GetMapping("/events")
    public Flux<ServerSentEvent<String>> events() {
        return sinkService.sinkManyUnicast();
    }
}
