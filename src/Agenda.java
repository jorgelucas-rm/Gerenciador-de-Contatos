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
