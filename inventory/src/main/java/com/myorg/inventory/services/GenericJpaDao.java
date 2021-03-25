package com.myorg.inventory.services;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE )
public class GenericJpaDao< T extends Serializable>
        extends AbstractDao< T > implements GenericDao< T >{

    @Override
    public T findOne(long id) {
        return null;
    }

    @Override
    public void create(T entity) {

    }

    @Override
    public void deleteById(long entityId) {

    }

    @Override
    public T update( T entity ){
        return entityManager.merge( entity );
    }
}