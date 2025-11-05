package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.EbServiceLinkWithProjectID;
import com.example.Dashboard2.Repository.EbServiceLinkWithProjectIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EbServiceLinkWithProjectIDService {

    @Autowired
    private EbServiceLinkWithProjectIDRepository ebServiceLinkWithProjectIDRepository;

    // Save single record
    public EbServiceLinkWithProjectID saveEbServiceNo(EbServiceLinkWithProjectID ebServiceLinkWithProjectID) {
        if (ebServiceLinkWithProjectID.getTimestamp() == null) {
            ebServiceLinkWithProjectID.setTimestamp(LocalDateTime.now());
        }
        return ebServiceLinkWithProjectIDRepository.save(ebServiceLinkWithProjectID);
    }

    // Get all records
    public List<EbServiceLinkWithProjectID> getAllEbServiceNos() {
        return ebServiceLinkWithProjectIDRepository.findAll();
    }

    // Update record by ID
    public EbServiceLinkWithProjectID updateEbServiceNos(Long id, EbServiceLinkWithProjectID ebServiceLinkWithProjectID) {
        Optional<EbServiceLinkWithProjectID> existing = ebServiceLinkWithProjectIDRepository.findById(id);

        if (existing.isPresent()) {
            EbServiceLinkWithProjectID updated = existing.get();
            updated.setTimestamp(LocalDateTime.now());
            updated.setEbServiceNo(ebServiceLinkWithProjectID.getEbServiceNo());
            updated.setDoorNo(ebServiceLinkWithProjectID.getDoorNo());
            updated.setProjectId(ebServiceLinkWithProjectID.getProjectId());
            return ebServiceLinkWithProjectIDRepository.save(updated);
        } else {
            throw new RuntimeException("Eb Service No not found with ID: " + id);
        }
    }

    // Upload & parse SQL file containing EB service data
    public String uploadEbServiceNoData(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty. Please upload a valid SQL file.";
        }
        List<EbServiceLinkWithProjectID> recordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("INSERT INTO")) {
                    // Example: INSERT INTO `eb_service_link_with_projectid` (`project_id`, `door_no`, `eb_service_no`) VALUES (1, 'A1', 'EB123');
                    int valuesIndex = line.indexOf("VALUES");
                    if (valuesIndex == -1) continue;
                    String valuesPart = line.substring(valuesIndex + 6).trim();
                    valuesPart = valuesPart.replaceAll("^\\(|\\);?$", ""); // remove leading/trailing brackets
                    // Split into tuples if multiple
                    String[] tuples = valuesPart.split("\\),\\s*\\(");
                    for (String tuple : tuples) {
                        String[] fields = tuple.replace("'", "").split(",");
                        if (fields.length < 3) continue;
                        EbServiceLinkWithProjectID record = new EbServiceLinkWithProjectID();
                        try {
                            record.setProjectId(Long.parseLong(fields[0].trim()));
                        } catch (Exception e) {
                            record.setProjectId(null);
                        }
                        record.setDoorNo(fields[1].trim());
                        record.setEbServiceNo(fields[2].trim());
                        record.setTimestamp(LocalDateTime.now());
                        recordList.add(record);
                    }
                }
            }
            if (recordList.isEmpty()) {
                return "No valid records found in the SQL file.";
            }
            ebServiceLinkWithProjectIDRepository.saveAll(recordList);
            return "Successfully uploaded " + recordList.size() + " EB service records.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
    public void deleteEbServiceNoById(Long id){
        ebServiceLinkWithProjectIDRepository.deleteById(id);
    }
}