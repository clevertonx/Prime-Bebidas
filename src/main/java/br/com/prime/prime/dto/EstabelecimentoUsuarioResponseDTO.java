package br.com.prime.prime.dto;
import br.com.prime.prime.models.Estabelecimento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoUsuarioResponseDTO {
    private long id;
    private String nome;
    private String logradouro;
    private String telefone;
    private String cnpj;
   

    public EstabelecimentoUsuarioResponseDTO(Estabelecimento estabelecimento) {
        this.id = estabelecimento.getId();
        this.nome = estabelecimento.getNome();
        this.logradouro = estabelecimento.getLogradouro();
        this.telefone = estabelecimento.getTelefone();
        this.cnpj = estabelecimento.getCnpj();
    }

}
