import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.sql.*;

public class Agenda {

    private List<Contato> contatos = new ArrayList<>();

    public void jbdc() {
        String url= "jdbc:mysql://acilab.com.br:3309/db2603";
        String user= "root";
        String password= "admin";
        String query = "";
        
        try{
            Connection conn = DriverManager.getConnection(url,user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }





    Scanner scan = new Scanner(System.in);
    int opcao;

    public int tipoContato(Contato contato){
        if(contato instanceof ContatoPessoal){
            return 1;
        }
        else if(contato instanceof ContatoProfissional){
            return 2;
        }
        else {
            return -1;
        }
    }

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

            contatos.add(new ContatoPessoal(nome, email, telefone, aniversario, endereco));
        } if (opcao == 2) {
            System.out.println("Informar empresa do contato:");
            String empresa = scan.nextLine();

            System.out.println("Informar cargo do contato:");
            String cargo = scan.nextLine();

            contatos.add(new ContatoProfissional(nome, email, telefone, empresa, cargo));
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

        System.out.println("Qual o nome do contato que deseja buscar?");
        String buscar = scan.nextLine();

        for (Contato contato : contatos) {
            if (Objects.equals(buscar, contato.getNome())) {
                System.out.println("\nNome: " + contato.getNome() +
                        "\nEmail: " + contato.getEmail() +
                        "\nTelefone: " + contato.getTelefone());

                switch (tipoContato(contato)){
                    case 1:
                        System.out.println("Aniversário: " + contato.getAdicional1() +
                                "\nEndereço: " + contato.getAdicional2());
                        break;

                    case 2:
                        System.out.println("Empresa: " + contato.getAdicional1() +
                                "\nCargo: " + contato.getAdicional2());
                        break;
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
