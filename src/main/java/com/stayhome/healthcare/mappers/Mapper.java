package com.stayhome.healthcare.mappers;

// Used to transform from entity to DTO OR
// from DTO to entity
public interface Mapper<A, B> {

    B mapTo(A a);
    A mapFrom(B b);

}
