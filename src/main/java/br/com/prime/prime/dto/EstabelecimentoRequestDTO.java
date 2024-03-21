package br.com.prime.prime.dto;

import br.com.prime.prime.models.Usuario;
import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoRequestDTO {
     
    @NotBlank(message = "Nome não informado")
    private String nome;
    @NotBlank(message = "Telefone não informado")
    private String telefone;
    @NotBlank(message = "Horario de atendimento não informado")
    private String horarioAtendimento;
    @NotBlank(message = "cep obrigatório")
    private String cep;
    private int numero;
    @NotBlank(message = "Cidade não informada")
    private String cidade;
    @NotBlank(message = "Logradouro não informado")
    private String logradouro;
    @NotBlank(message = "Bairro não informado")
    private String bairro;
    @NotBlank(message = "Estado não informado")
    private String estado;
    @CNPJ(message = "Campo inválido")
    @NotBlank(message = "CNPJ não informado")
    private String cnpj;
    private Long idUsuario;


    public EstabelecimentoRequestDTO(String teste, String number, String s, String cg, String ruaTeste, String ms, String s1, Usuario usuario) {
    }
}
