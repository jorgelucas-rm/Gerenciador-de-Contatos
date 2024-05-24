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

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Agenda agenda = new Agenda();
        int opcao = -1;

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
import java.util.Scanner;
import java.sql.*;

public class Agenda {

    String url = "jdbc:mysql://acilab.com.br:3309/jorge_martins";
    String user = "root";
    String password = "admin";
    Scanner scan = new Scanner(System.in);
    int opcao;

    public void adicionarContato() {
        System.out.println("\nQual o tipo de contato que você deseja adicionar?\n\n" +
                "[1] Adicionar um contato pessoal\n" +
                "[2] Adicionar um contato profissional\n");

        System.out.println("Opção desejada:");
        opcao = scan.nextInt();
        scan.nextLine();

        if (opcao != 1 && opcao != 2) {
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

            Contato pessoal = new ContatoPessoal(nome, email, telefone, aniversario, endereco);

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String query = "INSERT INTO Contatos (tipo, nome, telefone, email, adicional1, adicional2) VALUES ('Pessoal',?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, pessoal.getNome());
                pstmt.setString(2, pessoal.getTelefone());
                pstmt.setString(3, pessoal.getEmail());
                pstmt.setString(4, pessoal.getAdicional1());
                pstmt.setString(5, pessoal.getAdicional2());
                pstmt.execute();
                pstmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (opcao == 2) {
            System.out.println("Informar empresa do contato:");
            String empresa = scan.nextLine();

            System.out.println("Informar cargo do contato:");
            String cargo = scan.nextLine();

            Contato profissional = new ContatoProfissional(nome, telefone, email, empresa, cargo);

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                String query = "INSERT INTO Contatos (tipo, nome, telefone, email, adicional1, adicional2) VALUES ('Profissional',?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, profissional.getNome());
                pstmt.setString(2, profissional.getTelefone());
                pstmt.setString(3, profissional.getEmail());
                pstmt.setString(4, profissional.getAdicional1());
                pstmt.setString(5, profissional.getAdicional2());
                pstmt.execute();
                pstmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removerContato() {
        System.out.println("\nQual o nome do contato que deseja remover?");
        String deletar = scan.nextLine();

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Contatos WHERE nome = '" + deletar + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            if (rs.getString("nome").equalsIgnoreCase(deletar)) {
                query = "DELETE FROM Contatos WHERE nome = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, deletar);
                pstmt.execute();
                pstmt.close();
                System.out.println("O contato foi deletado com sucesso");
            } else if (rs.getRow() == 0) {
                System.out.println("\nO contato não foi identificado ou não existe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarContato() {

        System.out.println("\nQual o nome do contato que deseja buscar?");
        String buscar = scan.nextLine();

        try {
            Connection conn = DriverManager.getConnection(url, user, password);

            String query = "SELECT * FROM Contatos WHERE nome = '" + buscar + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            if (rs.getString("nome").equalsIgnoreCase(buscar)) {
                if (rs.getString("tipo").equals("Pessoal")) {
                    Contato pessoal = new ContatoPessoal(rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("adicional1"),
                            rs.getString("adicional2"));

                    System.out.println("\nDados do contato:" +
                            "\nNome: " + pessoal.getNome() +
                            "\nTelefone: " + pessoal.getTelefone() +
                            "\nEmail: " + pessoal.getEmail() +
                            "\nAniversário: " + pessoal.getAdicional1() +
                            "\nEndereço: " + pessoal.getAdicional2());

                } else if (rs.getString("tipo").equals("Profissional")) {
                    Contato profissional = new ContatoProfissional(rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("adicional1"),
                            rs.getString("adicional2"));

                    System.out.println("\nDados do contato:" +
                            "\nNome: " + profissional.getNome() +
                            "\nTelefone: " + profissional.getTelefone() +
                            "\nEmail: " + profissional.getEmail() +
                            "\nEmpresa: " + profissional.getAdicional1() +
                            "\nCargo: " + profissional.getAdicional2());
                }
            } else if (rs.getRow() == 0) {
                System.out.println("\nO contato não foi identificado ou não existe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarContatos() {

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            String query = "SELECT * FROM Contatos WHERE tipo = 'Pessoal'";
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("\nLista de contatos pessoais: \n");

            while (rs.next()) {
                System.out.println(rs.getString("nome") +
                        " - (" + rs.getString("telefone") + ")");
            }

            query = "SELECT * FROM Contatos WHERE tipo = 'Profissional'";
            rs = stmt.executeQuery(query);
            System.out.println("\nLista de contatos profissionais: \n");

            while (rs.next()) {
                System.out.println(rs.getString("nome") +
                        " - (" + rs.getString("telefone") + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
