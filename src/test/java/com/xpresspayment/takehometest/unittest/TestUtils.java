package com.xpresspayment.takehometest.unittest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.entity.StringEntity;

import java.nio.charset.StandardCharsets;

public class TestUtils {

    public static StatusLine createStatusLine(int statusCode) {
        return new MockStatusLine(statusCode);
    }

    public static HttpEntity createHttpEntity(String responseBody) {
        return new StringEntity(responseBody, StandardCharsets.UTF_8);
    }

    private static class MockStatusLine implements StatusLine {
        private final int statusCode;

        public MockStatusLine(int statusCode) {
            this.statusCode = statusCode;
        }

        @Override
        public ProtocolVersion getProtocolVersion() {
            return new ProtocolVersion("HTTP", 1, 1);
        }

        @Override
        public int getStatusCode() {
            return statusCode;
        }

        @Override
        public String getReasonPhrase() {
            return null;
        }
    }
}
