# Sistema de Biblioteca em Java — Adapter (GoF)

## Sobre o projeto

Este projeto foi desenvolvido com **finalidade acadêmica**, como parte dos estudos de **Design Patterns** e **Arquitetura de Sistemas**.

A proposta não é construir um sistema comercial completo, mas sim criar um exemplo didático, organizado e compreensível, que possa ser utilizado para **estudo**, **apresentação em aula** e **apoio a outros alunos** que estejam aprendendo padrões de projeto orientados a objetos.

O foco principal deste projeto está na aplicação do padrão **Adapter**, descrito no catálogo **GoF (Gang of Four)**, além da utilização de conceitos de **SOLID** e **GRASP** quando fizerem sentido dentro da modelagem proposta.

---

## Objetivo

O objetivo deste projeto é demonstrar, de forma prática, como estruturar um sistema em **Java** com separação de responsabilidades e uso de padrões de projeto.

Mais especificamente, este trabalho busca:

- apresentar uma estrutura de projeto em camadas;
- aplicar boas práticas de orientação a objetos;
- demonstrar o padrão **Adapter** de forma realista;
- servir como material de estudo para alunos que estão aprendendo **Design Patterns**;
- mostrar como padrões como **SOLID** e **GRASP** podem aparecer no código.

---

## Contexto do sistema

O sistema simula um **gerenciamento de biblioteca**, com operações como:

- cadastro de usuários;
- cadastro de itens do acervo;
- consulta de itens;
- realização de empréstimos;
- devolução de itens.

A interface escolhida foi um **menu em console**, pois o foco do projeto está na **arquitetura e no código**, e não em interface gráfica.

---

## Padrão de projeto em destaque: Adapter

O padrão **Adapter** é o principal padrão explorado neste projeto.

### O que é o Adapter?

O **Adapter** é um padrão estrutural do GoF cuja finalidade é **compatibilizar interfaces diferentes**, permitindo que classes com formatos incompatíveis consigam trabalhar juntas sem que seja necessário alterar suas estruturas internas.

Em termos simples, ele funciona como um **tradutor** entre duas partes do sistema.

### Exemplo do mundo real

Um exemplo clássico é o de um **adaptador de tomada**:

- a tomada da parede possui um padrão;
- o plugue do aparelho possui outro;
- em vez de modificar a parede ou cortar o cabo do aparelho, usamos um **adaptador**.

No software, a lógica é a mesma:
- uma parte do sistema espera um tipo de interface;
- a outra oferece outra forma de comunicação;
- o **Adapter** faz essa ponte.

### Como isso aparece neste projeto?

Neste sistema, a aplicação trabalha com a abstração de repositório, como por exemplo:

- `RepositorioItem`
- `RepositorioUsuario`
- `RepositorioEmprestimo`

Entretanto, a persistência real pode ocorrer em arquivo, memória ou outra tecnologia.

A classe `RepositorioItemArquivoAdapter` atua como **Adapter**, pois faz a ponte entre:

- o que a aplicação espera usar (`RepositorioItem`);
- e a forma concreta de armazenamento em arquivo.

Ou seja, o Adapter traduz:

- **objetos do domínio → formato de arquivo**
- **dados do arquivo → objetos do domínio**

Assim, o restante do sistema não precisa conhecer os detalhes técnicos de leitura e escrita em arquivo.

---

## Estrutura do projeto

```text
src/
  biblioteca/
    app/
      Main.java
      ConsoleMenu.java
    application/
      ServicoCadastroItem.java
      ServicoCadastroUsuario.java
      ServicoConsultaAcervo.java
      ServicoEmprestimo.java
    domain/
      model/
        Item.java
        Livro.java
        Periodico.java
        Usuario.java
        Aluno.java
        Professor.java
        Emprestimo.java
      policy/
        PoliticaEmprestimo.java
        PoliticaEmprestimoAluno.java
        PoliticaEmprestimoProfessor.java
      repository/
        RepositorioItem.java
        RepositorioUsuario.java
        RepositorioEmprestimo.java
    infrastructure/
      adapter/
        RepositorioItemArquivoAdapter.java
        RepositorioUsuarioMemoriaAdapter.java
        RepositorioEmprestimoMemoriaAdapter.java
      persistence/
        ItemRegistroCsv.java