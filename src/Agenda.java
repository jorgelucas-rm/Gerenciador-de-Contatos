import java.util.Scanner;
import java.sql.*;

public class Agenda {
    private Connection conn = null;

    Agenda() {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://acilab.com.br:3309/jorge_martins",
                    "root",
                    "admin"
            );
        } catch (SQLException e) {
        }
    }

    Scanner scan = new Scanner(System.in);
    int opcao;

    public void adicionarContato() {
        System.out.println(
                "\nQual o tipo de contato que você deseja adicionar?\n\n" +
                        "[1] Adicionar um contato pessoal\n" +
                        "[2] Adicionar um contato profissional\n"
        );
        System.out.println("Opção desejada:");
        opcao = scan.nextInt();
        scan.nextLine();

        while (1 < opcao && opcao > 2) {
            System.out.println("\nOpção inválida\n");
            System.out.println("Opção desejada:");
            opcao = scan.nextInt();
            scan.nextLine();
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

            Contato contato = new ContatoPessoal(nome, email, telefone, aniversario, endereco);

            try {
                String query = "INSERT INTO Contatos (tipo, nome, telefone, email, adicional1, adicional2) VALUES ('Pessoal',?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, contato.getNome());
                pstmt.setString(2, contato.getTelefone());
                pstmt.setString(3, contato.getEmail());
                pstmt.setString(4, contato.getAdicional1());
                pstmt.setString(5, contato.getAdicional2());
                pstmt.execute();

                pstmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (opcao == 2) {
            System.out.println("Informar empresa do contato:");
            String empresa = scan.nextLine();

            System.out.println("Informar cargo do contato:");
            String cargo = scan.nextLine();

            Contato contato = new ContatoProfissional(nome, email, telefone, empresa, cargo);

            try {

                String query = "INSERT INTO Contatos (tipo, nome, telefone, email, adicional1, adicional2) VALUES ('Profissional',?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, contato.getNome());
                pstmt.setString(2, contato.getTelefone());
                pstmt.setString(3, contato.getEmail());
                pstmt.setString(4, contato.getAdicional1());
                pstmt.setString(5, contato.getAdicional2());
                pstmt.execute();

                pstmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removerContato() {
        System.out.println("\nQual o nome do contato que deseja remover?");
        String deletar = scan.nextLine();

        try {
            String query = "SELECT * FROM Contatos WHERE nome = '" + deletar + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if (rs.getRow() > 0) {
                if (rs.getString("nome").equalsIgnoreCase(deletar)) {
                    query = "DELETE FROM Contatos WHERE nome = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, deletar);
                    pstmt.execute();
                    pstmt.close();
                    System.out.println("O contato foi deletado com sucesso");

                }
            } else if (rs.getRow() == 0) {
                System.out.println("\nO contato não foi identificado ou não existe");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarContato() {

        Contato contato = null;

        System.out.println("\nInforme um dado (Nome,telefone,email, etc) do contato que deseja atualizar:");
        String alterar = scan.nextLine();

        try {
            String query = "SELECT * FROM Contatos " +
                    "WHERE nome = '" + alterar +
                    "' OR telefone = '" + alterar +
                    "' OR email = '" + alterar +
                    "' OR adicional1 = '" + alterar +
                    "' OR adicional2 = '" + alterar + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            if (rs.getRow() > 0) {
                if (rs.getString("nome").equalsIgnoreCase(alterar)
                        || rs.getString("telefone").equalsIgnoreCase(alterar)
                        || rs.getString("email").equalsIgnoreCase(alterar)
                        || rs.getString("adicional1").equalsIgnoreCase(alterar)
                        || rs.getString("adicional1").equalsIgnoreCase(alterar)) {

                    if (rs.getString("tipo").equals("Pessoal")) {
                        contato = new ContatoPessoal(rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("telefone"),
                                rs.getString("adicional1"),
                                rs.getString("adicional2"));

                        System.out.println(
                                "\nDados do contato:" +
                                        "\nNome: " + contato.getNome() +
                                        "\nTelefone: " + contato.getTelefone() +
                                        "\nEmail: " + contato.getEmail() +
                                        "\nAniversário: " + contato.getAdicional1() +
                                        "\nEndereço: " + contato.getAdicional2()
                        );

                    } else if (rs.getString("tipo").equals("Profissional")) {
                        contato = new ContatoProfissional(rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("telefone"),
                                rs.getString("adicional1"),
                                rs.getString("adicional2"));

                        System.out.println(
                                "\nDados do contato:" +
                                        "\nNome: " + contato.getNome() +
                                        "\nTelefone: " + contato.getTelefone() +
                                        "\nEmail: " + contato.getEmail() +
                                        "\nEmpresa: " + contato.getAdicional1() +
                                        "\nCargo: " + contato.getAdicional2()
                        );
                    }

                    System.out.println("\n" +
                            "Qual o dado você deseja alterar?\n\n" +
                            "[1] Nome\n" +
                            "[2] Telefone\n" +
                            "[3] Email"
                    );

                    if (contato instanceof ContatoPessoal) {
                        System.out.println("[4] Aniversário\n" + "[5] Endereço\n");

                    } else if (contato instanceof ContatoProfissional) {
                        System.out.println("[4] Aniversário\n" + "[5] Endereço\n");

                    }

                    System.out.println("Opção desejada:");
                    opcao = scan.nextInt();
                    scan.nextLine();

                    while (1 < opcao && opcao > 5) {
                        System.out.println("\nOpção inválida\n");
                        System.out.println("Opção desejada:");
                        opcao = scan.nextInt();
                        scan.nextLine();
                    }
                    String novoValor;

                    PreparedStatement pstmt = null;

                    switch (opcao) {

                        case 1:
                            System.out.println("\nInforme o valor que você deseja inserir no lugar de " +
                                    contato.getNome() + ":");
                            novoValor = scan.nextLine();
                            contato.setNome(novoValor);

                            query = "UPDATE Contatos SET nome = ? WHERE nome = ? OR telefone = ? OR email = ? OR adicional1 = ? OR adicional2 = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, contato.getNome());
                            break;

                        case 2:
                            System.out.println("\nInforme o valor que você deseja inserir no lugar de " +
                                    contato.getTelefone() + ":");
                            novoValor = scan.nextLine();
                            contato.setTelefone(novoValor);

                            query = "UPDATE Contatos SET telefone = ? WHERE nome = ? OR telefone = ? OR email = ? OR adicional1 = ? OR adicional2 = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, contato.getTelefone());
                            break;

                        case 3:
                            System.out.println("\nInforme o valor que você deseja inserir no lugar de " +
                                    contato.getEmail() + ":");
                            novoValor = scan.nextLine();
                            contato.setEmail(novoValor);

                            query = "UPDATE Contatos SET email = ? WHERE nome = ? OR telefone = ? OR email = ? OR adicional1 = ? OR adicional2 = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, contato.getEmail());
                            break;

                        case 4:
                            System.out.println("\nInforme o valor que você deseja inserir no lugar de "
                                    + contato.getAdicional1() + ":");
                            novoValor = scan.nextLine();
                            contato.setAdicional1(novoValor);

                            query = "UPDATE Contatos SET adicional1 = ? WHERE nome = ? OR telefone = ? OR email = ? OR adicional1 = ? OR adicional2 = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, contato.getAdicional1());
                            break;

                        case 5:
                            System.out.println("\nInforme o valor que você deseja inserir no lugar de "
                                    + contato.getAdicional2() + ":");
                            novoValor = scan.nextLine();
                            contato.setAdicional2(novoValor);

                            query = "UPDATE Contatos SET adicional2 = ? WHERE nome = ? OR telefone = ? OR email = ? OR adicional1 = ? OR adicional2 = ?";
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, contato.getAdicional2());
                            break;
                    }

                    pstmt.setString(2, alterar);
                    pstmt.setString(3, alterar);
                    pstmt.setString(4, alterar);
                    pstmt.setString(5, alterar);
                    pstmt.setString(6, alterar);
                    pstmt.execute();

                    System.out.println("\nO contato foi alterado com sucesso");

                }
            } else if (rs.getRow() == 0) {
                System.out.println("\nO contato não foi identificado ou não existe");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buscarContato() {

        System.out.println("\nInforme um dado (Nome,telefone,email, etc) do contato que deseja buscar:");
        String buscar = scan.nextLine();

        try {
            String query = "SELECT * FROM Contatos " +
                    "WHERE nome = '" + buscar +
                    "' OR telefone = '" + buscar +
                    "' OR email = '" + buscar +
                    "' OR adicional1 = '" + buscar +
                    "' OR adicional2 = '" + buscar + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            if (rs.getRow() > 0) {
                if (rs.getString("nome").equalsIgnoreCase(buscar)
                        || rs.getString("telefone").equalsIgnoreCase(buscar)
                        || rs.getString("email").equalsIgnoreCase(buscar)
                        || rs.getString("adicional1").equalsIgnoreCase(buscar)
                        || rs.getString("adicional1").equalsIgnoreCase(buscar)) {

                    if (rs.getString("tipo").equals("Pessoal")) {
                        Contato pessoal = new ContatoPessoal(rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("telefone"),
                                rs.getString("adicional1"),
                                rs.getString("adicional2"));

                        System.out.println(
                                "\nDados do contato:" +
                                        "\nNome: " + pessoal.getNome() +
                                        "\nTelefone: " + pessoal.getTelefone() +
                                        "\nEmail: " + pessoal.getEmail() +
                                        "\nAniversário: " + pessoal.getAdicional1() +
                                        "\nEndereço: " + pessoal.getAdicional2()
                        );

                    } else if (rs.getString("tipo").equals("Profissional")) {
                        Contato profissional = new ContatoProfissional(rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("telefone"),
                                rs.getString("adicional1"),
                                rs.getString("adicional2"));

                        System.out.println(
                                "\nDados do contato:" +
                                        "\nNome: " + profissional.getNome() +
                                        "\nTelefone: " + profissional.getTelefone() +
                                        "\nEmail: " + profissional.getEmail() +
                                        "\nEmpresa: " + profissional.getAdicional1() +
                                        "\nCargo: " + profissional.getAdicional2()
                        );
                    }
                }
            } else if (rs.getRow() == 0) {
                System.out.println("\nO contato não foi identificado ou não existe");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarContatos() {

        try {
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

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
