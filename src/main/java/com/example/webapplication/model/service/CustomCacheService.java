package com.example.webapplication.model.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomCacheService<S, T> {
    void add(S key, T value);

    void remove(S key);

    Optional<T> find(S key);

    List<T> getAll();
}
