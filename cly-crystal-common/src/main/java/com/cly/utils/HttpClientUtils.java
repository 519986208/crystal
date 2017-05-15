package com.cly.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cly.constant.DefaultConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * HTTP请求工具类
 */
@Slf4j
public class HttpClientUtils {

    private HttpClientUtils() {
    }

    /**
     * @param url http请求url
     * @return
     */
    public static String doGetRequst(String url) {
        return doGetRequst(url, null);
    }

    /**
     * @param url http请求url
     * @param headers http 请求头信息
     * @return
     */
    public static String doGetRequst(String url, Map<String, String> headers) {
        return doGetRequst(url, headers, null);
    }

    /**
     * @param url 请求url
     * @param headers 请求头信息
     * @param params 请求参数
     * @return
     */
    public static String doGetRequst(String url, Map<String, String> headers, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("parameter is illegal : url");
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            List<NameValuePair> nvps = new ArrayList<>();
            if (MapUtils.isNotEmpty(params)) {
                for (Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            String str = EntityUtils.toString(new UrlEncodedFormEntity(nvps, DefaultConstants.DEFAULT_CHARSET));
            String excuteUrl = url;
            if (StringUtils.isNotEmpty(str)) {
                excuteUrl = excuteUrl + "?" + str;
            }
            HttpGet httpPost = new HttpGet(excuteUrl);
            if (MapUtils.isNotEmpty(headers)) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse res = httpclient.execute(httpPost);
            return EntityUtils.toString(res.getEntity(), DefaultConstants.DEFAULT_CHARSET);
        } catch (Exception e) {
            log.error("doGetRequst error ! " + url, e);
            throw new RuntimeException("doGetRequst error ! " + url, e);
        }
    }

    /**
     * @param url 请求url
     * @param headers 请求头信息
     * @param params 请求参数
     * @return
     */
    public static String doPostRequst(String url, Map<String, String> headers, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("parameter is illegal : url");
        }
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);
            if (MapUtils.isNotEmpty(headers)) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            List<NameValuePair> nvps = new ArrayList<>();
            if (MapUtils.isNotEmpty(params)) {
                for (Entry<String, String> entry : params.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse res = httpclient.execute(httpPost);
            return EntityUtils.toString(res.getEntity(), DefaultConstants.DEFAULT_CHARSET);
        } catch (Exception e) {
            log.error("doPostRequst error ! " + url, e);
            throw new RuntimeException("doPostRequst error !" + url, e);
        }
    }

}
