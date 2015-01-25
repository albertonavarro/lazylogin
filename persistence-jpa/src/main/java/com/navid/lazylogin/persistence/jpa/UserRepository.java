package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.persistence.jpa.domain.UserHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface UserRepository extends JpaRepository<UserHb, Long> {

}
