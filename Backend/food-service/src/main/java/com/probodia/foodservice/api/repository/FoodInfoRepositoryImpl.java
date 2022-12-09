package com.probodia.foodservice.api.repository;

import com.probodia.foodservice.api.entity.FoodInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class FoodInfoRepositoryImpl{
    private final EntityManager em;


    @Transactional
    public int saveEntity(String query){

        //log.debug("query : {}",query);
        int i = em.createNativeQuery(query).executeUpdate();
        em.flush();

        return i;
    }

}
