package com.smartresume.service;

import java.util.List;

import com.smartresume.dao.CompanyDAO;
import com.smartresume.entity.Company;

public class CompanyService {

    private final CompanyDAO companyDAO;

    public CompanyService() {
        companyDAO = new CompanyDAO();
    }

    public String addCompany(
            String name,
            String industry,
            String website,
            String location) {

        if (name == null || name.trim().isEmpty()) {
            return "INVALID";
        }

        Company existing =
                companyDAO.findByName(name.trim());

        if (existing != null) {
            return "EXISTS";
        }

        Company company = new Company();

        company.setName(name.trim());
        company.setIndustry(industry);
        company.setWebsite(website);
        company.setLocation(location);

        boolean saved =
                companyDAO.saveCompany(company);

        return saved ? "SUCCESS" : "FAILED";
    }

    public List<Company> getAllCompanies() {

        return companyDAO.findAllCompanies();
    }

    public Company getCompanyById(Long id) {

        return companyDAO.findById(id);
    }

    public boolean updateCompany(Company company) {

        return companyDAO.updateCompany(company);
    }

    public boolean deleteCompany(Company company) {

        return companyDAO.deleteCompany(company);
    }
}