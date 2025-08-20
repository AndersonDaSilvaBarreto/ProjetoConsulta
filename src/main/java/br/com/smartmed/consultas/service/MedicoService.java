package br.com.smartmed.consultas.service;


import br.com.smartmed.consultas.exception.*;
import br.com.smartmed.consultas.model.MedicoModel;
import br.com.smartmed.consultas.repository.EspecialidadeRepository;
import br.com.smartmed.consultas.repository.MedicoRepository;
import br.com.smartmed.consultas.rest.dto.MedicoCadastroRequest;
import br.com.smartmed.consultas.rest.dto.MedicoDTO;
import br.com.smartmed.consultas.rest.dto.MedicoDeleteRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EspecialidadeRepository especialidadeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Transactional(readOnly = true)
    public MedicoDTO obterPorId(int id) {
        MedicoModel medico = medicoRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Médico com id " + id + "não encontrado!"));
        return modelMapper.map(medico, MedicoDTO.class);
    }

    @Transactional(readOnly = true)
    public List<MedicoDTO> obterTodos() {
        List<MedicoModel> medicos = medicoRepository.findAll();
        return medicos.stream().map(medico ->modelMapper.map(medico, MedicoDTO.class)).collect(Collectors.toList());

    }
    @Transactional
    public MedicoDTO salvar(MedicoCadastroRequest novoMedico) {

        try {
            if(medicoRepository.existsByCrm(novoMedico.getCrm())) {
                throw new ConstraintException("Já existe um médico com esse Crm" + novoMedico.getCrm() + "na base de dados");
            }
            MedicoModel medico = new MedicoModel();
            medico.setNome(novoMedico.getNome());
            medico.setCrm(novoMedico.getCrm());
            medico.setTelefone(novoMedico.getTelefone());
            medico.setEmail(novoMedico.getEmail());
            medico.setValorConsultaReferencia(novoMedico.getValorConsultaReferencia());
            medico.setAtivo(novoMedico.isAtivo());
            medico.setEspecialidade(especialidadeRepository.findById(novoMedico.getEspecialidadeID())
                    .orElseThrow(() -> new BusinessRuleException("Especialidade especificada não encontrada!")));

            MedicoModel medicoSalvo = medicoRepository.save(medico);

            //Resposta

            return modelMapper.map(medicoSalvo,MedicoDTO.class);



        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + " !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro de restrição de integridade ao salvar o médico " + novoMedico.getNome() + ".");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível salvar o médico " + novoMedico.getNome() + ". Falha na conexão com o banco de dados!");
        }
    }

    @Transactional
    public MedicoDTO atualizar(MedicoCadastroRequest medicoExistente) {

       try {
           if(!medicoRepository.existsByCrm(medicoExistente.getCrm())) {
               throw new ConstraintException("O médico com esse Crm " + medicoExistente.getCrm() + " não existe na base dados!");
           }
           MedicoModel medicoAtualizado = medicoRepository.findByCrm(medicoExistente.getCrm()).orElseThrow(() -> new ConstraintException("O médico com esse Crm não existe no banco de dados!"));
           medicoAtualizado.setNome(medicoExistente.getNome());
           medicoAtualizado.setCrm(medicoExistente.getCrm());
           medicoAtualizado.setTelefone(medicoExistente.getTelefone());
           medicoAtualizado.setEmail(medicoExistente.getEmail());
           medicoAtualizado.setValorConsultaReferencia(medicoExistente.getValorConsultaReferencia());
           medicoAtualizado.setAtivo(medicoExistente.isAtivo());
           medicoAtualizado.setEspecialidade(especialidadeRepository.findById(medicoExistente.getEspecialidadeID())
                   .orElseThrow(() -> new BusinessRuleException("Especialidade especificada não encontrada!")));

           return modelMapper.map(medicoRepository.save(medicoAtualizado), MedicoDTO.class);
       } catch (DataIntegrityException e) {
           throw new DataIntegrityException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + " !");
       } catch (ConstraintException e) {
           // Relança a mensagem original ou adiciona contexto
           if (e.getMessage() == null || e.getMessage().isBlank()) {
               throw new ConstraintException("Erro ao atualizar o médico " + medicoExistente.getNome() + ": Restrição de integridade de dados.");
           }
           throw e;
       } catch (BusinessRuleException e) {
           throw new BusinessRuleException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Violação de regra de negócio!");
       } catch (SQLException e) {
           throw new SQLException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Falha na conexão com o banco de dados!");
       } catch (ObjectNotFoundException e) {
           throw new ObjectNotFoundException("Erro! Não foi possível atualizar o médico " + medicoExistente.getNome() + ". Não encontrado no banco de dados!");
       }
    }

    @Transactional
    public void deletar(MedicoDeleteRequest medicoExistente) {
        try {
            if(!medicoRepository.existsByCrm(medicoExistente.getCrm())) {
                throw new ConstraintException("Médico inexistente na base de dados!");
            }
            MedicoModel medicoParaDeletar = medicoRepository.findByCrm(medicoExistente.getCrm()).orElseThrow(() -> new ConstraintException("Médico inexistente no banco de dados!"));
            medicoRepository.delete(medicoParaDeletar);
        }  catch (DataIntegrityException e) {
            throw new DataIntegrityException("Erro! Não foi possível deletar o medico !");
        } catch (ConstraintException e) {
            // Relança a mensagem original ou adiciona contexto
            if (e.getMessage() == null || e.getMessage().isBlank()) {
                throw new ConstraintException("Erro ao deletar o medico com o Crm " + medicoExistente.getCrm() + " : Restrição de integridade de dados.");
            }
            throw e;
        } catch (BusinessRuleException e) {
            throw new BusinessRuleException("Erro! Não foi possível deletar o medico " + medicoExistente.getCrm() + ". Violação de regra de negócio!");
        } catch (SQLException e) {
            throw new SQLException("Erro! Não foi possível deletar medico com o Crm " + medicoExistente.getCrm() + ". Falha na conexão com o banco de dados!");
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Erro! Não foi possível deletar o paciente" + medicoExistente.getCrm() + ". Não encontrado no banco de dados!");
        }
    }
}
