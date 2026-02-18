package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.Branch;
import com.example.Dashboard2.Repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Branch updateBranch(Long id, Branch branch) {
        Optional<Branch> existingBranch = branchRepository.findById(id);
        if (existingBranch.isPresent()) {
            Branch updatedBranch = existingBranch.get();
            updatedBranch.setBranch(branch.getBranch());
            return branchRepository.save(updatedBranch);
        } else {
            throw new RuntimeException("Branch not found " + id);
        }
    }

    public void deleteBranch(Long id) {
        if (branchRepository.existsById(id)) {
            branchRepository.deleteById(id);
        } else {
            throw new RuntimeException("Branch not found " + id);
        }
    }
}
