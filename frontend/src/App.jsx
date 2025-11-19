import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [dados, setDados] = useState('Carregando dados do Backend...');
  const [erro, setErro] = useState(null);
  
  //  ATENÇÃO: Use as credenciais CORRETAS do seu Spring Security
  // Por padrão, se não configurado, o Spring usa "user" e uma senha gerada no log.
  const USERNAME = 'user'; 
  const PASSWORD = 'user123'; 

  useEffect(() => {
    // A rota '/alunos' será proxy para 'http://localhost:8080/alunos'
    axios.get('/alunos', {
        auth: {
            username: USERNAME,
            password: PASSWORD
        }
    })
    .then(response => {
        setDados(JSON.stringify(response.data, null, 2));
        setErro(null);
    })
    .catch(error => {
        if (error.response && error.response.status === 401) {
             setErro(`Erro 401: Acesso Negado. Credenciais (${USERNAME}/${PASSWORD}) Inválidas!`);
        } else {
             setErro('Erro na conexão com o Backend. Verifique se o servidor está rodando.');
        }
        setDados(null);
        console.error("Erro na requisição:", error);
    });
  }, []);

  // ... (Restante do seu componente 'return' permanece igual)
  return (
    <div style={{ padding: '20px' }}>
      <h1>Status do Frontend: Funcionando! 🚀</h1>
      
      <h2>Teste de Conexão com o Backend (Porta 8080)</h2>
      
      {/* ... (código de exibição de erro e dados) ... */}
    </div>
  );
}

export default App;