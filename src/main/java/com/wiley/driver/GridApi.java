package com.wiley.driver;

import com.wiley.utils.Report;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.openqa.selenium.remote.SessionId;

import java.io.IOException;
import java.net.URL;

class GridApi {

    private final URL hubUrl;
    private final SessionId sessionId;

    GridApi(URL hubUrl, SessionId sessionId) {
        this.hubUrl = hubUrl;
        this.sessionId = sessionId;
    }

    String getNodeIp() {
        try {
            Connection.Response execute = Jsoup
                    .connect(hubUrl.getProtocol() + "://" + hubUrl.getHost() + ":" + hubUrl.getPort() + "/grid/api/")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.GET)
                    .execute();

            if (execute.statusCode() != 200) {
                return hubUrl.getHost();
            }

            String json = Jsoup
                    .connect(hubUrl.getProtocol() + "://" + hubUrl.getHost() + ":" + hubUrl.getPort() + "/grid/api/testsession?session=" + sessionId)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.POST)
                    .execute()
                    .parse()
                    .text();

            return new URL(new JSONObject(json).get("proxyId").toString()).getHost();
        } catch (JSONException e) {
            Report.jenkins("JSONException in getNodeIp " + e.getMessage());
        } catch (IOException e) {
            Report.jenkins("IOException in getNodeIp " + e.getMessage());
        }
        throw new RuntimeException("Cannot get node id by session from grid api.");
    }
}
