package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.dto.EstabelecimentoUsuarioResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.UsuarioRepository;

@Component
public class EstabelecimentoMapperImpl implements EstabelecimentoMapper {
        @Autowired
        private UsuarioRepository usuarioRepository;

        @Override
        public Estabelecimento estabelecimentoRequestParaEstabelecimento(
                        EstabelecimentoRequestDTO estabelecimentoRequestDTO) {
                Usuario usuario = usuarioRepository
                                .findById(estabelecimentoRequestDTO.getIdUsuario())
                                .get();
                return new Estabelecimento(estabelecimentoRequestDTO.getNome(), estabelecimentoRequestDTO.getTelefone(),
                                estabelecimentoRequestDTO.getHorarioAtendimento(),
                                estabelecimentoRequestDTO.getNumero(), estabelecimentoRequestDTO.getCidade(),
                                estabelecimentoRequestDTO.getLogradouro(), estabelecimentoRequestDTO.getEstado(),
                                estabelecimentoRequestDTO.getCnpj(),
                                usuario);
        }

        @Override
        public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento) {
                return new EstabelecimentoResponseDTO(estabelecimento);
        }

        @Override
        public Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosParaEstabelecimentosUsuariosResponse(
                        Collection<Estabelecimento> estabelecimentos) {
                Collection<EstabelecimentoUsuarioResponseDTO> estabelecimentosMapeados = new ArrayList<>();
                for (Estabelecimento estabelecimento : estabelecimentos) {
                        estabelecimentosMapeados.add(new EstabelecimentoUsuarioResponseDTO(estabelecimento.getId(),
                                        estabelecimento.getNome(), estabelecimento.getLogradouro(),
                                        estabelecimento.getTelefone(), estabelecimento.getCnpj()));
                }
                return estabelecimentosMapeados;
        }
}
