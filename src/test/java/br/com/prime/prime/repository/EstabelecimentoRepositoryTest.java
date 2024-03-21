package br.com.prime.prime.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;

@DataJpaTest
public class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Test
    public void deve_buscar_estabelecimento_pelo_id() throws Exception {

        String nome = "Comper";
        String telefone = "6733886075";
        String horarioAtendimento = "8hr às 20hr";
        String cep = "79063550";
        int numero = 2023;
        String cidade = "Campo Grande";
        String logradouro = "Rua tchudusbangu";
        String bairro = "bairro teste";
        String estado = "MS";
        String cnpj = "67.596.818/0001-90";
        Usuario usuario = new Usuario();
        Estabelecimento estabelecimento = new Estabelecimento(nome, telefone, horarioAtendimento, cep, numero, cidade, logradouro, bairro, estado, cnpj, usuario);
        estabelecimentoRepository.save(estabelecimento);

        Estabelecimento estabelecimentoRetornado = estabelecimentoRepository.findById(estabelecimento.getId()).orElse(null);

        assertThat(estabelecimentoRetornado).isNotNull();
        assertThat(estabelecimentoRetornado.getId()).isEqualTo(estabelecimento.getId());
        assertThat(estabelecimentoRetornado.getNome()).isEqualTo(nome);
        assertThat(estabelecimentoRetornado.getTelefone()).isEqualTo(telefone);
        assertThat(estabelecimentoRetornado.getHorarioAtendimento()).isEqualTo(horarioAtendimento);
        assertThat(estabelecimentoRetornado.getNumero()).isEqualTo(numero);
        assertThat(estabelecimentoRetornado.getCidade()).isEqualTo(cidade);
        assertThat(estabelecimentoRetornado.getEstado()).isEqualTo(estado);
        assertThat(estabelecimentoRetornado.getCnpj()).isEqualTo(cnpj);
        assertThat(estabelecimentoRetornado.getUsuario()).isEqualTo(usuario);
    }

    @Test
    public void deve_salvar_um_estabelecimento() {

        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();

        estabelecimentoRepository.save(estabelecimento);

        Assertions.assertNotNull(estabelecimento.getId());
    }

    @Test
    public void deve_remover_estabelecimento() {
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().construir();
        estabelecimentoRepository.save(estabelecimento);

        estabelecimentoRepository.deleteById(estabelecimento.getId());

        Optional<Estabelecimento> estabelecimentoBuscado = estabelecimentoRepository.findById(estabelecimento.getId());

        Assertions.assertFalse(estabelecimentoBuscado.isPresent());
    }

    @Test
    public void deve_buscar_estabelecimento_pelo_nome() {
        String nome = "comper";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comNome(nome).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByNomeContainingIgnoreCase(nome);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_pelo_telefone() {
        String telefone = "6733886075";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comTelefone(telefone).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository
                .findByTelefoneContainingIgnoreCase(telefone);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_pelo_logradouro() {
        String logradouro = "Rua tchudusbangu";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comLogradouro(logradouro).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository
                .findByLogradouroContainingIgnoreCase(logradouro);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_por_cidade() {
        String cidade = "Campo Grande";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comCidade(cidade).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository
                .findByCidadeContainingIgnoreCase(cidade);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_por_estado() {
        String estado = "MS";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comEstado(estado).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository
                .findByEstadoContainingIgnoreCase(estado);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_pelo_cnpj() {
        String cnpj = "67.596.818/0001-90";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comCnpj(cnpj).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByCnpjContaining(cnpj);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }
}
