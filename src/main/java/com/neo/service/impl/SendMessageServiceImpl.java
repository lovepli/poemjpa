package com.neo.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.neo.service.SendMessageService;
import com.neo.utils.resultcodemessageutil.ResultCode;
import com.neo.utils.resultcodemessageutil.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 18m38546
 * @CreateTime: 2018-11-30
 * @Description:
 */
@Slf4j
@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Override
    public String sendMessage(String tellNumber,String lnt,String wei){

        // 短信应用SDK AppID   1400开头
        int appid = 1400145509;

        // 短信应用SDK AppKey
        String appkey = "5ec7c78e67a3b6f2455d4f8b28fb8829";

        // 需要发送短信的手机号码
        String[] phoneNumbers = {tellNumber};

        // 短信模板ID，需要在短信应用中申请
        int templateId =222882; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
        //templateId7839对应的内容是"您的验证码是: {1}"
        // 签名
        String smsSign = "华晨鑫源"; // NOTE: 这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台中申请，另外签名参数使用的是`签名内容`，而不是`签名ID`

        try {
            String  sb="精度位置为："+lnt+"维度位置为："+wei+"，"+"123456";
            String resultCode= sb;
           // log.info("生成的经纬度信息为："+sb);

            String[] params = {sb};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            //log.info("调用腾讯短信接口返回的状态码数据："+result);
            return resultCode;
        } catch(HTTPException e){//抛出异常自定义返回值

            ResultCode resultCode=new ResultCode.ResultCodeBuilder()//使用到建造者模式
                    .setStatusCode(ResultEnum.getEnumIndexByValue("Http_Error"))
                    .setCodeMessage("HTTP响应码错误")
                    .setData(null)
                    .build();//建造了一个有参数的对像
            String backStr= JSON.toJSONString(resultCode);

            return backStr;
        }catch(JSONException e){//抛出异常自定义返回值
            ResultCode resultCode=new ResultCode.ResultCodeBuilder()
                    .setStatusCode(ResultEnum.getEnumIndexByValue("Json_Error"))
                    .setCodeMessage("json解析错误")
                    .setData(null)
                    .build();
            String backStr= JSON.toJSONString(resultCode);

            return backStr;
        }catch(IOException e){//抛出异常自定义返回值

            ResultCode resultCode=new ResultCode.ResultCodeBuilder()
                    .setStatusCode(ResultEnum.getEnumIndexByValue("Io_Error"))
                    .setCodeMessage("网络IO错误")
                    .setData(null)
                    .build();
            String backStr= JSON.toJSONString(resultCode);

            return backStr; //返回的异常状态码
        }
        //总共会出现的三种异常
//        catch (HTTPException e) {
//            // HTTP响应码错误
//            e.printStackTrace();
//            return "HTTP响应码错误";
//        } catch (JSONException e) {
//            // json解析错误
//            e.printStackTrace();
//            return " json解析错误";
//        } catch (IOException e) {
//            // 网络IO错误
//            e.printStackTrace();
//            return " 网络IO错误";
//        }
    }
}
