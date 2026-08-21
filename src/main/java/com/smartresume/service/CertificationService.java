package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.CertificationDAO;
import com.smartresume.entity.Certification;
import com.smartresume.entity.Resume;

public class CertificationService {

    private final CertificationDAO certificationDAO;

    public CertificationService() {
        certificationDAO = new CertificationDAO();
    }

    public boolean addCertification(
            Resume resume,
            String certificationName,
            String issuingOrganization,
            String issueDate,
            String expiryDate,
            String credentialUrl) {

        Certification certification =
                new Certification();

        certification.setResume(resume);

        certification.setCertificationName(
                certificationName
        );

        certification.setIssuingOrganization(
                issuingOrganization
        );

        certification.setIssueDate(issueDate);

        certification.setExpiryDate(expiryDate);

        certification.setCredentialUrl(credentialUrl);

        return certificationDAO.saveCertification(
                certification
        );
    }

    public List<Certification> getCertificationsByResumeId(
            Long resumeId) {

        return certificationDAO.findByResumeId(
                resumeId
        );
    }

    public Certification getCertificationById(
            Long id) {

        return certificationDAO.findById(id);
    }

    public boolean updateCertification(
            Certification certification) {

        return certificationDAO.updateCertification(
                certification
        );
    }

    public boolean deleteCertification(
            Certification certification) {

        return certificationDAO.deleteCertification(
                certification
        );
    }
}