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
