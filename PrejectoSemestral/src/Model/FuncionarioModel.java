package Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "funcionario")
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment para o id
    @Column(name = "id_funcionario")
    private int idFuncionario;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cargo", nullable = false)
    private String cargo;

    @Column(name = "salario", nullable = false)
    private double salario;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Temporal(TemporalType.DATE) // Para armazenar a data corretamente no banco
    @Column(name = "data_nascimento")
    private Date dataNascimento;

    @Column(name = "morada")
    private String morada;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "contato_emergencia")
    private String contatoEmergencia;

    @Column(name = "genero")
    private String genero;

    @Column(name = "foto_path")
    private String fotoPath;

    @Column(name = "existe", nullable = false)
    private boolean existe;  // Campo para indicar se o funcionário está ativo ou não

    // Getters e Setters
    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(String contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
}
