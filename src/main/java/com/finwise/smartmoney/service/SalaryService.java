package com.finwise.smartmoney.service;

import com.finwise.smartmoney.dto.SalaryRequestDTO;
import com.finwise.smartmoney.dto.SalaryResponseDTO;
import com.finwise.smartmoney.entity.Salary;
import com.finwise.smartmoney.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalaryService {

    @Autowired
    SalaryRepository salaryRepository;

    public SalaryResponseDTO saveSalary(SalaryRequestDTO salaryRequestDto) {
        // Convert DTO to Entity
        Salary salary = new Salary();
        salary.setAmount(salaryRequestDto.getAmount());
        salary.setMonth(salaryRequestDto.getMonth());
        salary.setYear(salaryRequestDto.getYear());
        salary.setCreatedAt(LocalDateTime.now());
        salary.setUpdatedAt(LocalDateTime.now());

        // Save the entity to the database
        Salary savedSalary = salaryRepository.save(salary);

        // Convert the saved entity to a DTO to return it as a response
        return mapToDTO(savedSalary);
    }

    public List<SalaryResponseDTO> getAllSalaries() {
        List<Salary> salaries = salaryRepository.findAll();
        return salaries
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // Helper method to map entity to DTO
    private SalaryResponseDTO mapToDTO(Salary salary) {
        SalaryResponseDTO salaryResponseDTO = new SalaryResponseDTO();
        salaryResponseDTO.setId(salary.getId());
        salaryResponseDTO.setAmount(salary.getAmount());
        salaryResponseDTO.setMonth(salary.getMonth());
        salaryResponseDTO.setYear(salary.getYear());
        salaryResponseDTO.setCreatedAt(salary.getCreatedAt());
        salaryResponseDTO.setUpdatedAt(salary.getUpdatedAt());
        return salaryResponseDTO;
    }
}
