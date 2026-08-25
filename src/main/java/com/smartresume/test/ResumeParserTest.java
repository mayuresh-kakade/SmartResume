package com.smartresume.test;

import com.smartresume.service.ParsedResumeData;
import com.smartresume.service.ResumeParserService;

public class ResumeParserTest {

    public static void main(String[] args) {

        String sampleResume =
                """
                Mayuresh Kakade
                mayuresh@gmail.com
                9112737264

                SUMMARY

                Java developer with experience in
                Hibernate and MySQL.

                EDUCATION

                B.E. Computer Engineering
                ABC College
                2026

                EXPERIENCE

                Java Developer
                XYZ Technologies
                Pune

                SKILLS

                Java
                Hibernate
                MySQL
                Git

                PROJECTS

                SmartResume
                Java JSP Hibernate MySQL

                CERTIFICATIONS

                Java Programming Certification
                """;


        ResumeParserService parser =
                new ResumeParserService();

        ParsedResumeData data =
                parser.parseStructuredData(
                        sampleResume
                );


        System.out.println(
                "Name: " + data.getName()
        );

        System.out.println(
                "Email: " + data.getEmail()
        );

        System.out.println(
                "Phone: " + data.getPhone()
        );

        System.out.println(
                "Summary: " + data.getSummary()
        );


        System.out.println(
                "\nEducation:"
        );

        data.getEducation()
                .forEach(System.out::println);


        System.out.println(
                "\nExperience:"
        );

        data.getExperience()
                .forEach(System.out::println);


        System.out.println(
                "\nSkills:"
        );

        data.getSkills()
                .forEach(System.out::println);


        System.out.println(
                "\nProjects:"
        );

        data.getProjects()
                .forEach(System.out::println);


        System.out.println(
                "\nCertifications:"
        );

        data.getCertifications()
                .forEach(System.out::println);
    }
}