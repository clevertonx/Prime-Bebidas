package br.com.prime.prime.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
@Entity(name = "estabelecimento")
@EqualsAndHashCode(of = "id")
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
