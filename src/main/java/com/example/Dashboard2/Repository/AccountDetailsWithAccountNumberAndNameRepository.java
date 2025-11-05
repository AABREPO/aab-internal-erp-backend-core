package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AccountDetailsWithAccountNumberAndName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDetailsWithAccountNumberAndNameRepository extends JpaRepository<AccountDetailsWithAccountNumberAndName, Long> {
}
