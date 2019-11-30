package com.neo.utils.resultcodemessageutil;

import java.io.Serializable;

/**
 * @Author:
 * @CreateTime: 2018-10-30
 * @Description: 建造者模式创建对象
 */
public class ResultCode implements Serializable {

    private static final long serialVersionUID = -958839235068348010L;
    private Integer statusCode;
    private String codeMessage;
    private String data;

    public ResultCode() {

    }

    public ResultCode(Integer statusCode, String codeMessage, String data) {
        this.statusCode = statusCode;
        this.codeMessage = codeMessage;
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(String codeMessage) {
        this.codeMessage = codeMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static class ResultCodeBuilder {  //使用到了建造者模式

        private Integer statusCode;
        private String codeMessage;
        private String data;

        public ResultCodeBuilder setStatusCode(Integer statusCode) {  //返回值为对象？
            this.statusCode = statusCode;
            return this;
        }

        public ResultCodeBuilder setCodeMessage(String codeMessage) {
            this.codeMessage = codeMessage;
            return this;
        }

        public ResultCodeBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public ResultCode build() {                     //建造的方法，返回的是一个有参数的对象
            return new ResultCode(statusCode, codeMessage, data);
        }

    }
}
