package com.zongzhang.websocket;

import java.io.Serializable;

/**
 * @author zongzhang
 * @since 1.0
 * <p>
 * create on 2018-12-13
 */
public class WsData implements Serializable {
    private static final long serialVersionUID = 414634839897092692L;

    private String username;
    private String message;
    private String action;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
