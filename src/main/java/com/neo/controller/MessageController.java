package com.neo.controller;


import com.alibaba.fastjson.JSON;
import com.neo.service.SendMessageService;
import com.neo.utils.resultcodemessageutil.ResultCode;
import com.neo.utils.resultcodemessageutil.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {//提供给别人调用的发送短信的接口

    @Autowired
    SendMessageService sendMessageService;

    @RequestMapping(value = "/messageSend", method = RequestMethod.GET)
    public String getData(@RequestParam(name = "phoneNumber") String phoneNumber,@RequestParam(name = "lat") String lat,@RequestParam(name = "lon") String lon) {

        try {
           // log.info("需要发送短信的手机号码为："+phoneNumber);

            String resultStr = sendMessageService.sendMessage(phoneNumber,lat,lon);  //传的参数带了一个logInfo对象
            ResultCode resultCode = new ResultCode.ResultCodeBuilder()
                    .setStatusCode(ResultEnum.getEnumIndexByValue("Success"))
                    .setCodeMessage("短信发送成功")
                    .setData(resultStr)
                    .build();

            return JSON.toJSONString(resultCode);
        } catch (Exception e) {
            e.printStackTrace();
           // log.info("短信接口调用异常信息："+e);
            ResultCode resultCode = new ResultCode.ResultCodeBuilder()
                    .setStatusCode(ResultEnum.getEnumIndexByValue("Msg_Error"))
                    .setCodeMessage("短信发送失败")
                    .setData(null)
                    .build();
            return JSON.toJSONString(resultCode);
        }
    }
}