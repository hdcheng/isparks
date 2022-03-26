package app.isparks.core.service;

import app.isparks.core.pojo.entity.Dictionary;

import java.util.Optional;

public interface IDictionaryService {

    /**
     * 创建字典
     */
    Optional<Dictionary> create(String name, int value);



}
