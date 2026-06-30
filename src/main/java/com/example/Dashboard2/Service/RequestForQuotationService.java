package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.RequestForQuotationHistoryRepository;
import com.example.Dashboard2.Repository.RequestForQuotationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RequestForQuotationService {

    @Autowired
    private RequestForQuotationRepository requestForQuotationRepository;

    @Autowired
    private RequestForQuotationHistoryRepository historyRepository;
    @Autowired
    private ObjectMapper objectMapper;

    // create or update a rfq
    public RequestForQuotation saveRfq(RequestForQuotation requestForQuotation){
        if (requestForQuotation.getCreatedDateTime() == null){
            requestForQuotation.setCreatedDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        return requestForQuotationRepository.save(requestForQuotation);
    }
    //get all rfq
    public List<RequestForQuotation> getAllRfq(){
        return requestForQuotationRepository.findAll();
    }
    //get last 150 data from this rfq
    public List<RequestForQuotation> getLast150Rfq(){
        return requestForQuotationRepository.findLatestRFQ(PageRequest.of(0,150));
    }
    //edit rfq data
    public RequestForQuotation editRFQ(Long id, RequestForQuotation updateRFQ){
        return requestForQuotationRepository.findById(id).map(existingRfq ->{
            existingRfq.setVendorId(updateRFQ.getVendorId());
            existingRfq.setClientId(updateRFQ.getClientId());
            existingRfq.setDate(updateRFQ.getDate());
            existingRfq.setSiteInchargeId(updateRFQ.getSiteInchargeId());
            existingRfq.setSiteInchargeMobileNumber(updateRFQ.getSiteInchargeMobileNumber());
            existingRfq.setENo(updateRFQ.getENo());
            existingRfq.setRfqTable(updateRFQ.getRfqTable());
            return requestForQuotationRepository.save(existingRfq);
        }).orElseThrow(() -> new RuntimeException(" Request For Quotation with Id" + id + "Not Found."));
    }
    // Delete Rfq by id
    public String deleteRfq(Long id){
        if (requestForQuotationRepository.existsById(id)){
            requestForQuotationRepository.deleteById(id);
            return "Request For Quotation with Id" + id + "deleted.";
        } else {
            throw new RuntimeException("Purchase Order with Id" + id + " not found.");
        }
    }
    //get RFQ by id
    public RequestForQuotation getRfqById(Long id){
        return requestForQuotationRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("RFQ with id not found"+ id));
    }
    // delete All RFQ data
    public String deleteAllRFQ(){
        requestForQuotationRepository.deleteAll();
        return "All RFQ data have been deleted.";
    }
    // change the delete status
    @Transactional
    public RequestForQuotation toggleDeletedStatus(Long id, boolean deleteStatus){
        RequestForQuotation rfq = requestForQuotationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("RFQ not found"));
        rfq.setDeleteStatus(deleteStatus);
        return requestForQuotationRepository.saveAndFlush(rfq);
    }
    // get the vendor count
    public Long countByVendorId(int vendorId){
        return requestForQuotationRepository.countByVendorId(vendorId);
    }
    //update the rfq notes
    public RequestForQuotation updateRFQNotes(Long id, RequestForQuotationNotes rfqNotes){
        return requestForQuotationRepository.findById(id).map(existingRfq ->{
            existingRfq.setRfqNotes(rfqNotes);
            return requestForQuotationRepository.save(existingRfq);
        }).orElseThrow(() -> new RuntimeException("RFQ with id not found" + id));
    }
    // edit the data with history
    public RequestForQuotation editRFQWithHistory(Long id, RequestForQuotation updateRFQ, String changedBy){
        return requestForQuotationRepository.findById(id).map(existingRfq ->{
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            List<RequestForQuotationHistory> histories = new ArrayList<>();
            if (existingRfq.getVendorId() != updateRFQ.getVendorId()){
                throw new RuntimeException("Vendor Id modification is not allowed");
            }
            /* =============== HEADER FIELD CHANGES =============== */
            if (existingRfq.getClientId() != updateRFQ.getClientId()){
                histories.add(history(id, "clientId",existingRfq.getClientId(),
                        updateRFQ.getClientId(), changedBy, now));
                existingRfq.setClientId(updateRFQ.getClientId());
            }
            if (!existingRfq.getDate().equals(updateRFQ.getDate())){
                histories.add(history(id, "date", existingRfq.getDate(),updateRFQ.getDate(),changedBy,now));
                existingRfq.setDate(updateRFQ.getDate());
            }
            if (existingRfq.getSiteInchargeId() != updateRFQ.getSiteInchargeId()){
                histories.add(history(id, "siteIchargeId", existingRfq.getSiteInchargeId(),
                        updateRFQ.getSiteInchargeId(), changedBy, now));
                existingRfq.setSiteInchargeId(updateRFQ.getSiteInchargeId());
            }
            if (existingRfq.getSiteInchargeType() != updateRFQ.getSiteInchargeType()){
                histories.add(history(id, "siteInchargeType", existingRfq.getSiteInchargeType(),
                        updateRFQ.getSiteInchargeType(), changedBy, now));
                existingRfq.setSiteInchargeId(updateRFQ.getSiteInchargeId());
            }
            /* ============================== RFQ TABLE ================================== */
            compareAndTrackTableChanges(existingRfq.getRfqTable(),
                    updateRFQ.getRfqTable(),
                    id,changedBy,now,histories
            );
            existingRfq.setRfqTable(updateRFQ.getRfqTable());
            historyRepository.saveAll(histories);
            return requestForQuotationRepository.save(existingRfq);
        }).orElseThrow(() -> new RuntimeException("RFQ not found"));
    }
    /* =================== TABLE COMPARISON LOGIC =================== */
    private void compareAndTrackTableChanges(
        List<RequestForQuotationTable> oldList,
        List<RequestForQuotationTable> newList,
        Long rfqId,
        String user,
        LocalDateTime time,
        List<RequestForQuotationHistory> histories
    ){
        Map<Long, RequestForQuotationTable> oldMap = oldList.stream()
                .collect(Collectors.toMap(RequestForQuotationTable::getId,t-> t));
        Map<Long, RequestForQuotationTable> newMap = newList.stream()
                .filter(t-> t.getId() != null)
                .collect(Collectors.toMap(RequestForQuotationTable::getId,t -> t));
        /* UPDATED ROWS */
        for (Long id : oldMap.keySet()){
            if (newMap.containsKey(id)){
                RequestForQuotationTable oldRow = oldMap.get(id);
                RequestForQuotationTable newRow = newMap.get(id);
                if (oldRow.getQuantity() != newRow.getQuantity()){
                    histories.add(history(
                            rfqId,
                            "TABLE:ITEM_ID="+ oldRow.getItemId() + "quantity",
                            oldRow.getQuantity(),
                            newRow.getQuantity(),
                            user, time
                    ));
                }
                if (oldRow.getAmount() != newRow.getAmount()){
                    histories.add(history(
                            rfqId,
                            "TABLE:ITEM_ID="+ oldRow.getAmount() + "amount",
                            oldRow.getAmount(),
                            newRow.getAmount(),
                            user, time
                    ));
                }
            }
        }
        /* ADDED ROWS*/
        for (RequestForQuotationTable row : newList){
            if (row.getId() == null){
                histories.add(history(
                        rfqId,
                        "TABLE ROW ADDED",
                        "N/A",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        user, time
                ));
            }
        }
        /* 🔹 DELETED ROWS */
        for (RequestForQuotationTable row : oldList) {
            if (!newMap.containsKey(row.getId())) {
                histories.add(history(
                        rfqId,
                        "TABLE ROW REMOVED",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        "REMOVED",
                        user, time
                ));
            }
        }
    }
    /* ===================== HISTORY BUILDER ======================== */
    private RequestForQuotationHistory history( Long rfqId, String field,
                                                Object oldVal, Object newVal, String user, LocalDateTime time){
        RequestForQuotationHistory h = new RequestForQuotationHistory();
        h.setRfqId(rfqId);
        h.setFieldName(field);
        h.setOldValue(String.valueOf(oldVal));
        h.setNewValue(String.valueOf(newVal));
        h.setChangedBy(user);
        h.setChangedAt(time);
        return h;
    }
    public List<RequestForQuotationHistory> getRFQHistory(Long rfqId){
        return historyRepository.findByRfqId(rfqId);
    }
}
