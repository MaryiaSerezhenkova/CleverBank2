package com.clevertec.service;

public interface IService <ENTITY, DTO> {
    DTO create(DTO item);

    DTO read(long id);
}