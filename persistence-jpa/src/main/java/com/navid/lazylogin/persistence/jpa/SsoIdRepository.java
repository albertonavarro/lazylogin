package com.navid.lazylogin.persistence.jpa;

import com.navid.lazylogin.persistence.jpa.domain.SessionIdHb;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alberto
 */
public interface SsoIdRepository extends JpaRepository<SessionIdHb, String> {

}
