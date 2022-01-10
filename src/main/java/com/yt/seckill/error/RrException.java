package com.yt.seckill.error;


/**
 *  自定义业务异常
 *  注意：如果继承的是Exception类，那么Spring的事务管理将会失效，
 *  只有继承RuntimeException类才使Spring的事务管理不会失效
 * @author yt
 */
public class RrException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RrException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RrException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RrException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RrException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}