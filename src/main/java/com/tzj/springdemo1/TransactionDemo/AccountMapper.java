package com.tzj.springdemo1.TransactionDemo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Select("SELECT * FROM account WHERE account_number = #{accountNumber}")
    Account findByAccountNumber(@Param("accountNumber") String accountNumber);
}