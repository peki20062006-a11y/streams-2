package mack.fichamento;

import java.util.List;

public interface ArtigoDAO {
    void salvar(ArtigoCientifico artigo);
    List<ArtigoCientifico> listarTodos();
    ArtigoCientifico buscarPorId(int id);
}