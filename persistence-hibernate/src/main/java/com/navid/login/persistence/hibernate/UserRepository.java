package com.navid.login.persistence.hibernate;

import com.navid.login.persistence.hibernate.domain.UserHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface UserRepository extends JpaRepository<UserHb, Long> {
    
}
