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
@Entity(name = "usuario")
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String senha;
    
    @OneToMany(mappedBy = "usuario")
    @Cascade(CascadeType.PERSIST)
    private List<Estabelecimento> estabelecimentos;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}
