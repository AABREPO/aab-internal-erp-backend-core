package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.POBrandList;
import com.example.Dashboard2.Entity.POModelList;
import com.example.Dashboard2.Repository.POBrandListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class POBrandListService {

    @Autowired
    private POBrandListRepository poBrandListRepository;

    public POBrandList savePoBrandList(POBrandList poBrandList){
        String brand = poBrandList.getBrand().trim();
        String category = poBrandList.getCategory() != null ? poBrandList.getCategory().trim() : "";

        Optional<POBrandList> existing = poBrandListRepository.findByBrandIgnoreCaseAndCategoryIgnoreCase(brand, category);
        if (existing.isPresent()) {
            return null;
        }

        poBrandList.setBrand(brand);
        poBrandList.setCategory(category);
        return poBrandListRepository.save(poBrandList);
    }
    public List<POBrandList> getAllPOBrandList(){
        return poBrandListRepository.findAll();
    }
    public POBrandList updatePOBrandList(Long id, POBrandList poBrandList){
        Optional<POBrandList> existingPOBrandList = poBrandListRepository.findById(id);
        if (existingPOBrandList.isPresent()){
            POBrandList updatedPoBrandList = existingPOBrandList.get();
            updatedPoBrandList.setBrand(poBrandList.getBrand());
            updatedPoBrandList.setCategory(poBrandList.getCategory());
            return poBrandListRepository.save(updatedPoBrandList);
        } else {
            throw new RuntimeException("Brand not found" + id);
        }
    }
    public void deletePoBrand(Long id){
        if (poBrandListRepository.existsById(id)){
            poBrandListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Brand not found" + id);
        }
    }
    public void deleteAllPoBrandList(){
        poBrandListRepository.deleteAll();
    }


    public String uploadPOBrandLists(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        List<POBrandList> poBrandLists = new ArrayList<>();

        // Fetch existing brands with their categories to check for duplicates
        List<POBrandList> existingEntries = poBrandListRepository.findAll();
        Set<String> existingModelCategorySet = new HashSet<>();
        for (POBrandList existing : existingEntries) {
            existingModelCategorySet.add(existing.getBrand().toLowerCase() + "::" +
                    (existing.getCategory() != null ? existing.getCategory().toLowerCase() : ""));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String filename = file.getOriginalFilename();
            String line;

            if (filename != null && filename.endsWith(".csv")) {
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine && line.toLowerCase().contains("brand")) {
                        isFirstLine = false;
                        continue; // skip header
                    }
                    String[] values = line.split(",");
                    if (values.length >= 1) {
                        String brand = values[0].trim();
                        String category = values.length > 1 ? values[1].trim() : "";

                        String key = brand.toLowerCase() + "::" + category.toLowerCase();
                        if (!existingModelCategorySet.contains(key)) {
                            POBrandList poBrandList = new POBrandList();
                            poBrandList.setBrand(brand);
                            poBrandList.setCategory(category);
                            poBrandLists.add(poBrandList);
                            existingModelCategorySet.add(key); // Also avoid duplicates within the file
                        }
                    }
                }
            } else if (filename != null && filename.endsWith(".sql")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("INSERT INTO `pobrand_list`")) {
                        int startIndex = line.indexOf("VALUES (") + 8;
                        int endIndex = line.lastIndexOf(");");
                        if (startIndex > 0 && endIndex > startIndex) {
                            String valuesPart = line.substring(startIndex, endIndex);
                            String[] values = valuesPart.replaceAll("'", "").split(",");

                            if (values.length >= 1) {
                                String brand = values[0].trim();
                                String category = values.length > 1 ? values[1].trim() : "";

                                String key = brand.toLowerCase() + "::" + category.toLowerCase();
                                if (!existingModelCategorySet.contains(key)) {
                                    POBrandList poBrandList = new POBrandList();
                                    poBrandList.setBrand(brand);
                                    poBrandList.setCategory(category);
                                    poBrandLists.add(poBrandList);
                                    existingModelCategorySet.add(key);
                                }
                            }
                        }
                    }
                }
            } else {
                return "Unsupported file type. Please upload a CSV or SQL file.";
            }

            poBrandListRepository.saveAll(poBrandLists);
            return "File uploaded successfully! " + poBrandLists.size() + " new records saved.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }

}
