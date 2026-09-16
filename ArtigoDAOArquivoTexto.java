package mack.fichamento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ArtigoDAOArquivoTexto implements ArtigoDAO {

    private final Path arquivo;

    public ArtigoDAOArquivoTexto(Path arquivo) {
        this.arquivo = arquivo;
    }

    public ArtigoDAOArquivoTexto() {
        this(Paths.get("artigos.txt"));
    }

    @Override
    public void salvar(ArtigoCientifico artigo) {
        try (BufferedWriter writer = Files.newBufferedWriter(arquivo, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(artigo.toLinha());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ArtigoCientifico> listarTodos() {
        List<ArtigoCientifico> artigos = new ArrayList<>();

        if (!Files.exists(arquivo)) {
            return artigos;
        }

        try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    artigos.add(ArtigoCientifico.fromLinha(linha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return artigos;
    }

    @Override
    public ArtigoCientifico buscarPorId(int id) {
        if (!Files.exists(arquivo)) {
            return null;
        }

        try (BufferedReader reader = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    ArtigoCientifico artigo = ArtigoCientifico.fromLinha(linha);
                    if (artigo.id() == id) {
                        return artigo;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean baixarArtigo(ArtigoCientifico artigo, String diretorioDestino) {
        if (artigo == null || artigo.link() == null || artigo.link().isEmpty()) {
            return false;
        }

        try {
            Path pasta = Paths.get(diretorioDestino);
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(artigo.link()))
                    .GET()
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (response.statusCode() == 200) {
                Path destinoFinal = pasta.resolve("artigo_" + artigo.id() + ".pdf");
                Files.copy(response.body(), destinoFinal, StandardCopyOption.REPLACE_EXISTING);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}