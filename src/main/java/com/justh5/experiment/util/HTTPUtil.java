/**
 * Create date: 2017/12/26
 * Author jw
 * Description
 */
package com.justh5.experiment.util;

import com.youxinpai.common.util.web.ResponseResult;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.youxinpai.common.util.constant.Constant.UTF8_CHARSET;

public class HTTPUtil {

    private static CloseableHttpClient defaulHttpClient = HttpClients.createDefault();
    private static final Header[] NULL_HEADER = new Header[0];

    private static int connectTimeout = 2000;
    private static int connectionRequestTimeout = 2000;
    private static int socketTimeout = 5000;

    private static String DEFAULT_CHARSET = "UTF-8";

    private static RequestConfig config;

    private static Logger logger = LoggerFactory.getLogger(com.youxinpai.cloud.util.HTTPUtil.class);

    public static void setConfig(RequestConfig config) {

    }

    static {
        buildRequestConfig(connectTimeout, connectionRequestTimeout, socketTimeout);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @Description:下载Excel文件(xlsx)
     * @author jw
     * @date 2017/12/27
     * @param bytes 文件字节数组
     * @param response
     * @param fileName 文件名
     * @return void
     */
    public static void downloadExcelFile(byte[] bytes, HttpServletResponse response,String fileName) {
        try {
            InputStream is = new ByteArrayInputStream(bytes);

            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @Description:使用HttpClient发送post请求
     */
    public static String httpClientPost(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlParam);
        // 构建请求参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> elem = iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), String.valueOf(elem.getValue())));
        }
        BufferedReader br = null;
        try {
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = client.execute(httpPost);
            // 读取服务器响应数据
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }

    /**
     * @Description:使用HttpClient发送get请求
     */
    public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        BufferedReader br = null;
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                try {
                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                sbParams.append("&");
            }
        }
        if (sbParams != null && sbParams.length() > 0) {
            urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
        }
        HttpGet httpGet = new HttpGet(urlParam);
        httpGet.addHeader("Authorization","Basic Z3Vlc3Q6Z3Vlc3Q=");
        try {
            HttpResponse response = client.execute(httpGet);
            // 读取服务器响应数据
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }


    public static ResponseResult doFormFilePostReq(String url, Map<String,MultipartFile> multipartFileMap, Map<String,String> paramsMap) throws IOException {
        logger.info("开始上传文件,url:{}",url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        //不设置的话会中文文件名乱码
        multipartEntityBuilder.setCharset(CharsetUtils.get(DEFAULT_CHARSET));
        //遍历文件map放入请求主体
        if (multipartFileMap != null && multipartFileMap.size() > 0) {
            for (Map.Entry<String,MultipartFile> multipartFile : multipartFileMap.entrySet()) {
                multipartEntityBuilder.addBinaryBody(
                        //文件的参数名(不是文件名)
                        multipartFile.getKey(),
                        //文件流
                        multipartFile.getValue().getInputStream()
                );
                logger.info("文件名{}",multipartFile.getValue().getOriginalFilename());
            }
        }
        //遍历非文件参数map放入请求实体
        if(paramsMap != null && paramsMap.size() > 0){
            for (Map.Entry<String, String> param : paramsMap.entrySet()){
                StringBody sb = new StringBody(param.getValue(), ContentType.APPLICATION_FORM_URLENCODED);
                multipartEntityBuilder.addPart(param.getKey(), sb);
            }
        }
        //发送请求
        HttpEntity httpEntity = multipartEntityBuilder.build();
        httpPost.setEntity(httpEntity);

        return getHttpResult(httpPost);

    }

    /**
     * @param connectTimeout           connectTimeout 建立连接超时时间
     * @param connectionRequestTimeout 获取连接超时时间
     * @param socketTimeout            从服务器读取数据的超时时间
     * @return
     */
    public static RequestConfig buildRequestConfig(int connectTimeout, int connectionRequestTimeout, int socketTimeout) {
        RequestConfig.Builder copyConfig = RequestConfig.copy(RequestConfig.DEFAULT);
        copyConfig.setConnectTimeout(connectTimeout);
        copyConfig.setConnectionRequestTimeout(connectionRequestTimeout);
        copyConfig.setSocketTimeout(socketTimeout);
        return config;
    }

    /**
     * 执行http请求,获取请求结果
     *
     * @param httpUriRequest
     * @return
     * @throws IOException
     */
    private static ResponseResult getHttpResult(HttpUriRequest httpUriRequest) throws IOException {
        try (CloseableHttpResponse response = defaulHttpClient.execute(httpUriRequest)) {
            ResponseResult<String> responseResult = new ResponseResult<>();
            responseResult.setCode(response.getStatusLine().getStatusCode());
            responseResult.setData(EntityUtils.toString(response.getEntity(), UTF8_CHARSET));
            return responseResult;
        }
    }


}
