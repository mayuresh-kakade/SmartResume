package com.smartresume.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

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

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

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

        String submittedName =
                filePart.getSubmittedFileName();

        if (submittedName == null
                || !submittedName.toLowerCase()
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
             * Read uploaded PDF into memory.
             * We use PDFBox to extract text.
             */

            byte[] pdfBytes;

            try (InputStream inputStream =
                         filePart.getInputStream()) {

                pdfBytes = inputStream.readAllBytes();
            }

            /*
             * Extract text
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
             * Store original PDF
             */

            Integer userId =
                    (Integer) session.getAttribute(
                            "userId"
                    );

            String safeFileName =
                    "resume_" + userId + ".pdf";

            Path uploadDirectory =
                    Paths.get(
                            getServletContext()
                                    .getRealPath(
                                            "/uploads"
                                    )
                    );

            Files.createDirectories(
                    uploadDirectory
            );

            Path targetFile =
                    uploadDirectory.resolve(
                            safeFileName
                    );

            Files.write(
                    targetFile,
                    pdfBytes
            );

            /*
             * Send extracted text to JSP
             */

            request.setAttribute(
                    "extractedText",
                    extractedText
            );

            request.setAttribute(
                    "fileName",
                    submittedName
            );

            request.setAttribute(
                    "success",
                    "PDF uploaded and text extracted successfully."
            );

            request.getRequestDispatcher(
                    "pdf-result.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    "Unable to process the PDF: "
                            + e.getMessage()
            );

            request.getRequestDispatcher(
                    "pdf-upload.jsp"
            ).forward(request, response);
        }
    }
}