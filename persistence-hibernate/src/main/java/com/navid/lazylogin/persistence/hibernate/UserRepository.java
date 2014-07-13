package com.navid.lazylogin.persistence.hibernate;

import com.navid.lazylogin.persistence.hibernate.domain.UserHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface UserRepository extends JpaRepository<UserHb, Long> {
    
}
