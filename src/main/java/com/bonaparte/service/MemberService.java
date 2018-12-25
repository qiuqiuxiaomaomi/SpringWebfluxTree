package com.bonaparte.service;


import com.bonaparte.bean.MemberEntity;
import com.bonaparte.dao.MemberEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberService {

    @Autowired
    private MemberEntityRepository memberEntityRepository;

    public Mono<MemberEntity> save(MemberEntity memberEntity){
        return memberEntityRepository.save(memberEntity)
                .onErrorResume( e ->
                        memberEntityRepository.findUserByName(memberEntity.getName())
                .flatMap(originalUser -> {
                    memberEntity.setId(originalUser.getId());
                    return memberEntityRepository.save(memberEntity);
                }));
    }

    public Mono<Long> deleteByName(String name){
        return memberEntityRepository.deleteByName(name);
    }

    public Mono<MemberEntity> findByName(String name){
        return memberEntityRepository.findUserByName(name);
    }

    public Flux<MemberEntity> findAll(){
        return memberEntityRepository.findAll();
    }
}
