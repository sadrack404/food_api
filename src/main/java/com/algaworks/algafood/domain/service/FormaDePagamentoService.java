package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FormaDePagamentoService {

    public static final String MSG_FORMA_PAGAMENTO_NAO_PODE_SER_REMOVIDA
            = "Forma de pagamento não pode ser removida, pois está em uso";
    @Autowired
    FormaPagamentoRepository formaDePagamentoRepository;

    public FormaPagamento validaFormaPagamento(Long formaPagamentoId) {
        return formaDePagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Forma de pagamento não encontrada"));
    }

    public List<FormaPagamento> listar() {
        return formaDePagamentoRepository.findAll();
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaDePagamento) {
        return formaDePagamentoRepository.save(formaDePagamento);
    }

    @Transactional
    public void deletar(Long id) {
        try {
            formaDePagamentoRepository.deleteById(id);
            formaDePagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new FormaPagamentoNaoEncontradaException(
                    String.format(MSG_FORMA_PAGAMENTO_NAO_PODE_SER_REMOVIDA, e)
            );
        }
    }
}