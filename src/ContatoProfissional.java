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
