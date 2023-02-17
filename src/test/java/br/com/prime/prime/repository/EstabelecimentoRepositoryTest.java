package br.com.prime.prime.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.prime.prime.Builders.EstabelecimentoBuilder;
import br.com.prime.prime.models.Estabelecimento;

@DataJpaTest
public class EstabelecimentoRepositoryTest {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

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

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByTelefoneContainingIgnoreCase(telefone);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_pelo_logradouro() {
        String logradouro = "Rua tchudusbangu";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comLogradouro(logradouro).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByLogradouroContainingIgnoreCase(logradouro);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_por_cidade() {
        String cidade = "Campo Grande";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comCidade(cidade).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByCidadeContainingIgnoreCase(cidade);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }

    @Test
    public void deve_buscar_estabelecimento_por_estado() {
        String estado = "MS";
        Estabelecimento estabelecimento = new EstabelecimentoBuilder().comEstado(estado).construir();
        estabelecimentoRepository.save(estabelecimento);

        List<Estabelecimento> estabelecimentoRetornado = estabelecimentoRepository.findByEstadoContainingIgnoreCase(estado);

        Assertions.assertTrue(estabelecimentoRetornado.contains(estabelecimento));
    }
}
