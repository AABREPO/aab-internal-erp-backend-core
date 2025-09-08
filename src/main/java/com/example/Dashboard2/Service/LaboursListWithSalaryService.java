package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.LaboursListWithSalary;
import com.example.Dashboard2.Entity.POBrandList;
import com.example.Dashboard2.Repository.LaboursListWithSalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LaboursListWithSalaryService {

    @Autowired
    private LaboursListWithSalaryRepository laboursListWithSalaryRepository;

    public LaboursListWithSalary saveLaboursListWithSalary(LaboursListWithSalary laboursListWithSalary){
        return laboursListWithSalaryRepository.save(laboursListWithSalary);
    }
    public List<LaboursListWithSalary> getAllLaboursListWithSalary(){
        return laboursListWithSalaryRepository.findAll();
    }
    public void deleteLabour(Long id){
        laboursListWithSalaryRepository.deleteById(id);
    }
    public void deleteAllLaboursList(){
        laboursListWithSalaryRepository.deleteAll();
    }
    public LaboursListWithSalary updateLabour(Long id, LaboursListWithSalary updatedLabour) {
        return laboursListWithSalaryRepository.findById(id)
                .map(existingLabour -> {
                    existingLabour.setLabourName(updatedLabour.getLabourName());
                    existingLabour.setLabourSalary(updatedLabour.getLabourSalary());
                    return laboursListWithSalaryRepository.save(existingLabour);
                })
                .orElseThrow(() -> new RuntimeException("Labour not found with id " + id));
    }
    public String uploadLaboursList(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }
        List<LaboursListWithSalary> laboursListWithSalaries = new ArrayList<>();
        // Fetch existing entries to check for duplicates
        List<LaboursListWithSalary> existingEntries = laboursListWithSalaryRepository.findAll();
        Set<String> existingLabourSalary = new HashSet<>();
        for (LaboursListWithSalary existing : existingEntries) {
            existingLabourSalary.add(existing.getLabourName().toLowerCase() + "::" + existing.getLabourSalary());
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String filename = file.getOriginalFilename();
            String line;
            if (filename != null && filename.endsWith(".csv")) {
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine && line.toLowerCase().contains("labour_name")) {
                        isFirstLine = false;
                        continue; // skip header
                    }
                    String[] values = line.split(",");
                    if (values.length >= 1) {
                        String labourName = values[0].trim();
                        double labourSalary = 0.0;
                        if (values.length > 2 && !values[2].trim().isEmpty()) {
                            try {
                                labourSalary = Double.parseDouble(values[2].trim());
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid salary value for labour: " + labourName + " -> " + values[2]);
                                continue; // skip invalid record
                            }
                        }
                        String labourJob = values[1].trim();
                        double extraAmount = 0.0;
                        if (values.length > 3 && !values[3].trim().isEmpty()){
                            try {
                                extraAmount = Double.parseDouble(values[3].trim());
                            } catch (NumberFormatException e){
                                System.err.println("Invalid extra value for labour: " + labourName + " ->" + values[3]);
                                continue;
                            }
                        }
                        String key = labourName.toLowerCase() + "::" + labourSalary;
                        if (!existingLabourSalary.contains(key)) {
                            LaboursListWithSalary laboursListWithSalary = new LaboursListWithSalary();
                            laboursListWithSalary.setLabourName(labourName);
                            laboursListWithSalary.setLabourSalary(labourSalary);
                            laboursListWithSalary.setJob(labourJob);
                            laboursListWithSalary.setExtra(extraAmount);
                            laboursListWithSalaries.add(laboursListWithSalary);
                            existingLabourSalary.add(key);
                        }
                    }
                }
            } else if (filename != null && filename.endsWith(".sql")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("INSERT INTO `labours_list_with_salary`")) {
                        int startIndex = line.indexOf("VALUES (") + 8;
                        int endIndex = line.lastIndexOf(");");
                        if (startIndex > 0 && endIndex > startIndex) {
                            String valuesPart = line.substring(startIndex, endIndex);
                            String[] values = valuesPart.replaceAll("'", "").split(",");
                            if (values.length >= 1) {
                                String labourName = values[0].trim();
                                String labourJob = values[1].trim();
                                double labourSalary = 0.0;
                                if (values.length > 2 && !values[2].trim().isEmpty()) {
                                    try {
                                        labourSalary = Double.parseDouble(values[2].trim());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Invalid salary value for labour: " + labourName + " -> " + values[2]);
                                        continue; // skip invalid record
                                    }
                                }
                                double extraAmount = 0.0;
                                if (values.length >3 && !values[3].trim().isEmpty()){
                                    try {
                                        extraAmount = Double.parseDouble(values[3].trim());
                                    } catch (NumberFormatException e){
                                        System.err.println("Invalid extra value for labour: "+ labourName + " ->" + values[3]);
                                        continue;
                                    }
                                }
                                String key = labourName.toLowerCase() + "::" + labourSalary;
                                if (!existingLabourSalary.contains(key)) {
                                    LaboursListWithSalary laboursListWithSalary = new LaboursListWithSalary();
                                    laboursListWithSalary.setLabourName(labourName);
                                    laboursListWithSalary.setLabourSalary(labourSalary);
                                    laboursListWithSalary.setJob(labourJob);
                                    laboursListWithSalary.setExtra(extraAmount);
                                    laboursListWithSalaries.add(laboursListWithSalary);
                                    existingLabourSalary.add(key);
                                }
                            }
                        }
                    }
                }
            } else {
                return "Unsupported file type. Please upload a CSV or SQL file.";
            }
            laboursListWithSalaryRepository.saveAll(laboursListWithSalaries);
            return "File uploaded successfully! " + laboursListWithSalaries.size() + " new records saved.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
}
