package com.navid.login.persistence.hibernate;

import com.navid.login.persistence.hibernate.domain.SsoIdHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface SsoIdRepository extends JpaRepository<SsoIdHb, String> {
    
}
