package com.haruns.utility;

import java.util.List;
import java.util.Optional;

public interface ICrud<T>{
	Optional<T> save(T t);
	void delete(Long id);
	Optional<T> update(T t);
	List<T> findAll();
	Optional<T> findById(Long id);
}