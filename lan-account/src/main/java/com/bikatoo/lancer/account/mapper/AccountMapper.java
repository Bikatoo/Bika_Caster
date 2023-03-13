package com.bikatoo.lancer.account.mapper;

import com.bikatoo.lancer.account.model.Account;
import com.bikatoo.lancer.common.model.SortParam;
import com.bikatoo.lancer.common.orm.ExtendedMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface AccountMapper extends ExtendedMapper<Account> {

  List<Account> queryAccount(
      @Param("keyword") String keyword,
      @Param("sortParam") SortParam sortParam);
}
