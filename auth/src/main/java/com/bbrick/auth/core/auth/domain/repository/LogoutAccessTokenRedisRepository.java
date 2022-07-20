package com.bbrick.auth.core.auth.domain.repository;

import com.bbrick.auth.core.auth.dto.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
