package com.mysoft.leistd.logging;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 避免流被关闭
 */
public class CustomContentCachingResponseWrapper extends ContentCachingResponseWrapper {
    private final Integer contentCacheLimit;
    private final ByteArrayOutputStream cachedContent;

    public CustomContentCachingResponseWrapper(HttpServletResponse response, int contentCacheLimit) {
        super(response);
        this.contentCacheLimit = contentCacheLimit;
        this.cachedContent = new ByteArrayOutputStream(contentCacheLimit);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new LimitedServletOutputStream(super.getOutputStream());
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(getOutputStream());
    }

    @Override
    public byte[] getContentAsByteArray() {
        return cachedContent.toByteArray();
    }

    public void copyBodyToResponse(boolean complete) throws IOException {
        super.copyBodyToResponse(complete);
    }

    private class LimitedServletOutputStream extends ServletOutputStream {
        private final ServletOutputStream original;

        public LimitedServletOutputStream(ServletOutputStream original) {
            this.original = original;
        }

        @Override
        public void write(int b) throws IOException {
            if (cachedContent.size() < contentCacheLimit) {
                cachedContent.write(b);
            }
            original.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            int bytesToCache = Math.min(len, contentCacheLimit - cachedContent.size());
            if (bytesToCache > 0) {
                cachedContent.write(b, off, bytesToCache);
            }
            original.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return original.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            original.setWriteListener(writeListener);
        }
    }
}
