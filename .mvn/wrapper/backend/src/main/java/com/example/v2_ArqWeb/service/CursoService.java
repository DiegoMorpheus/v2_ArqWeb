package com.example.v2_ArqWeb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.v2_ArqWeb.domain.Curso;
import com.example.v2_ArqWeb.repository.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Listar todos os cursos
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Criar novo curso
    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Buscar curso por ID (retorna Optional)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    // Atualizar curso existente
    public Optional<Curso> atualizar(Long id, Curso dadosAtualizados) {
        return cursoRepository.findById(id).map(curso -> {
            curso.setNome(dadosAtualizados.getNome());
            curso.setCargaHoraria(dadosAtualizados.getCargaHoraria());
            return cursoRepository.save(curso);
        });
    }

    // Deletar curso
    public boolean deletar(Long id) {
        return cursoRepository.findById(id).map(curso -> {
            cursoRepository.delete(curso);
            return true;
        }).orElse(false);
    }
}
