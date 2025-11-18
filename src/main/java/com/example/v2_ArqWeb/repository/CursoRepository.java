package com.example.v2_ArqWeb.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.v2_ArqWeb.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    Optional<Curso> findByNome(String nome);
}
