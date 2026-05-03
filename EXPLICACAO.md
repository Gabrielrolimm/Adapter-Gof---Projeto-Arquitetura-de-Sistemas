# Explicação do Adapter no Projeto

Este arquivo foi criado para explicar, de forma didática, **como o padrão Adapter funciona neste projeto**.

A ideia aqui não é explicar o projeto inteiro, mas sim mostrar **como a adaptação acontece na prática**, usando o `RepositorioItemArquivoAdapter` como exemplo principal.

---

## Visão geral

Neste projeto, a aplicação trabalha com objetos do tipo `Item` e com a interface `RepositorioItem`.

Porém, os dados reais dos itens são armazenados em um arquivo `.csv`.

Isso gera uma incompatibilidade natural entre:

- o que a aplicação espera usar: **objetos e métodos**
- e o que o arquivo oferece: **texto estruturado em CSV**

O papel do Adapter é justamente fazer essa ponte.

---

# 1. Item

Esse é o formato que a aplicação utiliza, então é com ele que o sistema vai trabalhar.

```java
package biblioteca.domain.model;

public abstract class Item {
    private final String id;
    private final String titulo;
    private boolean disponivel;

    protected Item(String id, String titulo) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do item é obrigatório.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título do item é obrigatório.");
        }

        this.id = id;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void marcarComoEmprestado() {
        if (!disponivel) {
            throw new IllegalStateException("Item já está emprestado.");
        }
        this.disponivel = false;
    }

    public void marcarComoDisponivel() {
        this.disponivel = true;
    }

    public abstract String getTipo();
}
```

### Explicação

A aplicação não trabalha diretamente com CSV nem com linhas de texto.  
Ela trabalha com objetos do domínio, como `Item`, `Livro` e `Periodico`.

Por isso, quando o sistema precisa listar, buscar ou salvar dados, ele quer trabalhar com esse formato.

---

# 2. Interface `RepositorioItem`

Essa é a interface principal esperada pela aplicação.

```java
package biblioteca.domain.repository;

import biblioteca.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface RepositorioItem {
    void salvar(Item item);
    Optional<Item> buscarPorId(String id);
    List<Item> listarTodos();
    void restaurarDisponibilidadeDeTodos();
}
```

### Explicação

Aqui foram definidos os métodos principais usados pela aplicação:

- salvar um item
- buscar um item por id
- listar todos os itens
- restaurar a disponibilidade dos itens no reinício do sistema

O ponto importante é que **essa interface não fala nada sobre arquivo CSV**.

Ela representa apenas o que a aplicação espera usar.

---

# 3. Adapter principal: `RepositorioItemArquivoAdapter`

Esse é o Adapter principal do projeto.

```java
public class RepositorioItemArquivoAdapter implements RepositorioItem
```

### Explicação

Essa linha é a mais importante para entender o padrão.

O `RepositorioItemArquivoAdapter` implementa `RepositorioItem`, então a aplicação entende que está usando exatamente o formato que ela espera: um repositório de itens.

Mas, por trás, essa classe não está acessando banco de dados, nem objetos em memória:  
ela está lidando com **arquivo CSV**.

Ou seja:

- para a aplicação, ele parece um `RepositorioItem`
- internamente, ele trabalha com leitura e escrita em arquivo

É exatamente aí que está a adaptação.

---

# 4. Função `listarTodos()`

Aqui ocorre a tradução do CSV para `Item`.

```java
@Override
public List<Item> listarTodos() {
    if (!Files.exists(caminhoArquivo)) {
        return new ArrayList<>();
    }

    try {
        List<String> linhas = Files.readAllLines(caminhoArquivo);
        List<Item> itens = new ArrayList<>();

        for (String linha : linhas) {
            if (linha.isBlank()) {
                continue;
            }

            ItemRegistroCsv registro = mapper.deLinha(linha);
            Item item = mapper.paraItem(registro);
            itens.add(item);
        }

        return itens;
    } catch (IOException e) {
        throw new IllegalStateException("Erro ao ler arquivo de itens.", e);
    }
}
```

### Explicação

A aplicação espera receber uma lista de `Item`.

Mas o arquivo `.csv` não devolve objetos do tipo `Item`.  
Ele devolve apenas **linhas de texto**.

Então essa função faz o seguinte:

1. lê todas as linhas do arquivo
2. percorre linha por linha
3. transforma cada linha em um objeto intermediário (`ItemRegistroCsv`)
4. converte esse registro em um objeto `Item`
5. adiciona esse item na lista final

No final, ela devolve para a aplicação exatamente o que a aplicação queria: uma lista de `Item`.

Ou seja, aqui acontece a tradução:

**CSV → Item**

---

# 5. Função `salvar()`

Aqui a aplicação entrega um `Item`, e o adapter prepara a atualização dos dados antes de persistir.

```java
@Override
public void salvar(Item item) {
    List<Item> itens = listarTodos();
    List<Item> atualizados = new ArrayList<>();
    boolean substituido = false;

    for (Item existente : itens) {
        if (existente.getId().equals(item.getId())) {
            atualizados.add(item);
            substituido = true;
        } else {
            atualizados.add(existente);
        }
    }

    if (!substituido) {
        atualizados.add(item);
    }

    persistir(atualizados);
}
```

### Explicação

Essa função recebe um `Item` e precisa refletir isso no arquivo.

O que ela faz:

1. carrega os itens já existentes
2. percorre a lista atual
3. se encontrar um item com o mesmo id, substitui pelo item novo
4. se não encontrar, adiciona o item ao final
5. chama `persistir()` para gravar a nova lista no arquivo

Ou seja, essa função primeiro atualiza o estado lógico da lista e depois delega a gravação real para a função `persistir()`.

---

# 6. Função `persistir()`

Aqui ocorre a tradução de `Item` para texto no formato esperado pelo CSV.

```java
private void persistir(List<Item> itens) {
    List<String> linhas = new ArrayList<>();

    for (Item item : itens) {
        ItemRegistroCsv registro = mapper.deItem(item);
        String linha = mapper.paraLinha(registro);
        linhas.add(linha);
    }

    try {
        Files.write(caminhoArquivo, linhas);
    } catch (IOException e) {
        throw new IllegalStateException("Erro ao salvar arquivo de itens.", e);
    }
}
```

### Explicação

Essa função recebe uma lista de `Item` e transforma tudo em linhas de texto.

Ela faz isso em etapas:

1. percorre item por item
2. transforma o `Item` em `ItemRegistroCsv`
3. transforma o `ItemRegistroCsv` em uma linha de texto CSV
4. adiciona essa linha na lista
5. grava a lista inteira no arquivo com `Files.write(...)`

Então aqui acontece a tradução no sentido inverso:

**Item → CSV**

---

# 7. Sobre os outros adapters

Os outros adapters do projeto são:

- `RepositorioUsuarioMemoriaAdapter`
- `RepositorioEmprestimoMemoriaAdapter`

### Explicação

Eles seguem a mesma ideia estrutural de implementar os contratos esperados pela aplicação, mas usam **armazenamento em memória** em vez de arquivo.

Por isso:

- os usuários não ficam salvos ao reiniciar
- os empréstimos não ficam salvos ao reiniciar

Essa decisão foi tomada por simplicidade, porque o foco principal do projeto é demonstrar o Adapter com arquivo CSV no repositório de itens.

Por isso, o Adapter principal da apresentação é o:

- `RepositorioItemArquivoAdapter`

---

# 8. Sobre `ItemRegistroCsv`

Essa classe representa o formato do dado persistido no CSV.

```java
package biblioteca.infrastructure.persistence;

public class ItemRegistroCsv {
    private final String tipo;
    private final String id;
    private final String titulo;
    private final String campoExtra1;
    private final String campoExtra2;
    private final boolean disponivel;

    public ItemRegistroCsv(
            String tipo,
            String id,
            String titulo,
            String campoExtra1,
            String campoExtra2,
            boolean disponivel
    ) {
        this.tipo = tipo;
        this.id = id;
        this.titulo = titulo;
        this.campoExtra1 = campoExtra1;
        this.campoExtra2 = campoExtra2;
        this.disponivel = disponivel;
    }

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCampoExtra1() {
        return campoExtra1;
    }

    public String getCampoExtra2() {
        return campoExtra2;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
```

### Explicação

Aqui está apenas o formato do registro no arquivo.

Ele representa os campos que aparecem no CSV, como:

- tipo
- id
- título
- campos extras
- disponibilidade

Exemplo de `itens.csv`:

```text
LIVRO;I1;Clean Code;Robert C. Martin;9780132350884;true
PERIODICO;I2;Revista Java;10;;true
```

Dá para perceber que os atributos do `ItemRegistroCsv` seguem exatamente essa estrutura.

Ele não representa a regra de negócio da biblioteca.  
Ele representa apenas o **formato persistido no arquivo**.

---

# 9. Sobre `ItemCsvMapper`

Essa classe concentra a conversão entre os formatos.

```java
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
```

### Explicação

Aqui é onde realmente ocorre a tradução entre os formatos.

As duas funções mais importantes para mostrar são:

- `deLinha()`
- `paraLinha()`

### `deLinha()`
Essa função pega uma linha do CSV, quebra essa linha em partes e monta um `ItemRegistroCsv`.

Ou seja:

**linha CSV → ItemRegistroCsv**

### `paraLinha()`
Essa função faz o caminho contrário.

Ela pega um `ItemRegistroCsv` e transforma em uma linha de texto pronta para ser gravada no arquivo.

Ou seja:

**ItemRegistroCsv → linha CSV**

### Por que isso foi separado?
Essa decisão foi tomada por organização.

Se toda essa lógica ficasse dentro do adapter principal, ele ficaria com responsabilidades demais.

Separando essa conversão em uma classe específica, o projeto fica mais organizado e respeita melhor os princípios de separação de responsabilidade, coesão e organização arquitetural.

---

# Resumo final

O Adapter principal do projeto é o `RepositorioItemArquivoAdapter`.

Ele existe porque a aplicação espera trabalhar com um `RepositorioItem`, mas os dados reais estão armazenados em um arquivo CSV.

Então o adapter:

- recebe chamadas da aplicação no formato esperado
- traduz essas chamadas para leitura e escrita de arquivo
- converte CSV em `Item`
- converte `Item` em CSV

Os demais elementos, como `ItemRegistroCsv` e `ItemCsvMapper`, existem para organizar essa adaptação de forma mais clara e coesa.

---

# Transparência

Este arquivo foi produzido com apoio de inteligência artificial para auxiliar na organização e redação do conteúdo, com finalidade acadêmica e educacional.
