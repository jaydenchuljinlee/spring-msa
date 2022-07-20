package com.bbrick.auth.core.auth.domain.repository;

import com.bbrick.auth.core.auth.dto.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
