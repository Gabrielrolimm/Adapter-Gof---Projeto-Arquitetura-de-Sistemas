package biblioteca.infrastructure.persistence;

import biblioteca.domain.model.Item;
import biblioteca.domain.model.Livro;
import biblioteca.domain.model.Periodico;

public class ItemCsvMapper {

    public ItemRegistroCsv deLinha(String linha) {
        String[] partes = linha.split(";", -1);

        if (partes.length < 6) {
            throw new IllegalStateException("Linha CSV inválida: " + linha);
        }

        return new ItemRegistroCsv(
                partes[0],
                partes[1],
                partes[2],
                partes[3],
                partes[4],
                Boolean.parseBoolean(partes[5])
        );
    }

    public String paraLinha(ItemRegistroCsv registro) {
        return String.join(";",
                registro.getTipo(),
                registro.getId(),
                registro.getTitulo(),
                registro.getCampoExtra1(),
                registro.getCampoExtra2(),
                String.valueOf(registro.isDisponivel())
        );
    }

    public ItemRegistroCsv deItem(Item item) {
        if (item instanceof Livro livro) {
            return new ItemRegistroCsv(
                    livro.getTipo(),
                    livro.getId(),
                    livro.getTitulo(),
                    livro.getAutor(),
                    livro.getIsbn(),
                    livro.isDisponivel()
            );
        }

        if (item instanceof Periodico periodico) {
            return new ItemRegistroCsv(
                    periodico.getTipo(),
                    periodico.getId(),
                    periodico.getTitulo(),
                    String.valueOf(periodico.getEdicao()),
                    "",
                    periodico.isDisponivel()
            );
        }

        throw new IllegalStateException("Tipo de item não suportado para CSV.");
    }

    public Item paraItem(ItemRegistroCsv registro) {
        Item item;

        if ("LIVRO".equals(registro.getTipo())) {
            item = new Livro(
                    registro.getId(),
                    registro.getTitulo(),
                    registro.getCampoExtra1(),
                    registro.getCampoExtra2()
            );
        } else if ("PERIODICO".equals(registro.getTipo())) {
            item = new Periodico(
                    registro.getId(),
                    registro.getTitulo(),
                    Integer.parseInt(registro.getCampoExtra1())
            );
        } else {
            throw new IllegalStateException("Tipo de item inválido no registro CSV: " + registro.getTipo());
        }

        if (!registro.isDisponivel()) {
            item.marcarComoEmprestado();
        }

        return item;
    }
}