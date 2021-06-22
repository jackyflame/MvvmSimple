package com.jf.orr.net.entity;

/**
 * Created by Android Studio.
 * User:  github.com/jackyflame
 * Date: 2020/04/01
 * Time: 09:09
 */
public abstract class RespBaseEntity<T> implements IResp<T>{

    private String extMessage;
    private String errorMessage;
    private String errorCode;
    private String serverTime;
    private String errorFrom;
    private ReturnStatus returnStatus;
    private ReturnStatus status;
    private String description;
    private String code;

    public enum ReturnStatus {
        SUCCEED, FAILED , SUCCESS
    }

    public boolean isSucceed() {
        if(returnStatus != null){
            return (returnStatus == ReturnStatus.SUCCEED || returnStatus == ReturnStatus.SUCCESS);
        } else if(status != null){
            return (status == ReturnStatus.SUCCEED || status == ReturnStatus.SUCCESS);
        } else if (description != null) {
            return "SUCCESS".equalsIgnoreCase(description);
        } else if(code != null){
            return "0".endsWith(code);
        }
        return false;
    }

    public ReturnStatus getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(ReturnStatus returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode != null ? errorCode : code;
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

    public String getErrorFrom() {
        return errorFrom;
    }

    public void setErrorFrom(String errorFrom) {
        this.errorFrom = errorFrom;
    }

    public ReturnStatus getStatus() {
        return status;
    }

    public void setStatus(ReturnStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
