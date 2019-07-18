package com.building.ipi.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Harry Tian
 * @ClassName: HttpClientUtil
 * @Description: http发送请求的公共类
 * @date 2016年9月1日 下午6:31:14
 */
public class HttpClientUtil {
    private static Logger log = Logger.getLogger(HttpClientUtil.class);

    /**
     * http post请求公共方法
     *
     * @param url       地址
     * @param paramsMap 参数map
     * @param encoding  编码（可空，默认UTF-8）
     */
    public static String httpPost(String url, Map<String, String> paramsMap, String encoding) {
        if (StringUtils.isBlank(encoding)) {
            encoding = "UTF-8";
        }
        String result = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        if (paramsMap != null) {
            paramsList = covertParams2NVPS(paramsMap);
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(paramsList, encoding);
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    String strMessage = "Response content: " + EntityUtils.toString(entity);
                    System.out.println(strMessage);
                    log.error(strMessage);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return result;

    }

    /**
     * map转换为NameValuPair,用于httpclient传递参数
     *
     * @param params 参数map
     * @return
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, String> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();

            pairs.add(new BasicNameValuePair(key, value));
        }

        return pairs;
    }

    /**
     * <ul>
     * <li>http post请求公共方法</li>
     * <li>以url?paramName1=paramValue1&paramName2=paramValue2</li>
     * <li></li>
     * </ul>
     *
     * @param url       地址
     * @param paramsMap 参数map
     * @param encoding  编码(可空,默认UTF-8)
     */
    public static String httpPostWithUrl(String url, Map<String, String> paramsMap, String encoding) {

        if (StringUtils.isBlank(encoding)) {
            encoding = "UTF-8";
        }

        String result = null;

        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {

            // (01)
            // url?paramName1=paramValue1&paramName2=paramValue2
            List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
            if (paramsMap != null) {
                paramsList = covertParams2NVPS(paramsMap);
            }
            UrlEncodedFormEntity uefEntity;
            uefEntity = new UrlEncodedFormEntity(paramsList, encoding);

            url += "?" + EntityUtils.toString(uefEntity);

            // (02)
            HttpPost httppost = new HttpPost(url);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    String strMessage = "Response content: " + EntityUtils.toString(entity);
                    System.out.println(strMessage);
                    log.error(strMessage);

                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        return result;

    }

}
