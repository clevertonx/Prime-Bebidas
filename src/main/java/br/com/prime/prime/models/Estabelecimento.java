package br.com.prime.prime.models;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Data
@NoArgsConstructor
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String telefone;
    private String horarioAtendimento;
    private int numero;
    private String cidade;
    private String logradouro;
    private String estado;
    private String cnpj;


    @ManyToOne
    @JoinColumn(name = "usuario")
    @Cascade(CascadeType.PERSIST)
    private Usuario usuario;

    @OneToMany(mappedBy = "estabelecimento", orphanRemoval = true)
    @Cascade(CascadeType.PERSIST)
    private List<Produto> produtos;

    public Estabelecimento(String nome, String telefone, String horarioAtendimento, int numero, String cidade,
            String logradouro, String estado, String cnpj, Usuario usuario) {

        this.nome = nome;
        this.telefone = telefone;
        this.horarioAtendimento = horarioAtendimento;
        this.numero = numero;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.estado = estado;
        this.cnpj = cnpj;
        this.usuario = usuario;
    }

}
