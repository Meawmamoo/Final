package com.mimicki.afinal.Retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL on 7/13/2016.
 */
public class ServiceModel {
    /**
     * isConnectDBSuccess : true
     * status : true
     * msg : Register successful
     */

    private boolean isConnectDBSuccess;
    public boolean status;
    private String msg;
    /**
     * errmsg : This username address was already used!
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String toString() {
        StringBuilder string = new StringBuilder().append("isConnectDBSuccess : " + isConnectDBSuccess + "\n")
                .append("status: " + status + "\n");

        if (errmsg != null ) {
            string.append(errmsg);
        }
        return string.toString();
    }



    /////////////////////////////////////////////
//    /**
//     * isSuccess : true
//     */
//
//    private boolean isSuccess;
//
//    public boolean isIsSuccess() {
//        return isSuccess;
//    }
//
//    public void setIsSuccess(boolean isSuccess) {
//        this.isSuccess = isSuccess;
//    }
}
