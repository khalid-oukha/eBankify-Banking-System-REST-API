package com.ebankify.api.web.mapper.bankAccount;

import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import com.ebankify.api.web.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.web.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount toBankAccount(UserBankAccountRequestDTO dto);

    User toUser(UserBankAccountRequestDTO dto);

    UserRequestDTO toUserRequestDTO(UserBankAccountRequestDTO dto);

    BankAccountResponseDto toResponseDto(BankAccount bankAccount, User user);
}