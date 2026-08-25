package com.smartresume.controller;

import java.io.IOException;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.smartresume.service.ResumeParserResult;
import com.smartresume.service.ResumeParserService;
import com.smartresume.service.ParsedResumeData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/PDFUploadServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 10 * 1024 * 1024,
        maxRequestSize = 15 * 1024 * 1024
)
public class PDFUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ResumeParserService resumeParserService;

    @Override
    public void init() throws ServletException {

        resumeParserService =
                new ResumeParserService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Check login session
        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        // Get uploaded file
        Part filePart =
                request.getPart("resumeFile");

        if (filePart == null
                || filePart.getSize() == 0) {

            request.setAttribute(
                    "error",
                    "Please select a PDF file."
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);

            return;
        }

        // Original filename
        String submittedName =
                filePart.getSubmittedFileName();

        if (submittedName == null
                || submittedName.trim().isEmpty()) {

            request.setAttribute(
                    "error",
                    "Invalid file name."
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);

            return;
        }

        // Check PDF extension
        if (!submittedName
                .toLowerCase()
                .endsWith(".pdf")) {

            request.setAttribute(
                    "error",
                    "Only PDF files are allowed."
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);

            return;
        }

        try {

            /*
             * ========================================
             * READ PDF FILE
             * ========================================
             */

            byte[] pdfBytes;

            try (InputStream inputStream =
                         filePart.getInputStream()) {

                pdfBytes =
                        inputStream.readAllBytes();
            }


            /*
             * ========================================
             * EXTRACT TEXT USING PDFBOX
             * ========================================
             */

            String extractedText;

            try (PDDocument document =
                         Loader.loadPDF(pdfBytes)) {

                PDFTextStripper stripper =
                        new PDFTextStripper();

                extractedText =
                        stripper.getText(document);
            }


            /*
             * ========================================
             * CHECK EXTRACTED TEXT
             * ========================================
             */

            if (extractedText == null
                    || extractedText.trim().isEmpty()) {

                request.setAttribute(
                        "error",
                        "No readable text was found in this PDF. "
                        + "Please upload a text-based PDF."
                );

                request.getRequestDispatcher(
                        "pdf-upload.jsp"
                ).forward(request, response);

                return;
            }


            /*
             * ========================================
             * PARSE RESUME TEXT
             * ========================================
             */

            ResumeParserResult parserResult =
                    resumeParserService.parse(
                            extractedText
                    );
            ParsedResumeData parsedData =
                    resumeParserService.parseStructuredData(
                            extractedText
                    );

            /*
             * ========================================
             * SAVE ORIGINAL PDF
             * ========================================
             */

            Integer userId =
                    (Integer) session.getAttribute(
                            "userId"
                    );

            String safeFileName =
                    "resume_" + userId + ".pdf";


            /*
             * getRealPath points to the deployed
             * application's uploads directory.
             */

            String realUploadPath =
                    getServletContext().getRealPath(
                            "/uploads"
                    );

            if (realUploadPath == null) {

                request.setAttribute(
                        "error",
                        "Upload directory could not be found."
                );

                request.getRequestDispatcher(
                        "pdf-upload.jsp"
                ).forward(request, response);

                return;
            }


            Path uploadDirectory =
                    Paths.get(realUploadPath);


            // Create uploads directory if missing
            Files.createDirectories(
                    uploadDirectory
            );


            Path targetFile =
                    uploadDirectory.resolve(
                            safeFileName
                    );


            // Save uploaded PDF
            Files.write(
                    targetFile,
                    pdfBytes
            );


            /*
             * ========================================
             * SEND DATA TO RESULT JSP
             * ========================================
             */

            request.setAttribute(
                    "extractedText",
                    extractedText
            );

            request.setAttribute(
                    "parserResult",
                    parserResult
            );
            request.setAttribute(
                    "parsedData",
                    parsedData
            );

            request.setAttribute(
                    "fileName",
                    submittedName
            );

            request.setAttribute(
                    "success",
                    "PDF uploaded and analyzed successfully."
            );


            /*
             * Go to result page
             */

            request.getRequestDispatcher(
                    "pdf-result.jsp"
            ).forward(request, response);


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to process PDF: "
                    + e.getMessage()
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);
        }
    }
}