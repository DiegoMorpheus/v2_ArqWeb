package com.example.v2_ArqWeb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.v2_ArqWeb.domain.Aluno;
import com.example.v2_ArqWeb.domain.Curso;
import com.example.v2_ArqWeb.repository.AlunoRepository;
import com.example.v2_ArqWeb.repository.CursoRepository;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    // Listar todos os alunos
    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    // Criar novo aluno
    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    // Buscar aluno por ID (retorna Optional)
    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    // Atualizar aluno existente
    public Optional<Aluno> atualizar(Long id, Aluno dadosAtualizados) {
        return alunoRepository.findById(id).map(aluno -> {
            aluno.setNome(dadosAtualizados.getNome());
            aluno.setEmail(dadosAtualizados.getEmail());
            aluno.setMatricula(dadosAtualizados.getMatricula());
            return alunoRepository.save(aluno);
        });
    }

    // Deletar aluno
    public boolean deletar(Long id) {
        return alunoRepository.findById(id).map(aluno -> {
            alunoRepository.delete(aluno);
            return true;
        }).orElse(false);
    }

    // Relacionar aluno a um curso
    public Optional<Aluno> matricularEmCurso(Long alunoId, Long cursoId) {
        Optional<Aluno> alunoOpt = alunoRepository.findById(alunoId);
        Optional<Curso> cursoOpt = cursoRepository.findById(cursoId);

        if (alunoOpt.isPresent() && cursoOpt.isPresent()) {
            Aluno aluno = alunoOpt.get();
            Curso curso = cursoOpt.get();

            aluno.getCursos().add(curso);
            curso.getAlunos().add(aluno);

            return Optional.of(alunoRepository.save(aluno));
        }
        return Optional.empty();
    }
}
