package com.bikatoo.lancer.common.assembler;


import com.bikatoo.lancer.common.utils.PreconditionUtil;
import com.bikatoo.lancer.common.exception.GlobalException;
import java.lang.reflect.InvocationTargetException;
import org.springframework.beans.BeanUtils;

public abstract class DefaultAssembler<D, E> implements Assembler<D, E> {

    protected D entityToDTO(E entity, Class<D> clazz) {

        if (entity == null) {
            return null;
        }
        PreconditionUtil.checkNonNullAndThrow(clazz, new GlobalException("clazz is null"));

        D d = null;
        try {
            d = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(entity, d);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return d;
    }

    protected E dtoToEntity(D dto, Class<E> clazz) {
        if (dto == null) {
            return null;
        }
        PreconditionUtil.checkNonNullAndThrow(clazz, new GlobalException("clazz is null"));
        E entity = null;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, entity);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return entity;
    }
}
