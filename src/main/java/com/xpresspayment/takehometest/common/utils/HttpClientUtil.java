

package com.xpresspayment.takehometest.common.utils;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.xpresspayment.takehometest.common.exceptions.AppException;
import com.xpresspayment.takehometest.common.exceptions.HttpCallException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpClientUtil {

    public <T> T singleClassGet(
            String url, Map<String, String> reqHeaders,
            Map<String, String> params, Class<T> cls) throws HttpCallException {
        HttpGet request = new HttpGet(url);
        addRequestsParams(params, request);
        addRequestHeaders(reqHeaders, request);
        log.info("http GET request: {}", request);
        return executeRequest(request, cls);
    }

    public CloseableHttpResponse singleClassGetResponse(
            String url, Map<String, String> reqHeaders,
            Map<String, String> params) throws HttpCallException {
        HttpGet request = new HttpGet(url);
        addRequestsParams(params, request);
        addRequestHeaders(reqHeaders, request);
        log.info("http GET request: {}", request);
        return executeRequestForHttpResponse(request);
    }

    public <T> T singleClassPost(
            String url, Map<String, String> reqHeaders,
            Map<String, String> params,
            UrlEncodedFormEntity parameters, Class<T> cls) throws Exception {
        HttpPost request = new HttpPost(url);
        addRequestsParams(params, request);
        addRequestHeaders(reqHeaders, request);
        request.setEntity(parameters);
        log.info("http POST request: {}", request);
        return executeRequest(request, cls);
    }

    public <T> T singleClassPost(
            String url, Map<String, String> reqHeaders,
            Map<String, String> params,
            String json, Class<T> cls) throws HttpCallException {
        HttpPost request = new HttpPost(url);
        addRequestsParams(params, request);
        addRequestHeaders(reqHeaders, request);
        try {
            request.setEntity(new StringEntity(json));
            log.info("http POST request: {}", request);
            return executeRequest(request, cls);
        }catch (Exception e){
            log.error("something went wrong while making post request ", e);
            throw new HttpCallException("something went wrong while making post request", e);
        }
    }


    public <T> T singleClassPostForm(String url, Map<String, String> reqHeaders,
                                            Map<String, String> params,
                                            Map<String, String> formValues, Class<T> cls) throws HttpCallException {
        HttpPost request = new HttpPost(url);
        addRequestHeaders(reqHeaders, request);
        addRequestsParams(params, request);
        // add request headers
        request.addHeader(HttpHeaders.ACCEPT, "*/*");
        request.addHeader(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_URL_FORM_ENCODED);
        try {
            List<NameValuePair> nvps = new ArrayList<>();
            formValues.forEach((key, value) -> nvps.add(new BasicNameValuePair(key, value)));
            request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));

            log.info("http POST request: {}", request.getEntity());
        }catch (Exception ex){
            log.error("something went wrong while making post request ", ex);
            throw new HttpCallException("something went wrong while making post request", ex);
        }
        return executeRequest(request, cls);
    }



    private void throwOnError (CloseableHttpResponse response) throws HttpCallException {
        String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
        if  (statusCode.startsWith("4") || statusCode.startsWith("5")) {
            log.error("something went wrong while making http call >>> {}", response.getStatusLine());
            throw new HttpCallException(response.getStatusLine().getStatusCode(),
                    response.getStatusLine().getReasonPhrase());
        }
    }

    private void addRequestsParams(Map<String, String> requestParams,
                                          HttpRequestBase request) throws HttpCallException {
        if (requestParams != null && !requestParams.isEmpty()) {
            log.info("setting request params: {}", requestParams);
            URIBuilder uri = new URIBuilder(request.getURI());
            requestParams.forEach(uri::addParameter);
            try {
                request.setURI(uri.build());
            } catch (URISyntaxException e) {
                log.error("something went wrong while making adding parameters to request ", e);
                throw new HttpCallException("something went wrong while making adding parameters to request", e);
            }
        }
    }

    private void addRequestHeaders(Map<String, String> requestHeaders,
                                          HttpRequestBase request) {
        request.addHeader(HttpHeaders.ACCEPT, Constants.APPLICATION_JSON);
        request.addHeader(HttpHeaders.CONTENT_TYPE, Constants.APPLICATION_JSON);
        if(requestHeaders != null && !requestHeaders.isEmpty()) {
            for(String key : requestHeaders.keySet()) {
                request.addHeader(key, requestHeaders.get(key));
            }
        }
    }
    private CloseableHttpResponse executeRequestForHttpResponse(HttpRequestBase request) throws HttpCallException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            log.info("response status code: {}", response.getStatusLine().getStatusCode());
            log.info("http POST response: {}", entity);
            throwOnError(response);
            return response;
        } catch (Exception e) {
            log.error("something went wrong while making post request ", e);
            throw new HttpCallException("something went wrong while making post request", e);
        }
    }

    private <T> T executeRequest(HttpRequestBase request, Class<T> cls) throws HttpCallException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            T results = null;
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                log.info("http POST response: {}", entity);
                if (entity != null) {
                    // return it as a custom class type
                    results = new Gson().fromJson(EntityUtils.toString(entity), cls);
                }
                throwOnError(response);
                return results;
            } catch (Exception e) {
                log.error("something went wrong while making post request ", e);
                throw new HttpCallException("something went wrong while making post request", e);
            }
        } catch (IOException e) {
            log.error("something went wrong while executing http request:", e);
            throw new AppException("something went wrong while executing http request", e);
        }

    }
}
