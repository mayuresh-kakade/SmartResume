package com.smartresume.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ResumeParserService {

    public ResumeParserResult parse(String text) {

        Map<String, List<String>> sections =
                parseResumeText(text);

        return new ResumeParserResult(
                text,
                sections
        );
    }

    public Map<String, List<String>> parseResumeText(
            String text) {

        Map<String, List<String>> sections =
                new LinkedHashMap<>();

        sections.put("SUMMARY", new ArrayList<>());
        sections.put("EDUCATION", new ArrayList<>());
        sections.put("EXPERIENCE", new ArrayList<>());
        sections.put("SKILLS", new ArrayList<>());
        sections.put("PROJECTS", new ArrayList<>());
        sections.put("CERTIFICATIONS", new ArrayList<>());

        if (text == null || text.trim().isEmpty()) {

            return sections;
        }

        String[] lines = text.split("\\R");

        String currentSection = null;

        for (String rawLine : lines) {

            String line = rawLine.trim();

            if (line.isEmpty()) {
                continue;
            }

            String detectedSection =
                    detectSection(line);

            if (detectedSection != null) {

                currentSection =
                        detectedSection;

                continue;
            }

            if (currentSection != null) {

                sections
                        .get(currentSection)
                        .add(line);
            }
        }

        return sections;
    }

    private String detectSection(String line) {

        String normalized =
                line
                        .toLowerCase(Locale.ROOT)
                        .replace(":", "")
                        .trim();

        switch (normalized) {

            case "summary":
            case "professional summary":
            case "profile":
            case "about me":
                return "SUMMARY";

            case "education":
            case "academic background":
            case "educational qualification":
                return "EDUCATION";

            case "experience":
            case "work experience":
            case "professional experience":
                return "EXPERIENCE";

            case "skills":
            case "technical skills":
            case "technical skill":
                return "SKILLS";

            case "projects":
            case "project":
            case "academic projects":
                return "PROJECTS";

            case "certifications":
            case "certificates":
            case "certification":
                return "CERTIFICATIONS";

            default:
                return null;
        }
    }
}