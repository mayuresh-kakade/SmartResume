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


    public ParsedResumeData parseStructuredData(
            String text) {

        ResumeParserResult result =
                parse(text);

        ParsedResumeData data =
                new ParsedResumeData();

        Map<String, List<String>> sections =
                result.getSections();


        /*
         * SUMMARY
         */

        List<String> summaryLines =
                sections.get("SUMMARY");

        if (summaryLines != null
                && !summaryLines.isEmpty()) {

            data.setSummary(
                    String.join(
                            " ",
                            summaryLines
                    )
            );
        }


        /*
         * EDUCATION
         */

        if (sections.get("EDUCATION") != null) {

            data.setEducation(
                    new ArrayList<>(
                            sections.get("EDUCATION")
                    )
            );
        }


        /*
         * EXPERIENCE
         */

        if (sections.get("EXPERIENCE") != null) {

            data.setExperience(
                    new ArrayList<>(
                            sections.get("EXPERIENCE")
                    )
            );
        }


        /*
         * SKILLS
         */

        if (sections.get("SKILLS") != null) {

            data.setSkills(
                    new ArrayList<>(
                            sections.get("SKILLS")
                    )
            );
        }


        /*
         * PROJECTS
         */

        if (sections.get("PROJECTS") != null) {

            data.setProjects(
                    new ArrayList<>(
                            sections.get("PROJECTS")
                    )
            );
        }


        /*
         * CERTIFICATIONS
         */

        if (sections.get("CERTIFICATIONS") != null) {

            data.setCertifications(
                    new ArrayList<>(
                            sections.get("CERTIFICATIONS")
                    )
            );
        }


        /*
         * BASIC INFO
         */

        extractBasicInformation(
                text,
                data
        );

        return data;
    }


    private void extractBasicInformation(
            String text,
            ParsedResumeData data) {

        if (text == null) {
            return;
        }

        String[] lines =
                text.split("\\R");


        /*
         * Detect email
         */

        for (String rawLine : lines) {

            String line =
                    rawLine.trim();

            if (line.matches(
                    ".*[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}.*")) {

                String email =
                        line.replaceAll(
                                ".*?([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}).*",
                                "$1"
                        );

                data.setEmail(email);

                break;
            }
        }


        /*
         * Detect phone
         */

        for (String rawLine : lines) {

            String line =
                    rawLine.trim();

            if (line.matches(
                    ".*(\\+91[- ]?)?[6-9][0-9]{9}.*")) {

                String phone =
                        line.replaceAll(
                                "[^0-9+]",
                                ""
                        );

                data.setPhone(phone);

                break;
            }
        }


        /*
         * First non-empty line is treated
         * as possible name
         */

        for (String rawLine : lines) {

            String line =
                    rawLine.trim();

            if (!line.isEmpty()
                    && !line.contains("@")
                    && !line.matches(
                            ".*\\d{7,}.*")) {

                data.setName(line);

                break;
            }
        }
    }


    public Map<String, List<String>> parseResumeText(
            String text) {

        Map<String, List<String>> sections =
                new LinkedHashMap<>();

        sections.put(
                "SUMMARY",
                new ArrayList<>()
        );

        sections.put(
                "EDUCATION",
                new ArrayList<>()
        );

        sections.put(
                "EXPERIENCE",
                new ArrayList<>()
        );

        sections.put(
                "SKILLS",
                new ArrayList<>()
        );

        sections.put(
                "PROJECTS",
                new ArrayList<>()
        );

        sections.put(
                "CERTIFICATIONS",
                new ArrayList<>()
        );


        if (text == null
                || text.trim().isEmpty()) {

            return sections;
        }


        String[] lines =
                text.split("\\R");

        String currentSection = null;


        for (String rawLine : lines) {

            String line =
                    rawLine.trim();

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


    private String detectSection(
            String line) {

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