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
- mostrar como princípios como **SOLID** e **GRASP** podem aparecer no código.

---

## Funcionalidades atuais

O sistema simula um **gerenciamento de biblioteca** com interface em **menu no console**, permitindo:

- cadastro de alunos;
- cadastro de professores;
- cadastro de livros;
- cadastro de periódicos;
- listagem do acervo;
- realização de empréstimos;
- devolução de itens.

---

## Interface escolhida

A interface escolhida foi um **menu em console**, pois o foco do projeto está na **arquitetura, na organização do código e na aplicação dos padrões**, e não em interface gráfica.

Essa decisão foi intencional para manter o projeto mais adequado ao contexto acadêmico e facilitar a demonstração do funcionamento do sistema.

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

Neste sistema, a aplicação trabalha com abstrações de repositório, como por exemplo:

- `RepositorioItem`
- `RepositorioUsuario`
- `RepositorioEmprestimo`

Entretanto, a forma concreta de armazenamento pode variar.

A classe `RepositorioItemArquivoAdapter` atua como **Adapter**, pois faz a ponte entre:

- o que a aplicação espera usar: a interface `RepositorioItem`;
- e a forma concreta de persistência em arquivo `.csv`.

Ou seja, o Adapter traduz:

- **objetos do domínio → formato de arquivo**
- **dados do arquivo → objetos do domínio**

Além disso, o projeto também utiliza adapters em memória para usuários e empréstimos, como:

- `RepositorioUsuarioMemoriaAdapter`
- `RepositorioEmprestimoMemoriaAdapter`

Essas implementações reforçam a ideia de desacoplamento entre a aplicação e os detalhes de persistência.

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