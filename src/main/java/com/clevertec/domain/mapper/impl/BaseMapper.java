package com.clevertec.domain.mapper.impl;

import com.clevertec.domain.entity.IEntity;
import com.clevertec.domain.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public abstract class BaseMapper<E extends IEntity, D> implements Mapper<E, D> {
	protected ModelMapper modelMapper;
	private Class<E> entityType;
	private Class<D> dtoType;

	protected void init() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public BaseMapper() {
		super();
		this.entityType = getEntityClass();
		this.dtoType = getDtoClass();
		this.modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	@Override
	public D toDTO(E entity) {
		if (entity == null) {
			return null;
		}
		return modelMapper.map(entity, dtoType);
	}

	@Override
	public E toEntity(D dto) {
		if (dto == null) {
			return null;
		}
		return modelMapper.map(dto, entityType);
	}
	protected abstract Class<E> getEntityClass();
	protected abstract Class<D> getDtoClass();
	
	

}