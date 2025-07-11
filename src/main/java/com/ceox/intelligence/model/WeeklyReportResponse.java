package com.ceox.intelligence.model;

public class WeeklyReportResponse {
    private String html;
    private String markdown;

    public WeeklyReportResponse() {}

    public WeeklyReportResponse(String html, String markdown) {
        this.html = html;
        this.markdown = markdown;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }
}
