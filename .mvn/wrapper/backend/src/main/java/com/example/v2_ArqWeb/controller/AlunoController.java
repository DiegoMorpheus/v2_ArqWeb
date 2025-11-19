package com.example.v2_ArqWeb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.v2_ArqWeb.domain.Aluno;
import com.example.v2_ArqWeb.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // Listar todos os alunos
    @GetMapping
    public ResponseEntity<List<Aluno>> listarTodos() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    // Buscar aluno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(@PathVariable Long id) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        return aluno.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo aluno
    @PostMapping
    public ResponseEntity<Aluno> salvar(@RequestBody Aluno aluno) {
        return ResponseEntity.ok(alunoService.salvar(aluno));
    }

    // Atualizar aluno
    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(@PathVariable Long id, @RequestBody Aluno dadosAtualizados) {
        Optional<Aluno> aluno = alunoService.atualizar(id, dadosAtualizados);
        return aluno.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    // Deletar aluno
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = alunoService.deletar(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Matricular aluno em curso
    @PostMapping("/{alunoId}/cursos/{cursoId}")
    public ResponseEntity<Aluno> matricularEmCurso(@PathVariable Long alunoId, @PathVariable Long cursoId) {
        Optional<Aluno> aluno = alunoService.matricularEmCurso(alunoId, cursoId);
        return aluno.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
}
