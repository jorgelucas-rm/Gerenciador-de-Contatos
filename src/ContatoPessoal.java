public class ContatoPessoal extends Contato {
    private String aniversario, endereco;

    ContatoPessoal(String nome, String email, String telefone, String aniversario, String endereco) {
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
