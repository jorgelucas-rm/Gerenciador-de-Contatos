# Gerenciador-de-Contatos ☎

## Aluno 📚

**Nome:** Jorge Lucas Rodrigues Martins

**Faculdade:** Senac CE

**Turno:** Noite

## Objetivo 🎯

Criar uma aplicação capaz de gerenciar uma lista telefônica, esta aplicação deve conseguir adicionar, remover, buscar e listar os contatos. Os contatos são divididos em duas classes:

- Contatos pessoais
  >Capaz de armazenar Nome, Telefone, Email, Data de Aniversário e Endereço.
- Contatos profissionais
  >Capaz de armazenar Nome, Telefone, Email, Empresa e Cargo.

## Codificação 🖥

### Main

Aqui temos a Main, ela funciona dentro de um loop `while` e interage diretamente com a classe agenda.

``` Java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int opcao = -1;
        Agenda agenda = new Agenda();
        while (opcao != 5) {

            System.out.println("\nQual operação você deseja realizar?\n\n" +
                    "[1] Adicionar um contato\n" +
                    "[2] Remover um contato\n" +
                    "[3] Buscar um contato\n" +
                    "[4] Listar contatos\n" +
                    "[5] Sair\n");

            Scanner scan = new Scanner(System.in);
            System.out.println("Opção desejada:");
            opcao = scan.nextInt();

            switch (opcao) {
                case 1:
                    agenda.adicionarContato();
                    break;

                case 2:
                    agenda.removerContato();
                    break;

                case 3:
                    agenda.buscarContato();
                    break;

                case 4:
                    agenda.listarContatos();
                    break;

                case 5:
                    scan.close();
                    break;
            }
        }
    }
}
```

### Classe agenda

A classe agenda é responsável pelos métodos adicionar, remover, buscar e listar.

``` Java
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Agenda {

    private List<Contato> contatos = new ArrayList<>();
    Scanner scan = new Scanner(System.in);
    int opcao;

    public void adicionarContato() {
        System.out.println("\nQual o tipo de contato que você deseja adicionar?\n\n" +
                "[1] Adicionar um contato pessoal\n" +
                "[2] Adicionar um contato profissional\n");

        System.out.println("Opção desejada:");
        opcao = scan.nextInt();
        scan.nextLine();

        if (opcao != 1 && opcao != 2){
            System.out.println("\nOpção invalida");
            adicionarContato();
            return;
        }

        System.out.println("Informar nome do contato:");
        String nome = scan.nextLine();

        System.out.println("Informar telefone do contato:");
        String telefone = scan.nextLine();

        System.out.println("Informar email do contato:");
        String email = scan.nextLine();

        if (opcao == 1) {
            System.out.println("Informar aniversário do contato:");
            String aniversario = scan.nextLine();

            System.out.println("Informar endereço do contato:");
            String endereco = scan.nextLine();

            contatos.add(new ContatoPessoal(nome, email, Long.parseLong(telefone), aniversario, endereco));
        } else {
            System.out.println("Informar empresa do contato:");
            String empresa = scan.nextLine();

            System.out.println("Informar cargo do contato:");
            String cargo = scan.nextLine();

            contatos.add(new ContatoProfissional(nome, email, Long.parseLong(telefone), empresa, cargo));
        }
    }

    public void removerContato() {
        System.out.println("\nQual o nome do contato que deseja remover?");
        String deletar = scan.nextLine();

        if ((contatos.removeIf(e -> Objects.equals(e.getNome(), deletar)))) {
            System.out.println("\nContato deletado com sucesso\n");
        } else {
            System.out.println("\nO contato não foi identificado ou não existe\n");
        }
    }

    public void buscarContato() {

        System.out.println("\nQual o tipo de contato que você deseja buscar?\n\n" +
                "[1] Buscar um contato pessoal\n" +
                "[2] Buscar um contato profissional\n");

        System.out.println("Opção desejada:");
        opcao = scan.nextInt();
        scan.nextLine();

        if (opcao != 1 && opcao != 2){
            System.out.println("\nOpção invalida");
            buscarContato();
            return;
        }

        System.out.println("Qual o nome do contato que deseja buscar?");
        String buscar = scan.nextLine();

        for (Contato contato : contatos) {
            if (Objects.equals(buscar, contato.getNome())) {
                System.out.println("\nNome: " + contato.getNome() +
                        "\nEmail: " + contato.getEmail() +
                        "\nTelefone: " + contato.getTelefone());

                if (opcao == 1) {
                    System.out.println("Aniversário: " + contato.getAdicional1() +
                            "\nEndereço: " + contato.getAdicional2());
                    return;
                } if (opcao == 2) {
                    System.out.println("Empresa: " + contato.getAdicional1() +
                            "\nCargo: " + contato.getAdicional2());
                    return;
                }
            }

        }

        System.out.println("\nO contato não foi encontrado ou não existe\n");
        return;
    }

    public void listarContatos() {
        System.out.println("\nLista de contatos: \n");
        for (int i = 0; i < contatos.size(); i++) {
            System.out.println(i + 1 + "º - " + contatos.get(i).getNome() + " (" + contatos.get(i).getTelefone() + ").");
        }
    }
}
```

### Classe abstrata contato

Nessa classe temos a criação do objeto contato, nela criamos os métodos de set e get para todos os atributos que virão a ser utilizados, sendo dois deles atributos especiais dedicados a cada uma de suas classe filho.

```Java
public abstract class Contato {
    private String nome, email;
    private long telefone;

    Contato(String nome, String email, long telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // SET e GET Nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    // SET e GET Email
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // SET e GET Telefone
    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public long getTelefone() {
        return telefone;
    }

    // SET e GET Adicionais

    public abstract String getAdicional1();

    public abstract void setAdicional1(String parametro);

    public abstract String getAdicional2();

    public abstract void setAdicional2(String parametro);
}

```

### Classe filho Contato pessoal

Nessa classe temos o construtor dos contatos pessoais e o `@Override` dos métodos SET e GET.

``` Java
public class ContatoPessoal extends Contato {
    private String aniversario, endereco;

    ContatoPessoal(String nome, String email, long telefone, String aniversario, String endereco) {
        super(nome, email, telefone);
        this.aniversario = aniversario;
        this.endereco = endereco;
    }

    // SET e GET Aniversário
    @Override
    public void setAdicional1(String aniversario) {
        this.aniversario = aniversario;
    }

    @Override
    public String getAdicional1() {
        return aniversario;
    }

    // SET e GET Endereço
    @Override
    public void setAdicional2(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String getAdicional2() {
        return endereco;
    }
}
```

### Classe filho Contato profissional

Nessa classe temos o construtor dos contatos profissionais e o `@Override` dos métodos SET e GET.

``` Java
public class ContatoProfissional extends Contato {
    private String empresa, cargo;

    ContatoProfissional(String nome, String email, long telefone, String empresa, String cargo) {
        super(nome, email, telefone);
        this.empresa = empresa;
        this.cargo = cargo;
    }

    // SET e GET Empresa
    @Override
    public void setAdicional1(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String getAdicional1() {
        return empresa;
    }

    // SET e GET Cargo
    @Override
    public void setAdicional2(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String getAdicional2() {
        return cargo;
    }
}

```
