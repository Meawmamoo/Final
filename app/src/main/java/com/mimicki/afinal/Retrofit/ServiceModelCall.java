package com.mimicki.afinal.Retrofit;

/**
 * Created by DELL on 11/29/2016.
 */

public class ServiceModelCall {

    /**
     * isConnectDBSuccess : true
     * status : true
     * idcase : 4
     */

    private boolean isConnectDBSuccess;
    public boolean status;
    private String idcase;
    /**
     * errmsg : Error: Authentication
     */

    private String errmsg;


    public boolean isIsConnectDBSuccess() {
        return isConnectDBSuccess;
    }

    public void setIsConnectDBSuccess(boolean isConnectDBSuccess) {
        this.isConnectDBSuccess = isConnectDBSuccess;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getIdcase() {
        return idcase;
    }

    public void setIdcase(String idcase) {
        this.idcase = idcase;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
