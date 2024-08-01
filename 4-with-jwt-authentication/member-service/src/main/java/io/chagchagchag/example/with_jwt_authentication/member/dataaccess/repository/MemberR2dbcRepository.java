package io.chagchagchag.example.with_jwt_authentication.member.dataaccess.repository;

import io.chagchagchag.example.with_jwt_authentication.member.dataaccess.entity.MemberEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MemberR2dbcRepository extends R2dbcRepository<MemberEntity, Long> {

}
