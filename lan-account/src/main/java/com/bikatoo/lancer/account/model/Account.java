package com.bikatoo.lancer.account.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.bikatoo.lancer.common.model.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "a_account")
public class Account extends BaseDO<Account> {

  @TableId(value = "account_id", type = IdType.AUTO)
  private Long accountId;

  private String loginName;

  private String password;

  private String nickname;
}
