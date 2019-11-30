package com.neo.utils.resultcodemessageutil;

public enum ResultEnum {

    Success(1),        //短信发送成功 消息数据发送成功
    Http_Error(101),  //HTTP响应码错误
    Json_Error(102),  //json解析错误
    Io_Error(103),    //网络Io错误
    Msg_Error(104),  //短信发送失败
    TimeOut_Error(105), //超时 (目前没有这个类型)
    MessageData_Error(106), //mqtt消息发送失败
    API_exception(107);   //接口调用失败


    private Integer index;

    ResultEnum(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    /**
     * 根据枚举码获取对应的枚举属性
     *
     * @param index
     * @return ResultEnum
     */
    public static ResultEnum getEnumTypeByIndex(Integer index) {
        ResultEnum[] values = ResultEnum.values();
        for (ResultEnum value : values) {
            //System.out.println(value);
            if (value.getIndex() == index) {
                //System.out.println(value.getIndex());
                return value;
            }
        }
        return null;
    }

    /**
     * 根据枚举属性获取对应的枚举码
     *
     * @param value
     * @return Integer
     * @Despise switch之脱裤子放屁
     */
    public static Integer getEnumIndexByValue(String value) {

        switch (value) {
            case "Http_Error":
                return ResultEnum.Http_Error.getIndex();
            case "Msg_Error":
                return ResultEnum.Msg_Error.getIndex();
            case "TimeOut_Error":
                return ResultEnum.TimeOut_Error.getIndex();
            case "MessageData_Error":
                return ResultEnum.MessageData_Error.getIndex();
            case "Io_Error":
                return ResultEnum.Io_Error.getIndex();
            case "Json_Error":
                return ResultEnum.Json_Error.getIndex();
            case "API_exception":
                return ResultEnum.API_exception.getIndex();
            case "Success":
                return ResultEnum.Success.getIndex();
            default:
                return 0;
        }
    }

    public static void main(String[] args) {


    }
}

