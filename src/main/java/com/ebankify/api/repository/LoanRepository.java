package com.ebankify.api.repository;

import com.ebankify.api.entity.Loan;
import com.ebankify.api.entity.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Loan l set l.status = :status where l.id = :id")
    void updateStatusById(@Param("id") Long id, @Param("status") LoanStatus status);
}
