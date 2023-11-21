package com.wesell.authenticationserver.domain.repository;

import com.wesell.authenticationserver.domain.entity.RedisTokenInfo;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenInfoRepository extends CrudRepository<RedisTokenInfo,String> {
    /**
     * @Id 가 아닌 값으로 조회하고 싶은 경우, @Indexed 어노테이션을 필드에 붙이고 findBy... 메소드를 선언하면 된다.
     */
}
