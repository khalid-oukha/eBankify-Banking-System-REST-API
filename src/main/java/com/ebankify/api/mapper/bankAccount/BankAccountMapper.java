package com.ebankify.api.mapper.bankAccount;

import com.ebankify.api.dto.bankAccount.BankAccountRequestDTO;
import com.ebankify.api.entity.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    BankAccount toEntity(BankAccountRequestDTO dto);

    BankAccountRequestDTO toDto(BankAccount entity);
}
