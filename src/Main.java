import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://acilab.com.br:3309/db2603";
        String user = "root";
        String password = "admin";
        String query = "SELECT name, street FROM customers";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println(rs.getString("name") + ", " + rs.getString("street"));
            }

            System.out.println("Conectado com suceso.");

        } catch(SQLException e){
            e.printStackTrace();
        }


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
