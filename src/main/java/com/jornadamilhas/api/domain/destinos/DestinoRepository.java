package com.jornadamilhas.api.domain.destinos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinoRepository extends JpaRepository<Destino, Long> {
    
    Page<Destino> findAllByNome(String nome, Pageable paginacao);
    
}
