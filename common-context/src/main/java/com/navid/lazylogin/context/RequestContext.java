/*
 */
package com.navid.lazylogin.context;

import com.google.common.base.Objects;

public class RequestContext {

    private Long userId;

    private String userName;

    private String correlationId;

    private String sessionId;

    RequestContext() {

    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the correlationId
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * @param correlationId the correlationId to set
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("userName", userName)
                .add("userId", userId)
                .add("sessionId", sessionId)
                .add("correlationId", correlationId)
                .toString();
    }
}
