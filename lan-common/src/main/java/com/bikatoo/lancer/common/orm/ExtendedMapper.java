package com.bikatoo.lancer.common.orm;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ExtendedMapper<T> extends BaseMapper<T> {

	static <T> ExtendedMapper<T> extend(BaseMapper<T> mapper) {
		return new ExtendedMapper<T>() {
			@Override
			public int insert(T entity) {
				return mapper.insert(entity);
			}

			@Override
			public int deleteById(Serializable id) {
				return mapper.deleteById(id);
			}

			@Override
			public int deleteByMap(Map<String, Object> columnMap) {
				return mapper.deleteByMap(columnMap);
			}

			@Override
			public int delete(Wrapper<T> wrapper) {
				return mapper.delete(wrapper);
			}

			@Override
			public boolean exists(Wrapper<T> queryWrapper) {
				return ExtendedMapper.super.exists(queryWrapper);
			}

			@Override
			public int deleteById(T entity) {
				return mapper.deleteById(entity);
			}

			@Override
			public int deleteBatchIds(Collection<?> idList) {
				return mapper.deleteBatchIds(idList);
			}

			@Override
			public int updateById(T entity) {
				return mapper.updateById(entity);
			}

			@Override
			public int update(T entity, Wrapper<T> updateWrapper) {
				return mapper.update(entity, updateWrapper);
			}

			@Override
			public T selectById(Serializable id) {
				return mapper.selectById(id);
			}

			@Override
			public List<T> selectBatchIds(Collection<? extends Serializable> idList) {
				return mapper.selectBatchIds(idList);
			}

			@Override
			public List<T> selectByMap(Map<String, Object> columnMap) {
				return mapper.selectByMap(columnMap);
			}

			@Override
			public T selectOne(Wrapper<T> queryWrapper) {
				return mapper.selectOne(queryWrapper);
			}

			@Override
			public Long selectCount(Wrapper<T> queryWrapper) {
				return mapper.selectCount(queryWrapper);
			}

			@Override
			public List<T> selectList(Wrapper<T> queryWrapper) {
				return mapper.selectList(queryWrapper);
			}

			@Override
			public List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper) {
				return mapper.selectMaps(queryWrapper);
			}

			@Override
			public List<Object> selectObjs(Wrapper<T> queryWrapper) {
				return mapper.selectObjs(queryWrapper);
			}

			@Override
			public <P extends IPage<T>> P selectPage(P page, Wrapper<T> queryWrapper) {
				return mapper.selectPage(page, queryWrapper);
			}

			@Override
			public <P extends IPage<Map<String, Object>>> P selectMapsPage(P page, Wrapper<T> queryWrapper) {
				return mapper.selectMapsPage(page, queryWrapper);
			}
		};
	}


	/**
	 * 使用 mapper 获取给定条件数据的数目
	 *
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @return 查询结果
	 */
	default long selectCount(Consumer<LambdaQueryWrapper<T>> cond) {
		return MyBatisTools.selectCount(this, cond);
	}

	/**
	 * 使用 mapper 判断给定条件的数据是否存在
	 *
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @return 查询结果
	 */
	default boolean exists(Consumer<LambdaQueryWrapper<T>> cond) {
		return MyBatisTools.exists(this, cond);
	}

	/**
	 * 使用 mapper 根据给定条件查询一个结果
	 *
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @return 查询结果
	 */
	default T selectOne(Consumer<LambdaQueryWrapper<T>> cond) {
		return MyBatisTools.selectOne(this, cond);
	}

	/**
	 * 使用 mapper 根据给定条件查询一组结果
	 *
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @return 查询结果
	 */
	default List<T> selectList(Consumer<LambdaQueryWrapper<T>> cond) {
		return MyBatisTools.selectList(this, cond);
	}

	/**
	 * 同 {@link #selectList(Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
	 *
	 * @param returnsEmpty
	 *            如果为 true，将不做任何查询直接返回空列表
	 */
	default List<T> selectList(Consumer<LambdaQueryWrapper<T>> cond, boolean returnsEmpty) {
		return MyBatisTools.selectList(this, cond, returnsEmpty);
	}

	/**
	 * 同 {@link #selectList(Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
	 *
	 * @param returnsEmpty
	 *            如果该 supplier 执行返回 true，将不做任何查询直接返回空列表
	 */
	default List<T> selectList(Consumer<LambdaQueryWrapper<T>> cond, Supplier<Boolean> returnsEmpty) {
		return MyBatisTools.selectList(this, cond, returnsEmpty);
	}

	/**
	 * 更新
	 *
	 * @param update
	 *            更新条件及更新数据
	 */
	default int update(Consumer<LambdaUpdateWrapper<T>> update) {
		return MyBatisTools.update(this, update);
	}

	/**
	 * @param data
	 *            非空字段将用于更新
	 * @param updateCond
	 *            用于构造更新条件
	 */
	default int update(T data, Consumer<LambdaQueryWrapper<T>> updateCond) {
		return MyBatisTools.update(this, data, updateCond);
	}

	/**
	 * @param removeCond
	 *            用于构造删除条件
	 */
	default int delete(Consumer<LambdaQueryWrapper<T>> removeCond) {
		return MyBatisTools.delete(this, removeCond);
	}
}
