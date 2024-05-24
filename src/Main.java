import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Agenda agenda = null;
        int opcao = -1;
        while (opcao != 6) {

            System.out.println(
                    "\nQual operação você deseja realizar?\n\n" +
                            "[1] Adicionar um contato\n" +
                            "[2] Remover um contato\n" +
                            "[3] Atualizar um contato\n" +
                            "[4] Buscar um contato\n" +
                            "[5] Listar contatos\n" +
                            "[6] Sair\n"
            );

            Scanner scan = new Scanner(System.in);
            System.out.println("Opção desejada:");
            opcao = scan.nextInt();

            while (1 < opcao && opcao > 6) {
                System.out.println("\nOpção inválida\n");
                System.out.println("Opção desejada:");
                opcao = scan.nextInt();
                scan.nextLine();
            }

            switch (opcao) {
                case 1:
                    agenda = new Agenda();
                    agenda.adicionarContato();
                    break;

                case 2:
                    agenda = new Agenda();
                    agenda.removerContato();
                    break;

                case 3:
                    agenda = new Agenda();
                    agenda.atualizarContato();
                    break;

                case 4:
                    agenda = new Agenda();
                    agenda.buscarContato();
                    break;

                case 5:
                    agenda = new Agenda();
                    agenda.listarContatos();
                    break;

                case 6:
                    agenda = new Agenda();
                    scan.close();
                    break;
            }
        }
    }
}