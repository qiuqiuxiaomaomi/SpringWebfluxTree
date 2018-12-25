package com.bonaparte.controller;

import com.bonaparte.bean.MemberEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/webclient")
public class WebClientController {

    @RequestMapping("/webclient1")
    public void webclient1(){
        WebClient webClient = WebClient.create("http://localhost:23000");
        Mono<String> resp = webClient.get()
                .uri("/basewebflux/helloworld")
                .retrieve()
                .bodyToMono(String.class);

        resp.subscribe(System.out::println);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/webclient2")
    public void webclient2(){
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:23000")
                .build();
        webClient.get().uri("/basewebflux/findAll")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(response -> response.bodyToFlux(MemberEntity.class))
                .doOnNext(System.out::println)
                .blockLast();
    }
}
