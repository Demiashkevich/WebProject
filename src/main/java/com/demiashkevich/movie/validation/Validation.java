package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Entity;

public interface Validation<T extends Entity> {

    public boolean execute(T item);

}
