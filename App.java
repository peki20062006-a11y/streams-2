import mack.fichamento.ArtigoCientifico;
import mack.fichamento.ArtigoDAOArquivoTexto;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ArtigoDAOArquivoTexto dao = new ArtigoDAOArquivoTexto();

        // Teste de listagem completa
        List<ArtigoCientifico> todos = dao.listarTodos();
        System.out.println("Total de artigos: " + todos.size());

        // Teste de busca rápida por ID
        ArtigoCientifico artigo = dao.buscarPorId(1);
        if (artigo != null) {
            System.out.println("Encontrado: " + artigo.titulo());
            
            // Teste de download (Tarefa 3)
            boolean sucesso = dao.baixarArtigo(artigo, "downloads");
            System.out.println("Download concluído: " + sucesso);
    }
    }
}