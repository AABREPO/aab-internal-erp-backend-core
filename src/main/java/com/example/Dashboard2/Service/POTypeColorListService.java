package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.POBrandList;
import com.example.Dashboard2.Entity.POModelList;
import com.example.Dashboard2.Entity.POTypeColorList;
import com.example.Dashboard2.Repository.POTypeColorListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class POTypeColorListService {

    @Autowired
    private POTypeColorListRepository poTypeColorListRepository;

    public POTypeColorList savePoTypeColorList(POTypeColorList poTypeColorList){
        String typeColor = poTypeColorList.getTypeColor().trim();
        String category = poTypeColorList.getCategory() !=null ? poTypeColorList.getCategory().trim() : "";

        Optional<POTypeColorList> existing = poTypeColorListRepository.findByTypeColorIgnoreCaseAndCategoryIgnoreCase(typeColor, category);
        if (existing.isPresent()){
            throw new RuntimeException("Duplicate entry: Type or Color already exists for this category. ");
        }

        return poTypeColorListRepository.save(poTypeColorList);
    }

    public List<POTypeColorList> getAllPoTypeColorList(){
        return poTypeColorListRepository.findAll();
    }
    public POTypeColorList updatePOTypeColorList(Long id, POTypeColorList poTypeColorList){
        Optional<POTypeColorList> existingPOTypeColorList = poTypeColorListRepository.findById(id);
        if (existingPOTypeColorList.isPresent()){
            POTypeColorList updatedPoTypeColorList = existingPOTypeColorList.get();
            updatedPoTypeColorList.setTypeColor(poTypeColorList.getTypeColor());
            updatedPoTypeColorList.setCategory(poTypeColorList.getCategory());
            return poTypeColorListRepository.save(updatedPoTypeColorList);
        } else {
            throw new RuntimeException("Type / Color not found"+ id);
        }
    }
    public void deletePoTypeColor(Long id){
        if (poTypeColorListRepository.existsById(id)){
            poTypeColorListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Type / Color not found" + id);
        }
    }
    public void deleteAllPoTypeColorList(){
        poTypeColorListRepository.deleteAll();
    }

    public String uploadPOTypeLists(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }

        List<POTypeColorList> poTypeColorLists = new ArrayList<>();

        //Fetch existing Type or Color with their categories to check for duplicates
        List<POTypeColorList> existingEntries = poTypeColorListRepository.findAll();
        Set<String> existingTypeColorCategorySet = new HashSet<>();
        for (POTypeColorList existing : existingEntries){
            existingTypeColorCategorySet.add(existing.getTypeColor().toLowerCase() + "::"+
                    (existing.getCategory() !=null ? existing.getCategory().toLowerCase(): ""));
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String filename = file.getOriginalFilename();
            String line;

            if (filename != null && filename.endsWith(".csv")) {
                boolean isFirstLine = true;
                // CSV file handling
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine && line.toLowerCase().contains("type color")){
                        isFirstLine = false;
                        continue; // skip header
                    }
                    String[] values = line.split(",");
                    if (values.length > 1) {
                        String typeColor = values[0].trim();
                        String category = values.length >1 ? values[1].trim() : "";

                        String key = typeColor.toLowerCase() + "::" + category.toLowerCase();
                        if (!existingTypeColorCategorySet.contains(key)) {
                            POTypeColorList poTypeColorList = new POTypeColorList();
                            poTypeColorList.setTypeColor(typeColor);
                            poTypeColorList.setCategory(category);
                            poTypeColorLists.add(poTypeColorList);
                            existingTypeColorCategorySet.add(key);
                        }
                    }
                }
            } else if (filename != null && filename.endsWith(".sql")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("INSERT INTO `potype_color_list`")) {
                        int startIndex = line.indexOf("VALUES (") + 8;
                        int endIndex = line.lastIndexOf(");");
                        if (startIndex > 0 && endIndex > startIndex) {
                            String valuesPart = line.substring(startIndex, endIndex);
                            String[] values = valuesPart.replaceAll("'", "").split(",");

                            if (values.length >= 1) {
                                String typeColor = values[0].trim();
                                String category = values.length > 1 ? values[1].trim() : "";

                                String key = typeColor.toLowerCase() + "::" + category.toLowerCase();
                                if (!existingTypeColorCategorySet.contains(key)) {
                                    POTypeColorList poTypeColorList = new POTypeColorList();
                                    poTypeColorList.setTypeColor(typeColor);
                                    poTypeColorList.setCategory(category);
                                    poTypeColorLists.add(poTypeColorList);
                                    existingTypeColorCategorySet.add(key);
                                }
                            }
                        }
                    }
                }
            } else {
                return "Unsupported file type. Please upload a CSV or SQL file.";
            }

            poTypeColorListRepository.saveAll(poTypeColorLists);
            return "File uploaded successfully! " + poTypeColorLists.size() + " records saved.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }

}
