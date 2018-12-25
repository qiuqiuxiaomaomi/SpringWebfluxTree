package com.bonaparte.service;


import com.bonaparte.bean.MemberEntity;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.naming.InsufficientResourcesException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BonaparteService {

    public static Map<String, MemberEntity> memberEntityMap = new HashMap<>();

    public Flux<MemberEntity> getAll(){
        return Flux.fromIterable(memberEntityMap.values());
    }

    public Mono<MemberEntity> createOrUpdate(MemberEntity memberEntity){
        memberEntityMap.put(memberEntity.getPhone(), memberEntity);
        return Mono.justOrEmpty(memberEntityMap.get(memberEntity.getPhone()))
                .switchIfEmpty(Mono.error(new ResourceRequestDeniedException()));
    }
}
