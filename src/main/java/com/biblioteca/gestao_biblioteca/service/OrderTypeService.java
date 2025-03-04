package com.biblioteca.gestao_biblioteca.service;

import com.biblioteca.gestao_biblioteca.dtos.request.OrderTypeRequest;
import com.biblioteca.gestao_biblioteca.dtos.response.OrderTypeResponseDTO;
import com.biblioteca.gestao_biblioteca.utils.GeneratorCode;
import com.biblioteca.gestao_biblioteca.models.OrderType;
import com.biblioteca.gestao_biblioteca.repository.OrderTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderTypeService {

    @Autowired
    private OrderTypeRepository repository;

    public OrderType create(OrderType orderType) {
        return repository.save(orderType);
    }

    public void registrarTipoPedido(OrderTypeRequest dto) {
        try {
            OrderType orderType;

            boolean existsByDesignation = repository.existsByDesignation(dto.designation());
            if (existsByDesignation){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Tipo de pedido com a designação " + dto.designation() + " já existe");
            }

            orderType = new OrderType();
            orderType.setCode(GeneratorCode.generateCode());
            orderType.setDesignation(dto.designation());
            orderType.setOrderType(dto.orderTypes());

            create(orderType);
        } catch (ResponseStatusException e) {
            throw e;
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar tipo de pedido", e);
        }
    }

    public List<OrderTypeResponseDTO> listar(){
        List<OrderType> orderTypes = repository.findAll();
        List<OrderTypeResponseDTO> dto = new ArrayList<>();

        for (OrderType orderType : orderTypes){
            OrderTypeResponseDTO orderTypeResponseDTO = new OrderTypeResponseDTO(
                    orderType.getId(),
                    orderType.getCode(),
                    orderType.getDesignation(),
                    orderType.getOrderType()
            );
            dto.add(orderTypeResponseDTO);
        }
        return dto;
    }
}
