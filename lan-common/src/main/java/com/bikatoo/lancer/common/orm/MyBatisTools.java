package com.bikatoo.lancer.common.orm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyBatisTools {

	/**
	 * 扩展 MyBatis mapper，添加 MyBatisTools 中给出的方法
	 *
	 * @param mapper
	 *            MyBatis mapper
	 * @param <T>
	 *            Map 的对象
	 */
	public static <T> ExtendedMapper<T> extend(BaseMapper<T> mapper) {
		return ExtendedMapper.extend(mapper);
	}

	/**
	 * 使用 mapper 获取给定条件数据的数目
	 *
	 * @param mapper
	 *            MyBatis mapper
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @param <T>
	 *            Map 的对象
	 * @return 查询结果
	 */
	public static <T> long selectCount(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		cond.accept(w);
		return mapper.selectCount(w);
	}

	/**
	 * 使用 mapper 判断给定条件的数据是否存在
	 *
	 * @param mapper
	 *            MyBatis mapper
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @param <T>
	 *            Map 的对象
	 * @return 查询结果
	 */
	public static <T> boolean exists(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		cond.accept(w);
		return mapper.selectCount(w) != 0;
	}

	/**
	 * 使用 mapper 根据给定条件查询一个结果
	 *
	 * @param mapper
	 *            MyBatis mapper
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @param <T>
	 *            Map 的对象
	 * @return 查询结果
	 */
	public static <T> T selectOne(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		cond.accept(w);
		List<T> data = mapper.selectList(w);
		if (data.isEmpty()) {
			return null;
		}
		if (data.size() > 1) {
			log.warn("got multiple result when try select one: {} - {}", mapper, cond);
		}
		return data.get(0);
	}

	/**
	 * 使用 mapper 根据给定条件查询一组结果
	 *
	 * @param mapper
	 *            MyBatis mapper
	 * @param cond
	 *            其中可以定制查询条件，如可以传递 {@code w -> { w.eq(T::getId, 1); }}
	 * @param <T>
	 *            Map 的对象
	 * @return 查询结果
	 */
	public static <T> List<T> selectList(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		cond.accept(w);
		return mapper.selectList(w);
	}

	/**
	 * 同 {@link #selectList(BaseMapper, Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
	 *
	 * @param returnsEmpty
	 *            如果为 true，将不做任何查询直接返回空列表
	 */
	public static <T> List<T> selectList(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond,
			boolean returnsEmpty) {
		if (returnsEmpty) {
			return new ArrayList<>();
		} else {
			return selectList(mapper, cond);
		}
	}

	/**
	 * 同 {@link #selectList(BaseMapper, Consumer)} 功能一样，区别在于 {@code returnsEmpty} 参数
	 *
	 * @param returnsEmpty
	 *            如果该 supplier 执行返回 true，将不做任何查询直接返回空列表
	 */
	public static <T> List<T> selectList(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> cond,
			Supplier<Boolean> returnsEmpty) {
		if (returnsEmpty.get()) {
			return new ArrayList<>();
		} else {
			return selectList(mapper, cond);
		}
	}

	/**
	 * @param mapper
	 *            MyBatis mapper
	 * @param update
	 *            更新条件及更新数据
	 * @param <T>
	 *            Map 的数据
	 */
	public static <T> int update(BaseMapper<T> mapper, Consumer<LambdaUpdateWrapper<T>> update) {
		LambdaUpdateWrapper<T> w = new LambdaUpdateWrapper<>();
		update.accept(w);
		return mapper.update(null, w);
	}

	/**
	 * @param mapper
	 *            MyBatis mapper
	 * @param data
	 *            非空字段将用于更新
	 * @param updateCond
	 *            用于构造更新条件
	 * @param <T>
	 *            Map 的数据
	 */
	public static <T> int update(BaseMapper<T> mapper, T data, Consumer<LambdaQueryWrapper<T>> updateCond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		updateCond.accept(w);
		return mapper.update(data, w);
	}

	/**
	 * @param mapper
	 *            MyBatis mapper
	 * @param removeCond
	 *            用于构造删除条件
	 * @param <T>
	 *            Map 的数据
	 */
	public static <T> int delete(BaseMapper<T> mapper, Consumer<LambdaQueryWrapper<T>> removeCond) {
		LambdaQueryWrapper<T> w = new LambdaQueryWrapper<>();
		removeCond.accept(w);
		return mapper.delete(w);
	}
}
