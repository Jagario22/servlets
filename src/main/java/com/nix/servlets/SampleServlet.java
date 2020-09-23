package com.nix.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "sample-servlet", urlPatterns = "/sample")
public class SampleServlet extends HttpServlet {
    ConcurrentHashMap<String, String> elements = new ConcurrentHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(SampleServlet.class);

    @Override
    public void init() {
        log.info("Sample Servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String currentIp = req.getRemoteAddr();
        elements.put(currentIp, req.getHeader("User-Agent"));
        PrintWriter responseBody = resp.getWriter();
        resp.setContentType("text/html");

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<ul>");
        for (Map.Entry<String, String> entry : elements.entrySet()) {
            if (entry.getKey().equals(currentIp))
                stringBuilder.append("<li><b>").append(entry.getKey()).append(" ").append(entry.getValue()).append("</b></li>");
            else
                stringBuilder.append("<li>").append(entry.getKey()).append(" ").append(entry.getValue()).append("</li>");


        }
        stringBuilder.append("</ul>");

        responseBody.println("<h3 align=\"center\">Request from: " + stringBuilder.toString() + "</h3>");
    }

    @Override
    public void destroy() {
        log.info("Sample Servlet destroyed");
    }
}