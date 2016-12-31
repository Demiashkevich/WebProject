package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Entity;

public interface ValidationStrategy<T extends Entity> {

    public boolean validate(T item);

}
