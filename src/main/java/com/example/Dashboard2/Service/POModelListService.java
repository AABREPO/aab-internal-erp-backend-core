package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.POModelList;
import com.example.Dashboard2.Repository.POModelListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class POModelListService {

    @Autowired
    private POModelListRepository poModelListRepository;

    public POModelList savePoModelList(POModelList poModelList) {
        String model = poModelList.getModel().trim();
        String category = poModelList.getCategory() != null ? poModelList.getCategory().trim() : "";

        Optional<POModelList> existing = poModelListRepository.findByModelIgnoreCaseAndCategoryIgnoreCase(model, category);
        if (existing.isPresent()) {
            return null; // Already exists, no need to save
        }

        poModelList.setModel(model);
        poModelList.setCategory(category);
        return poModelListRepository.save(poModelList);
    }

    public List<POModelList> getAllPOModelList(){
        return poModelListRepository.findAll();
    }
    public POModelList getPOModalById(Long id){
        return poModelListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PO Modal With ID" + id + "Not Found"));
    }

    public POModelList updatePOModelList(Long id, POModelList poModelList){
        Optional<POModelList> existingPoModelList = poModelListRepository.findById(id);
        if (existingPoModelList.isPresent()){
            POModelList updatedPoModelList = existingPoModelList.get();
            updatedPoModelList.setModel(poModelList.getModel());
            updatedPoModelList.setCategory(poModelList.getCategory());
            return poModelListRepository.save(updatedPoModelList);
        } else {
            throw new RuntimeException("Model not found" + id);
        }
    }
    public void deletePoModel(Long id){
        if (poModelListRepository.existsById(id)){
            poModelListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Model not found" + id);
        }
    }
    public void deleteAllPoModelList(){
        poModelListRepository.deleteAll();
    }

    public String uploadPOModels(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        List<POModelList> poModelLists = new ArrayList<>();

        // Fetch existing models with their categories to check for duplicates
        List<POModelList> existingEntries = poModelListRepository.findAll();
        Set<String> existingModelCategorySet = new HashSet<>();
        for (POModelList existing : existingEntries) {
            existingModelCategorySet.add(existing.getModel().toLowerCase() + "::" +
                    (existing.getCategory() != null ? existing.getCategory().toLowerCase() : ""));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String filename = file.getOriginalFilename();
            String line;

            if (filename != null && filename.endsWith(".csv")) {
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine && line.toLowerCase().contains("model")) {
                        isFirstLine = false;
                        continue; // skip header
                    }
                    String[] values = line.split(",");
                    if (values.length >= 1) {
                        String model = values[0].trim();
                        String category = values.length > 1 ? values[1].trim() : "";

                        String key = model.toLowerCase() + "::" + category.toLowerCase();
                        if (!existingModelCategorySet.contains(key)) {
                            POModelList poModelList = new POModelList();
                            poModelList.setModel(model);
                            poModelList.setCategory(category);
                            poModelLists.add(poModelList);
                            existingModelCategorySet.add(key); // Also avoid duplicates within the file
                        }
                    }
                }
            } else if (filename != null && filename.endsWith(".sql")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("INSERT INTO `pomodel_list`")) {
                        int startIndex = line.indexOf("VALUES (") + 8;
                        int endIndex = line.lastIndexOf(");");
                        if (startIndex > 0 && endIndex > startIndex) {
                            String valuesPart = line.substring(startIndex, endIndex);
                            String[] values = valuesPart.replaceAll("'", "").split(",");

                            if (values.length >= 1) {
                                String model = values[0].trim();
                                String category = values.length > 1 ? values[1].trim() : "";

                                String key = model.toLowerCase() + "::" + category.toLowerCase();
                                if (!existingModelCategorySet.contains(key)) {
                                    POModelList poModelList = new POModelList();
                                    poModelList.setModel(model);
                                    poModelList.setCategory(category);
                                    poModelLists.add(poModelList);
                                    existingModelCategorySet.add(key);
                                }
                            }
                        }
                    }
                }
            } else {
                return "Unsupported file type. Please upload a CSV or SQL file.";
            }

            poModelListRepository.saveAll(poModelLists);
            return "File uploaded successfully! " + poModelLists.size() + " new records saved.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }


}
