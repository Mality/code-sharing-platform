package com.mality.emir.db;


import com.mality.emir.code.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    Code findByUuid(String uuid);

    @Transactional
    void deleteByUuid(String uuid);

}
