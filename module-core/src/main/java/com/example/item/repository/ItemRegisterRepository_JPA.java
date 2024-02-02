package com.example.item.repository;

import com.example.item.domain.ItemEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public interface ItemRegisterRepository_JPA extends JpaRepository<ItemEntity, Long>, ItemRegisterRepository {

}
