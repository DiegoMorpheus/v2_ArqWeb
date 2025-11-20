import { useEffect, useState } from "react";

function App() {
  const [alunos, setAlunos] = useState([]);
  const [cursos, setCursos] = useState([]);
  const [loading, setLoading] = useState(false);

  // Estados para formulário de aluno
  const [matricula, setMatricula] = useState("");
  const [nomeAluno, setNomeAluno] = useState("");
  const [email, setEmail] = useState("");

  // Estados para formulário de curso
  const [nomeCurso, setNomeCurso] = useState("");
  const [cargaHoraria, setCargaHoraria] = useState("");

  // Função genérica para buscar dados
  const carregarDados = async () => {
    try {
      setLoading(true);

      const [alunosRes, cursosRes] = await Promise.all([
        fetch("/api/alunos"),
        fetch("/api/cursos"),
      ]);

      if (!alunosRes.ok || !cursosRes.ok) {
        throw new Error("Erro ao buscar dados do backend");
      }

      const alunosData = await alunosRes.json();
      const cursosData = await cursosRes.json();

      console.log("Alunos recebidos:", alunosData);
      console.log("Cursos recebidos:", cursosData);

      setAlunos(alunosData);
      setCursos(cursosData);
    } catch (err) {
      console.error("Erro ao carregar dados:", err);
    } finally {
      setLoading(false);
    }
  };


useEffect(() => {
  fetch("http://localhost:8081/alunos")
    .then(res => res.json())
    .then(data => {
      console.log("Alunos recebidos:", data);
      setAlunos(data);
    })
    .catch(err => console.error("Erro ao buscar alunos:", err));
}, []);


  // Função para cadastrar aluno
  const cadastrarAluno = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("/api/alunos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ matricula, nome: nomeAluno, email }),
      });

      if (!res.ok) throw new Error("Erro ao cadastrar aluno");

      setMatricula("");
      setNomeAluno("");
      setEmail("");
      carregarDados();
    } catch (err) {
      console.error(err);
    }
  };

  // Função para cadastrar curso
  const cadastrarCurso = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch("/api/cursos", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nome: nomeCurso, cargaHoraria }),
      });

      if (!res.ok) throw new Error("Erro ao cadastrar curso");

      setNomeCurso("");
      setCargaHoraria("");
      carregarDados();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 font-sans">
      {/* Cabeçalho */}
      <header className="bg-blue-600 text-white shadow-md">
        <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">
          <h1 className="text-2xl font-bold">Portal Acadêmico 🎓</h1>
          <nav className="space-x-6">
            <a href="#alunos" className="hover:underline">Alunos</a>
            <a href="#cursos" className="hover:underline">Cursos</a>
          </nav>
        </div>
      </header>

      <main className="max-w-6xl mx-auto px-6 py-10">
        {/* Botão de recarregar */}
        <div className="mb-6">
          <button
            onClick={carregarDados}
            className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition"
          >
            🔄 Recarregar dados
          </button>
          {loading && <span className="ml-3 text-gray-600">Carregando...</span>}
        </div>

        {/* Formulário de Aluno */}
        <section className="mb-10">
          <h2 className="text-xl font-semibold mb-4">Cadastrar Aluno</h2>
          <form onSubmit={cadastrarAluno} className="space-y-4 bg-white p-4 shadow rounded">
            <input
              type="text"
              placeholder="Matrícula"
              value={matricula}
              onChange={(e) => setMatricula(e.target.value)}
              className="border p-2 w-full rounded"
              required
            />
            <input
              type="text"
              placeholder="Nome"
              value={nomeAluno}
              onChange={(e) => setNomeAluno(e.target.value)}
              className="border p-2 w-full rounded"
              required
            />
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="border p-2 w-full rounded"
              required
            />
            <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
              ➕ Adicionar Aluno
            </button>
          </form>
        </section>

        {/* Tabela de Alunos */}
        <section id="alunos" className="mb-10">
          <h2 className="text-xl font-semibold mb-4">Lista de Alunos</h2>
          {alunos.length > 0 ? (
            <table className="w-full border-collapse bg-white shadow-md rounded">
              <thead>
                <tr className="bg-gray-200">
                  <th className="p-2 border">Matrícula</th>
                  <th className="p-2 border">Nome</th>
                  <th className="p-2 border">Email</th>
                </tr>
              </thead>
              <tbody>
                {alunos.map((aluno) => (
                  <tr key={aluno.id || aluno.matricula} className="hover:bg-gray-100">
                    <td className="p-2 border">{aluno.matricula}</td>
                    <td className="p-2 border">{aluno.nome}</td>
                    <td className="p-2 border">{aluno.email}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-600">Nenhum aluno cadastrado.</p>
          )}
        </section>

        {/* Formulário de Curso */}
        <section className="mb-10">
          <h2 className="text-xl font-semibold mb-4">Cadastrar Curso</h2>
          <form onSubmit={cadastrarCurso} className="space-y-4 bg-white p-4 shadow rounded">
            <input
              type="text"
              placeholder="Nome do curso"
              value={nomeCurso}
              onChange={(e) => setNomeCurso(e.target.value)}
              className="border p-2 w-full rounded"
              required
            />
            <input
              type="number"
              placeholder="Carga Horária"
              value={cargaHoraria}
              onChange={(e) => setCargaHoraria(e.target.value)}
              className="border p-2 w-full rounded"
              required
            />
            <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
              ➕ Adicionar Curso
            </button>
          </form>
        </section>

        {/* Tabela de Cursos */}
        <section id="cursos">
          <h2 className="text-xl font-semibold mb-4">Lista de Cursos</h2>
          {cursos.length > 0 ? (
            <table className="w-full border-collapse bg-white shadow-md rounded">
              <thead>
                <tr className="bg-gray-200">
                  <th className="p-2 border">ID</th>
                  <th className="p-2 border">Nome</th>
                  <th className="p-2 border">Carga Horária</th>
                </tr>
              </thead>
              <tbody>
                {cursos.map((curso) => (
                  <tr key={curso.id || curso.nome} className="hover:bg-gray-100">
                    <td className="p-2 border">{curso.id}</td>
                    <td className="p-2 border">{curso.nome}</td>
                    <td className="p-2 border">{curso.cargaHoraria}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="text-gray-600">Nenhum curso cadastrado.</p>
          )}
        </section>
      </main>

      {/* Rodapé */}
      <footer className="bg-gray-200 text-gray-700 mt-10">
        <div className="max-w-6xl mx-auto px-6 py-4 text-center">
          <p>&copy; 2025 Portal Acadêmico - Integração Frontend e Backend</p>
        </div>
      </footer>
    </div>
  );
}

export default App;


