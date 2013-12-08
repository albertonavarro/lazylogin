package com.navid.login.persistence;

import com.navid.login.domain.SsoId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface SsoIdRepository extends JpaRepository<SsoId, String> {
    
}
