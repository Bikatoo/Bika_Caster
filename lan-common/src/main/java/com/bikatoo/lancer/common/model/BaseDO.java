package com.bikatoo.lancer.common.model;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "createTime", "updateTime", "deleteTime" })
@SuppressWarnings("unchecked")
public abstract class BaseDO<ConcreteBaseDO extends BaseDO<ConcreteBaseDO>>
		implements Serializable {

	@TableField(value = "create_time")
	protected Date createTime;

	@TableField(value = "update_time")
	protected Date updateTime;

	@TableField(value = "delete_time")
	protected Date deleteTime;

	public ConcreteBaseDO setCreateTime(Date createTime) {
		this.createTime = createTime;
		return (ConcreteBaseDO) this;
	}

	public ConcreteBaseDO setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
		return (ConcreteBaseDO) this;
	}

	public ConcreteBaseDO setDeleteTime(Date deleteTime) {
		this.deleteTime = deleteTime;
		return (ConcreteBaseDO) this;
	}
}
