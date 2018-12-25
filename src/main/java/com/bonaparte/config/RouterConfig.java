package com.bonaparte.config;


import com.bonaparte.service.SendTimePerSecond;
import com.bonaparte.service.TimeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component
public class RouterConfig {

    @Autowired
    private TimeHandler timeHandler;
    @Autowired
    private SendTimePerSecond sendTimePerSecond;

    @Bean
    public RouterFunction<ServerResponse> timeRouter(){
        return route(GET("/time"), req -> timeHandler.getTime(req))
                .andRoute(GET("/date"), req -> timeHandler.getDate(req))
                .andRoute(GET("/times"), req -> sendTimePerSecond.sendTimePerSecond(req));
    }
}
