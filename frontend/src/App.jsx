import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  // Estado para armazenar os dados recebidos do backend
  const [dados, setDados] = useState('Carregando dados do Backend...');
  // Estado para armazenar mensagens de erro
  const [erro, setErro] = useState(null);

  useEffect(() => {
    // A rota '/alunos' será automaticamente proxy para 'http://localhost:8080/alunos'
    axios.get('/alunos')
      .then(response => {
        // Se a requisição for bem-sucedida, mostre a resposta
        setDados(JSON.stringify(response.data, null, 2));
        setErro(null);
      })
      .catch(error => {
        // Se houver um erro (ex: backend não está rodando)
        setErro('Erro ao buscar dados. Verifique se o Backend está rodando em http://localhost:8080.');
        setDados(null);
        console.error("Erro na requisição:", error);
      });
  }, []); // O array vazio garante que o useEffect rode apenas uma vez, após o carregamento inicial

  return (
    <div style={{ padding: '20px' }}>
      <h1>Status do Frontend: Funcionando! 🚀</h1>
      
      {/* ------------------------------------- */}
      <h2>Teste de Conexão com o Backend (Porta 8080)</h2>
      {/* ------------------------------------- */}

      {erro && (
        <div style={{ color: 'red', border: '1px solid red', padding: '10px' }}>
          <p>{erro}</p>
          <p>
            **Observação:** Se o Backend não tiver a rota `/alunos` configurada ou não estiver ativo, este erro é esperado.
          </p>
        </div>
      )}

      {dados && (
        <>
          <h3>Resposta da Rota /alunos:</h3>
          <pre style={{ backgroundColor: '#f4f4f4', padding: '10px', borderRadius: '4px' }}>
            {dados}
          </pre>
        </>
      )}
    </div>
  );
}

export default App;