package com.bikatoo.lancer.common.assembler;


import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

public interface Assembler<D, E> {

    D entityToDTO(E entity);

    E dtoToEntity(D dto);

    default List<D> entityToDTOForList(List<E> entityList) {

        if (CollectionUtils.isEmpty(entityList)) {
            return Lists.newArrayList();
        }
        return entityList.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    default List<E> DTOToEntityForList(List<D> dObjectList) {
        if (CollectionUtils.isEmpty(dObjectList)) {
            return Lists.newArrayList();
        }
        return dObjectList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

    default PageInfo<D> entityToDTOForPage(PageInfo<E> entityPage) {
        if (entityPage == null) {
            return null;
        }
        List<E> entityList = entityPage.getList();
        List<D> dtoList = entityList.stream().map(this::entityToDTO).collect(Collectors.toList());
        PageInfo<D> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(entityPage, pageInfo);
        pageInfo.setList(dtoList);
        return pageInfo;
    }

    default PageInfo<E> DTOToEntityForPage(PageInfo<D> dtoPage) {
        if (dtoPage == null) {
            return null;
        }
        List<D> dtoList = dtoPage.getList();
        List<E> entityList = dtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
        PageInfo<E> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(dtoPage, pageInfo);
        pageInfo.setList(entityList);
        return pageInfo;
    }

}
