package com.thomazllr.moovium.service;

import com.thomazllr.moovium.exception.BusinessException;
import com.thomazllr.moovium.exception.NotFoundException;
import com.thomazllr.moovium.model.Role;
import com.thomazllr.moovium.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Role findByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    public Role findByIdOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Role not found with id: %s".formatted(id)));
    }

    public Role findByIdOrBadRequest(Long roleId) {
        try {
            return findByIdOrThrow(roleId);
        } catch (NotFoundException e) {
            throw new BusinessException("Invalid Role Id: '%d'".formatted(roleId));
        }
    }
}
