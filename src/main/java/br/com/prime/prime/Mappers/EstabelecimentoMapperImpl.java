package br.com.prime.prime.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.prime.prime.dto.EstabelecimentoPutDTO;
import br.com.prime.prime.dto.EstabelecimentoRequestDTO;
import br.com.prime.prime.dto.EstabelecimentoResponseDTO;
import br.com.prime.prime.models.Estabelecimento;
import br.com.prime.prime.models.Usuario;
import br.com.prime.prime.repository.EstabelecimentoRepository;
import br.com.prime.prime.repository.UsuarioRepository;

@Component
public class EstabelecimentoMapperImpl implements EstabelecimentoMapper {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private EstabelecimentoRepository estabelecimentoRepository;

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
        public Estabelecimento estabeleicmentoPutParaEstabelecimento(EstabelecimentoPutDTO estabelecimentoPutDTO) {
                Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoPutDTO.getId())
                                .get();
                estabelecimento.setNome(estabelecimentoPutDTO.getNome());
                estabelecimento.setTelefone(estabelecimentoPutDTO.getTelefone());
                estabelecimento.setHorarioAtendimento(estabelecimentoPutDTO.getHorarioAtendimento());
                estabelecimento.setNumero(estabelecimentoPutDTO.getNumero());
                estabelecimento.setCidade(estabelecimentoPutDTO.getCidade());
                estabelecimento.setLogradouro(estabelecimentoPutDTO.getLogradouro());
                estabelecimento.setEstado(estabelecimentoPutDTO.getEstado());
                estabelecimento.setCnpj(estabelecimentoPutDTO.getCnpj());

                return estabelecimento;
        }

        @Override
        public EstabelecimentoResponseDTO estabelecimentoParaEstabelecimentoResponse(Estabelecimento estabelecimento) {
                return new EstabelecimentoResponseDTO(estabelecimento.getId(), estabelecimento.getNome(),
                                estabelecimento.getTelefone(), estabelecimento.getHorarioAtendimento(),
                                estabelecimento.getNumero(), estabelecimento.getCidade(),
                                estabelecimento.getLogradouro(), estabelecimento.getEstado(),
                                estabelecimento.getCnpj());
        }

        @Override
        public List<EstabelecimentoResponseDTO> estabelecimentosParaEstabelecimentoResponseDTOs(
                        List<Estabelecimento> estabelecimentos) {
                List<EstabelecimentoResponseDTO> estabelecimentoResponseDTOs = new ArrayList<>();
                for (Estabelecimento estabelecimento : estabelecimentos) {
                        estabelecimentoResponseDTOs.add(estabelecimentoParaEstabelecimentoResponse(estabelecimento));
                }
                return estabelecimentoResponseDTOs;
        }
}
