package com.ebankify.api.mapper.bankAccount;

import com.ebankify.api.dto.bankAccount.BankAccountResponseDto;
import com.ebankify.api.dto.bankAccount.UserBankAccountRequestDTO;
import com.ebankify.api.dto.user.UserRequestDTO;
import com.ebankify.api.entity.BankAccount;
import com.ebankify.api.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    BankAccount toBankAccount(UserBankAccountRequestDTO dto);

    User toUser(UserBankAccountRequestDTO dto);

    UserRequestDTO toUserRequestDTO(UserBankAccountRequestDTO dto);

    BankAccountResponseDto toResponseDto(BankAccount bankAccount, User user);
}
