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
