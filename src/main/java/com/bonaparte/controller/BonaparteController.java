package com.bonaparte.controller;


import com.bonaparte.bean.MemberEntity;
import com.bonaparte.service.BonaparteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/basewebflux")
public class BonaparteController {
    @Autowired
    BonaparteService bonaparteService;

    @RequestMapping("/helloworld")
    public Mono<String> saveHelloWorld(){
        return Mono.just("Hello World");
    }

    @RequestMapping("/sse")
    public Flux<ServerSentEvent<Integer>> randomNumbers(){
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<Integer>builder().event("random")
                .id(Long.toString(data.getT1()))
                .data(data.getT2())
                .build());
    }

    @RequestMapping("/add/")
    public Flux<MemberEntity> add(){
        for (int i = 100000; i < 100033; i++) {
            MemberEntity memberEntity = new MemberEntity();
            memberEntity.setPhone(i+"");
            memberEntity.setAddress("成都");
            memberEntity.setName(i+"");
            bonaparteService.createOrUpdate(memberEntity);
        }
        return bonaparteService.getAll();
    }

    @RequestMapping("/list")
    public Flux<MemberEntity> list(){
        return bonaparteService.getAll();
    }
}
