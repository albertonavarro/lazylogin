package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.persistence.hibernate.domain.SessionIdHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface SsoIdRepository extends JpaRepository<SessionIdHb, String> {
    
}
