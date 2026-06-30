package com.example.Dashboard2.Service;

import com.example.Dashboard2.DTO.PaymentModeArrangementItemDTO;
import com.example.Dashboard2.DTO.PaymentModeArrangementRequest;
import com.example.Dashboard2.DTO.PaymentModeArrangementResponse;
import com.example.Dashboard2.Entity.PaymentMode;
import com.example.Dashboard2.Entity.PaymentModeArrangementForModules;
import com.example.Dashboard2.Repository.PaymentModeArrangementForModulesRepository;
import com.example.Dashboard2.Repository.PaymentModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentModeArrangementForModulesService {

    @Autowired
    private PaymentModeArrangementForModulesRepository repository;

    @Autowired
    private PaymentModeRepository paymentModeRepository;

    public List<PaymentModeArrangementResponse> getAll() {
        return repository.findAllByOrderByModuleNameAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    public PaymentModeArrangementResponse getByModuleName(String moduleName) {
        PaymentModeArrangementForModules arrangement = repository.findByModuleNameIgnoreCase(normalizeModuleName(moduleName))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Payment mode arrangement not found for module: " + moduleName));
        return toResponse(arrangement);
    }

    @Transactional
    public PaymentModeArrangementResponse saveOrUpdate(PaymentModeArrangementRequest request) {
        String moduleName = normalizeModuleName(request.getModuleName());
        if (moduleName.isEmpty()) {
            throw new IllegalArgumentException("module_name is required");
        }

        List<Long> orderedIds = sanitizeOrderedIds(request.getPaymentModeIds());
        validatePaymentModeIds(orderedIds);

        PaymentModeArrangementForModules existing = repository.findByModuleNameIgnoreCase(moduleName).orElse(null);
        if (existing != null) {
            existing.setPaymentModeIds(orderedIds);
            existing.setUpdatedAt(LocalDateTime.now());
            return toResponse(repository.save(existing));
        }

        PaymentModeArrangementForModules created = new PaymentModeArrangementForModules();
        created.setModuleName(moduleName);
        created.setPaymentModeIds(orderedIds);
        created.setUpdatedAt(LocalDateTime.now());
        return toResponse(repository.save(created));
    }

    @Transactional
    public PaymentModeArrangementResponse updateOrder(Long id, List<Long> paymentModeIds) {
        PaymentModeArrangementForModules existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment mode arrangement not found with id: " + id));

        List<Long> orderedIds = sanitizeOrderedIds(paymentModeIds);
        validatePaymentModeIds(orderedIds);

        existing.setPaymentModeIds(orderedIds);
        existing.setUpdatedAt(LocalDateTime.now());
        return toResponse(repository.save(existing));
    }

    @Transactional
    public PaymentModeArrangementResponse updateById(Long id, PaymentModeArrangementRequest request) {
        PaymentModeArrangementForModules existing = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment mode arrangement not found with id: " + id));

        if (request.getModuleName() != null && !request.getModuleName().trim().isEmpty()) {
            String moduleName = normalizeModuleName(request.getModuleName());
            if (!moduleName.equalsIgnoreCase(existing.getModuleName())
                    && repository.existsByModuleNameIgnoreCase(moduleName)) {
                throw new IllegalArgumentException("Payment mode arrangement already exists for module: " + moduleName);
            }
            existing.setModuleName(moduleName);
        }

        if (request.getPaymentModeIds() != null) {
            List<Long> orderedIds = sanitizeOrderedIds(request.getPaymentModeIds());
            validatePaymentModeIds(orderedIds);
            existing.setPaymentModeIds(orderedIds);
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return toResponse(repository.save(existing));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Payment mode arrangement not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private PaymentModeArrangementResponse toResponse(PaymentModeArrangementForModules arrangement) {
        PaymentModeArrangementResponse response = new PaymentModeArrangementResponse();
        response.setId(arrangement.getId());
        response.setModuleName(arrangement.getModuleName());
        response.setPaymentModeIds(new ArrayList<>(arrangement.getPaymentModeIds()));
        response.setUpdatedAt(arrangement.getUpdatedAt());
        response.setPaymentModes(resolvePaymentModes(arrangement.getPaymentModeIds()));
        return response;
    }

    private List<PaymentModeArrangementItemDTO> resolvePaymentModes(List<Long> paymentModeIds) {
        if (paymentModeIds == null || paymentModeIds.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, PaymentMode> paymentModeById = paymentModeRepository.findAllById(paymentModeIds).stream()
                .collect(Collectors.toMap(PaymentMode::getId, Function.identity()));

        List<PaymentModeArrangementItemDTO> items = new ArrayList<>();
        for (Long paymentModeId : paymentModeIds) {
            PaymentMode paymentMode = paymentModeById.get(paymentModeId);
            if (paymentMode == null) {
                continue;
            }
            PaymentModeArrangementItemDTO item = new PaymentModeArrangementItemDTO();
            item.setId(paymentMode.getId());
            item.setModeOfPayment(paymentMode.getModeOfPayment());
            items.add(item);
        }
        return items;
    }

    private void validatePaymentModeIds(List<Long> paymentModeIds) {
        if (paymentModeIds.isEmpty()) {
            throw new IllegalArgumentException("payment_mode_ids must contain at least one payment mode id");
        }

        List<PaymentMode> existingModes = paymentModeRepository.findAllById(paymentModeIds);
        if (existingModes.size() != paymentModeIds.size()) {
            throw new IllegalArgumentException("One or more payment_mode_ids are invalid");
        }
    }

    private String normalizeModuleName(String moduleName) {
        return moduleName == null ? "" : moduleName.trim();
    }

    private List<Long> sanitizeOrderedIds(List<Long> paymentModeIds) {
        if (paymentModeIds == null) {
            return new ArrayList<>();
        }
        LinkedHashSet<Long> orderedUnique = new LinkedHashSet<>();
        for (Long paymentModeId : paymentModeIds) {
            if (paymentModeId != null && paymentModeId > 0) {
                orderedUnique.add(paymentModeId);
            }
        }
        return new ArrayList<>(orderedUnique);
    }
}
