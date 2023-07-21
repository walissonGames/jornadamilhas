package com.jornadamilhas.api.domain.depoimentos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {

    @Query("""
            select depoimento from Depoimento depoimento order by rand()
           """)
    public Page<Depoimento> retornarDepoimentosAleatorios(Pageable paginacao);
}
