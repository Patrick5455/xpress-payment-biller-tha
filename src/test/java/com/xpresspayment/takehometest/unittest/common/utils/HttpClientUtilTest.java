package com.xpresspayment.takehometest.unittest.common.utils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.xpresspayment.takehometest.common.utils.HttpClientUtil;
import com.xpresspayment.takehometest.unittest.TestUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;


class HttpClientUtilTest {

    @Mock
    private CloseableHttpClient mockHttpClient;

    private CloseableHttpResponse mockHttpResponse;

    @InjectMocks
    private HttpClientUtil httpClientUtil;

    @BeforeEach
    void setUp() throws IOException {

        openMocks(this);
        mockHttpResponse = mock(CloseableHttpResponse.class);

        setupMockHttpResponse(200, "{\"size\": 1, \"page\": 1}");

        when(mockHttpClient.execute(any(HttpRequestBase.class)))
                .thenReturn(mockHttpResponse);

    }


    @Test
    void testSingleClassGet() throws Exception {
        String url = "https://example.com";
        Map<String, String> reqHeaders = Collections.singletonMap("header", "value");
        Map<String, String> params = Collections.singletonMap("param", "value");

        when(mockHttpClient.execute(any(HttpRequestBase.class)))
                .thenReturn(mockHttpResponse);

        httpClientUtil.singleClassGet(url, reqHeaders, params, PageAndSize.class);

        verify(mockHttpClient).execute(any(HttpRequestBase.class));
    }

    @Test
    void testSingleClassGetResponse() throws Exception {
        String url = "https://example.com";
        Map<String, String> reqHeaders = Collections.singletonMap("header", "value");
        Map<String, String> params = Collections.singletonMap("param", "value");

        when(mockHttpClient.execute(any(HttpRequestBase.class)))
                .thenReturn(mockHttpResponse);

        CloseableHttpResponse response = httpClientUtil.singleClassGetResponse(url, reqHeaders, params);

        verify(mockHttpClient).execute(any(HttpRequestBase.class));
        assertEquals(mockHttpResponse, response);
    }

    @Test
    void testSingleClassPostWithUrlEncodedFormEntity() throws Exception {
        String url = "https://example.com";
        Map<String, String> reqHeaders = Collections.singletonMap("header", "value");
        Map<String, String> params = Collections.singletonMap("param", "value");
        UrlEncodedFormEntity formEntity = mock(UrlEncodedFormEntity.class);

        when(mockHttpClient.execute(any(HttpPost.class)))
                .thenReturn(mockHttpResponse);

        httpClientUtil.singleClassPost(url, reqHeaders, params, formEntity, PageAndSize.class);

        verify(mockHttpClient).execute(any(HttpPost.class));
    }

    @Test
    void testSingleClassPostWithJson() throws Exception {
        String url = "https://example.com";
        Map<String, String> reqHeaders = Collections.singletonMap("header", "value");
        Map<String, String> params = Collections.singletonMap("param", "value");
        String json = "{ \"key\": \"value\" }";

        when(mockHttpClient.execute(any(HttpPost.class)))
                .thenReturn(mockHttpResponse);

        httpClientUtil.singleClassPost(url, reqHeaders, params, json, PageAndSize.class);

        verify(mockHttpClient).execute(any(HttpPost.class));
    }

    @Test
    void testSingleClassPostForm() throws Exception {
        String url = "https://example.com";
        Map<String, String> reqHeaders = Collections.singletonMap("header", "value");
        Map<String, String> params = Collections.singletonMap("param", "value");
        Map<String, String> formValues = Collections.singletonMap("formKey", "formValue");

        when(mockHttpClient.execute(any(HttpPost.class)))
                .thenReturn(mockHttpResponse);

        httpClientUtil.singleClassPostForm(url, reqHeaders, params, formValues, PageAndSize.class);

        verify(mockHttpClient).execute(any(HttpPost.class));
    }

    @Test
    void testExecuteRequestForHttpResponse() throws Exception {
        HttpRequestBase request = mock(HttpRequestBase.class);

        when(mockHttpClient.execute(request)).thenReturn(mockHttpResponse);

        CloseableHttpResponse response = httpClientUtil.executeRequestForHttpResponse(request);

        verify(mockHttpClient).execute(request);
        assertEquals(mockHttpResponse, response);
    }

    @Test
    void testExecuteRequest() throws Exception {
        HttpRequestBase request = mock(HttpRequestBase.class);

        when(mockHttpClient.execute(request)).thenReturn(mockHttpResponse);

        PageAndSize result = httpClientUtil.executeRequest(request, PageAndSize.class);

        verify(mockHttpClient).execute(request);
        assertEquals(1, result.getPage());
    }

    private void setupMockHttpResponse(int statusCode, String responseBody) {
        when(mockHttpResponse.getStatusLine()).thenReturn(TestUtils.createStatusLine(statusCode));
        HttpEntity httpEntity = TestUtils.createHttpEntity(responseBody);
        when(mockHttpResponse.getEntity()).thenReturn(httpEntity);
    }


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageAndSize {
        @Builder.Default
        private int page = 0;
        @Builder.Default
        private int size = 1;
    }
}