package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.Tenant;
import com.example.Dashboard2.Entity.TenantGroup;
import com.example.Dashboard2.Repository.TenantGroupRepository;
import com.example.Dashboard2.Repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TenantGroupService {

    @Autowired
    private TenantGroupRepository tenantGroupRepository;
    @Autowired
    private TenantRepository tenantRepository;

    public List<TenantGroup> saveMultipleTenantGroups(List<TenantGroup> groups) {
        for (TenantGroup group : groups) {
            Optional<TenantGroup> existingGroupOpt = tenantGroupRepository.findByTenantName(group.getTenantName());

            if (existingGroupOpt.isPresent()) {
                TenantGroup existingGroup = existingGroupOpt.get();
                // For each tenant in current group, save it and assign existingGroup
                if (group.getTenantDetailsList() != null) {
                    for (Tenant tenant : group.getTenantDetailsList()) {
                        boolean exists = tenantRepository.existsByTenantFullNameAndTenantAddressAndTenantFatherNameAndTenantMobile(
                                tenant.getTenantFullName(),
                                tenant.getTenantAddress(),
                                tenant.getTenantFatherName(),
                                tenant.getTenantMobile()
                        );
                        if (!exists) {
                            tenant.setTenantGroup(existingGroup);
                            tenantRepository.save(tenant);
                        }
                    }
                }
            } else {
                // No existing group with same tenantName, save normally
                if (group.getTenantDetailsList() != null) {
                    Set<Tenant> filteredTenants = new HashSet<>();
                    for (Tenant tenant : group.getTenantDetailsList()) {
                        boolean exists = tenantRepository.existsByTenantFullNameAndTenantAddressAndTenantFatherNameAndTenantMobile(
                                tenant.getTenantFullName(),
                                tenant.getTenantAddress(),
                                tenant.getTenantFatherName(),
                                tenant.getTenantMobile()
                        );
                        if (!exists) {
                            tenant.setTenantGroup(group);
                            filteredTenants.add(tenant);
                        }
                    }
                    group.setTenantDetailsList(filteredTenants);
                }
                tenantGroupRepository.save(group);
            }
        }
        return tenantGroupRepository.findAll(); // Return all groups after operation
    }

    public TenantGroup saveTenantGroup(TenantGroup group) {
        Optional<TenantGroup> existingGroupOpt = tenantGroupRepository.findByTenantName(group.getTenantName());

        if (existingGroupOpt.isPresent()) {
            TenantGroup existingGroup = existingGroupOpt.get();
            // Save tenants in this group but assign existingGroup
            if (group.getTenantDetailsList() != null) {
                for (Tenant tenant : group.getTenantDetailsList()) {
                    tenant.setTenantGroup(existingGroup);
                    tenantRepository.save(tenant);
                }
            }
            return existingGroup; // Return the existing group with new tenants added
        } else {
            if (group.getTenantDetailsList() != null) {
                for (Tenant tenant : group.getTenantDetailsList()) {
                    tenant.setTenantGroup(group);
                }
            }
            return tenantGroupRepository.save(group);
        }
    }
    public TenantGroup updateTenantGroup(Long id, TenantGroup updatedGroup) {
        return tenantGroupRepository.findById(id).map(existingGroup -> {
            existingGroup.setTenantName(updatedGroup.getTenantName());

            if (updatedGroup.getTenantDetailsList() != null) {
                // Optional: Clear existing tenants if needed
                existingGroup.getTenantDetailsList().clear();
                for (Tenant tenant : updatedGroup.getTenantDetailsList()) {
                    tenant.setTenantGroup(existingGroup);
                    existingGroup.getTenantDetailsList().add(tenant);
                }
            }

            return tenantGroupRepository.save(existingGroup);
        }).orElseThrow(() -> new RuntimeException("TenantGroup not found with id " + id));
    }
    public void deleteTenantGroup(Long id) {
        tenantGroupRepository.deleteById(id);
    }
    public void deleteAllTenantGroups() {
        tenantGroupRepository.deleteAll();
    }

    public List<TenantGroup> getAllTenantGroups() {
        return tenantGroupRepository.findAll();
    }

    public Optional<TenantGroup> getTenantGroupById(Long id) {
        return tenantGroupRepository.findById(id);
    }
}