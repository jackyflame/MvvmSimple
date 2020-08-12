package com.jf.orr.net.entity;

/**
 * @Class: BaseWorker
 * @Description:基础数据类（与服务协调沟通的基础状态字段封装）
 * @author: github.com/jackyflame
 * @Date: 2020/8/12
 */
public class RespBaseEntity {

    public String errorMessage;
    public String errorCode;
    private String serverTime;
    private ReturnStatus returnStatus = ReturnStatus.SUCCESS;

    public enum ReturnStatus {
        FAILED , SUCCESS
    }

    public boolean isSucceed() {
        return (returnStatus == ReturnStatus.SUCCESS);
    }

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
