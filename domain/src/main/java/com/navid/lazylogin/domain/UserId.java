package com.navid.lazylogin.domain;

import com.google.common.base.Objects;

/**
 *
 * @author alberto
 */
public class UserId {

    private final Long value;

    public UserId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("value", value).toString();
    }

}
