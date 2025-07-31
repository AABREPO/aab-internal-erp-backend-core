package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.POItemNameListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class POItemNameListService {

    @Autowired
    private POItemNameListRepo poItemNameListRepo;

    @Autowired
    private POModelListService poModelListService;

    @Autowired
    private POBrandListService poBrandListService;

    @Autowired
    private POTypeColorListService poTypeColorListService;


    public POItemNameList saveAllItemNameList(POItemNameList poItemNameList){
        return poItemNameListRepo.save(poItemNameList);
    }
    public List<POItemNameList> getAllPOItemNameList(){
        return poItemNameListRepo.findAll();
    }

    public POItemNameList editItemName(Long id, POItemNameList updatedPOItemName){
        Optional<POItemNameList> existingPOItemName = poItemNameListRepo.findById(id);
        if (existingPOItemName.isPresent()){
            POItemNameList pOItemName = existingPOItemName.get();
            pOItemName.setItemName(updatedPOItemName.getItemName());
            pOItemName.setCategory(updatedPOItemName.getCategory());
            pOItemName.setGroupName(updatedPOItemName.getGroupName());
            pOItemName.setOtherPOEntityList(updatedPOItemName.getOtherPOEntityList());
            return poItemNameListRepo.save(pOItemName);
        }
        throw new RuntimeException("PO Item Name With Id not found: " + id);
    }

    public String deletePOItemNameList(Long id){
        if (poItemNameListRepo.existsById(id)){
            poItemNameListRepo.deleteById(id);
            return "PO Item Name With Id had deleted"+ id;
        }
        throw new RuntimeException("PO Item Name not found" + id);
    }
    public String deleteAllPOItemNameList(){
        poItemNameListRepo.deleteAll();
        return "All Item Name have been deleted";
    }

    public String uploadCSV(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            boolean isHeader = true;
            Map<String, POItemNameList> itemMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] tokens = line.split(",", -1); // Allow empty strings

                if (tokens.length < 8) continue;

                String itemName = tokens[0].trim();
                String category = tokens[1].trim();
                String groupName = tokens[2].trim();
                String model = tokens[3].trim();
                String brand = tokens[4].trim();
                String type = tokens[5].trim();
                String minQty = tokens[6].trim();
                String defaultQty = tokens[7].trim();

                // Save model if not duplicate
                try {
                    POModelList modelEntity = new POModelList();
                    modelEntity.setModel(model);
                    modelEntity.setCategory(category);
                    poModelListService.savePoModelList(modelEntity);
                } catch (RuntimeException ignored) {}

                // Save brand if not duplicate
                try {
                    POBrandList brandEntity = new POBrandList();
                    brandEntity.setBrand(brand);
                    brandEntity.setCategory(category);
                    poBrandListService.savePoBrandList(brandEntity);
                } catch (RuntimeException ignored) {}

                // Save typeColor if not duplicate
                try {
                    POTypeColorList typeColorEntity = new POTypeColorList();
                    typeColorEntity.setTypeColor(type);
                    typeColorEntity.setCategory(category);
                    poTypeColorListService.savePoTypeColorList(typeColorEntity);
                } catch (RuntimeException ignored) {}

                // POItemNameList logic
                POItemNameList poItem = itemMap.getOrDefault(itemName, new POItemNameList());
                poItem.setItemName(itemName);
                poItem.setCategory(category);
                poItem.setGroupName(groupName);

                if (poItem.getOtherPOEntityList() == null) {
                    poItem.setOtherPOEntityList(new ArrayList<>());
                }

                POItemNameListWithAllOtherPOEntity subEntity = new POItemNameListWithAllOtherPOEntity();
                subEntity.setModelName(model);
                subEntity.setBrandName(brand);
                subEntity.setTypeColor(type);
                subEntity.setMinimumQty(minQty);
                subEntity.setDefaultQty(defaultQty);

                poItem.getOtherPOEntityList().add(subEntity);
                itemMap.put(itemName, poItem);
            }

            poItemNameListRepo.saveAll(itemMap.values());
            return "CSV uploaded and data saved successfully.";

        } catch (Exception e) {
            throw new RuntimeException("Error while processing CSV file: " + e.getMessage(), e);
        }
    }


    public String uploadPOItemNameLists(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a file!";
        }
        List<POItemNameList> poItemNameLists = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String filename = file.getOriginalFilename();
            String line;
            if (filename != null && filename.endsWith(".csv")) {
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false; // skip header
                        continue;
                    }
                    String[] values = line.split(",");
                    if (values.length >= 3) {
                        POItemNameList item = new POItemNameList();
                        item.setItemName(values[0].trim());
                        item.setCategory(values[1].trim());
                        item.setGroupName(values[2].trim());
                        poItemNameLists.add(item);
                    }
                }

            } else if (filename != null && filename.endsWith(".sql")) {
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("INSERT INTO `poitem_name_list`")) {
                        int valuesIndex = line.indexOf("VALUES (");
                        if (valuesIndex != -1) {
                            String valuesPart = line.substring(valuesIndex + 8, line.lastIndexOf(")"));
                            String[] values = valuesPart.split(",");
                            if (values.length >= 3) {
                                POItemNameList item = new POItemNameList();
                                item.setItemName(stripQuotes(values[0].trim()));
                                item.setCategory(stripQuotes(values[1].trim()));
                                item.setGroupName(stripQuotes(values[2].trim()));
                                poItemNameLists.add(item);
                            }
                        }
                    }
                }
            } else {
                return "Unsupported file type. Please upload a CSV or SQL file.";
            }
            poItemNameListRepo.saveAll(poItemNameLists);
            return "File uploaded successfully! " + poItemNameLists.size() + " records saved.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
    private String stripQuotes(String input) {
        if (input.startsWith("'") && input.endsWith("'")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }

}
