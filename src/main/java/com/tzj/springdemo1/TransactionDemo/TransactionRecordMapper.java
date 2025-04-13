package com.tzj.springdemo1.TransactionDemo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {
    @Select("SELECT * FROM transaction_record WHERE from_account = #{accountNumber} OR to_account = #{accountNumber} ORDER BY id DESC")
    List<TransactionRecord> findRecentTransactions(@Param("accountNumber") String accountNumber);
}