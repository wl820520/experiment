package com.justh5.experiment.controller;

import com.justh5.experiment.config.WeChatMpConfig;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Controller
@RequestMapping("/wechat")
public class WechatController {

    private static Logger log = LogManager.getLogger(WechatController.class);
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatMpConfig weChatMpConfig;

    @RequestMapping("/authorize")
    @ResponseBody
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        String url = "http://experiment.justh5.com/wechat/userInfo";
        wxMpService=weChatMpConfig.wxMpService();
        String redirectURL = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("【微信网页授权】获取code,redirectURL={}", redirectURL);
        return "redirect:" + redirectURL;
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl) throws Exception {
        log.info("【微信网页授权】code={}", code);
        log.info("【微信网页授权】state={}", returnUrl);
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken;
        try {
            wxMpService=weChatMpConfig.wxMpService();
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.info("【微信网页授权】{}", e);
            throw new Exception(e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【微信网页授权】openId={}", openId);
        return "redirect:" + returnUrl + "?openid=" + openId;
    }


    // URL:   http://www.xxxx.com/wechat/
    // Token: 此处TOKEN即为微信接口配置信息的Token

    private String TOKEN = "wjbjusth5";

    /**
     * 验证微信后台配置的服务器地址有效性
     *
     * 接收并校验四个请求参数
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return echostr
     */
    @RequestMapping("/")
    @ResponseBody
    public String checkName(@RequestParam(name = "signature") String signature,
                            @RequestParam(name = "timestamp") String timestamp,
                            @RequestParam(name = "nonce") String nonce,
                            @RequestParam(name = "echostr") String echostr) {

        log.info("微信-开始校验签名");
        log.info("收到来自微信的 echostr 字符串:{}", echostr);

//        加密/校验流程如下:
//        1. 将token、timestamp、nonce三个参数进行字典序排序
//        2. 将三个参数字符串拼接成一个字符串进行sha1加密
//        3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

        // 1.排序
        String sortString = sort(TOKEN, timestamp, nonce);
        // 2.sha1加密
        String myString = sha1(sortString);
        // 3.字符串校验
        if (myString != null && myString != "" && myString.equals(signature)) {
            log.info("微信-签名校验通过");
            //如果检验成功原样返回echostr，微信服务器接收到此输出，才会确认检验完成。
            log.info("回复给微信的 echostr 字符串:{}", echostr);
            return echostr;
        } else {
            log.error("微信-签名校验失败");
            return "";
        }
    }

    /**
     * 排序方法
     * @param token     Token
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @return
     */
    public String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str);
        }

        return sb.toString();
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return    加密后的内容
     */
    public String sha1(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // 创建 16进制字符串
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
