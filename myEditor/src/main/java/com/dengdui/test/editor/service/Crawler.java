package com.dengdui.test.editor.service;

import com.dengdui.test.editor.entity.Problem;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class Crawler {

    public Problem crawl(String problemId) {
        try {

            String problemUrl = getProblemUrl(problemId);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(problemUrl);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            Problem info = new Problem();
            populateProblemInfo(info, problemId, EntityUtils.toString(response.getEntity()));
            return info;
        }
        catch (Exception e) {
            return null;
        }
        finally {

        }
    }

    protected abstract String getProblemUrl(String problemId);

    protected abstract void populateProblemInfo(Problem info, String problemId, String html) throws Exception;

}
