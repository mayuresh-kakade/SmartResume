package com.smartresume.service;

import java.util.List;
import java.util.Map;

public class ResumeParserResult{

    private String rawText;

    private Map<String, List<String>> sections;

    public ResumeParserResult(
            String rawText,
            Map<String, List<String>> sections) {

        this.rawText = rawText;
        this.sections = sections;
    }

    public String getRawText() {
        return rawText;
    }

    public Map<String, List<String>> getSections() {
        return sections;
    }
}