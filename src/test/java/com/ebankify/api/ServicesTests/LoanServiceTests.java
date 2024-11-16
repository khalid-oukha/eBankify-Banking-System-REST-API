package com.ebankify.api.ServicesTests;

import com.ebankify.api.entity.Loan;
import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.LoanStatus;
import com.ebankify.api.entity.enums.Role;
import com.ebankify.api.repository.LoanRepository;
import com.ebankify.api.service.loan.LoanService;
import com.ebankify.api.service.loan.LoanServiceImpl;
import com.ebankify.api.service.loanProcessing.LoanProcessingService;
import com.ebankify.api.web.dto.loan.LoanResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoanServiceTests {
    private LoanRepository loanRepository;
    private LoanServiceImpl loanService;
    private LoanProcessingService loanProcessingService;

    @BeforeEach
    public void beforeEach() {
        loanRepository = mock(LoanRepository.class);
        loanService = new LoanServiceImpl(loanRepository, loanProcessingService);
    }

    @Test
    public void findAll_shouldReturnAllLoans() {
        User user = User.builder()
                .id(1L)
                .username("khalidoukha")
                .email("oukhakhalid@mail.Com")
                .age(25)
                .role(Role.USER)
                .build();

        List<Loan> ExpectedLoans = List.of(
                Loan.builder()
                        .id(1L)
                        .amount(1212.00)
                        .duration(3)
                        .monthlyIncome(23)
                        .debtToIncomeRatio(3)
                        .status((LoanStatus.PENDING))
                        .user(user)
                        .build(),

                Loan.builder()
                        .id(2L)
                        .amount(90000.00)
                        .duration(34)
                        .monthlyIncome(2340)
                        .debtToIncomeRatio(34)
                        .status((LoanStatus.PENDING))
                        .user(user)
                        .build(),

                Loan.builder()
                        .id(3L)
                        .amount(1212.00)
                        .duration(3)
                        .monthlyIncome(232)
                        .debtToIncomeRatio(342)
                        .status((LoanStatus.PENDING))
                        .user(user)
                        .build()
        );

        List<LoanResponseDTO> ExpectedLoanResponseDTOs = new ArrayList<>();
        for (Loan loan : ExpectedLoans) {
            ExpectedLoanResponseDTOs.add(LoanResponseDTO.fromLoanAndUser(loan, loan.getUser()));
        }
        when(loanRepository.findAll()).thenReturn(ExpectedLoans);

        List<LoanResponseDTO> result = loanService.findAll();

        assertEquals(result.size(), ExpectedLoans.size(), "The result should have the same size as the expected loans");
        assertEquals(result, ExpectedLoanResponseDTOs, "The result should match the expected loans");
    }

    @Test
    public void findById_shouldReturnLoan() {
        User user = User.builder()
                .id(1L)
                .username("khalidoukha")
                .email("oukhakhalid@gmail.Com")
                .age(25)
                .role(Role.USER)
                .build();

        Loan ExpectedLoan = Loan.builder()
                .id(3L)
                .amount(1212.00)
                .duration(3)
                .monthlyIncome(232)
                .debtToIncomeRatio(342)
                .status((LoanStatus.PENDING))
                .user(user)
                .build();

        LoanResponseDTO ExpectedLoanResponseDTO = LoanResponseDTO.fromLoanAndUser(ExpectedLoan, user);

        when(loanRepository.findById(3L)).thenReturn(java.util.Optional.of(ExpectedLoan));

        LoanResponseDTO result = loanService.findById(3L);

        assertEquals(result, ExpectedLoanResponseDTO, "The result should match the expected loan");

    }

}
