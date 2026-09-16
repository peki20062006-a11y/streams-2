package mack.fichamento;

public record ArtigoCientifico(
    int id,
    String titulo,
    String autores,
    String resumo,
    String palavrasChave,
    String referencias,
    String link
) {
    public String toLinha() {
        return id + " | " + titulo + " | " + autores + " | " + resumo + " | " + palavrasChave + " | " + referencias + (link != null && !link.isEmpty() ? " | " + link : "");
    }

    public static ArtigoCientifico fromLinha(String linha) {
        String[] partes = linha.split("\\s*\\|\\s*");
        int id = Integer.parseInt(partes[0].trim());
        String titulo = partes[1].trim();
        String autores = partes[2].trim();
        String resumo = partes[3].trim();
        String palavrasChave = partes[4].trim();
        String referencias = partes[5].trim();
        String link = partes.length > 6 ? partes[6].trim() : "";

        return new ArtigoCientifico(id, titulo, autores, resumo, palavrasChave, referencias, link);
    }
}