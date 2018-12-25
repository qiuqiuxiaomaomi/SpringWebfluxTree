package com.bonaparte.dao;

import com.bonaparte.bean.MemberEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface MemberEntityRepository extends ReactiveMongoRepository<MemberEntity, String> {

    Mono<MemberEntity> findUserByName(String name);

    Mono<Long> deleteByName(String name);
}
