package com.thomazllr.moovium.dto;

import jakarta.validation.constraints.NotNull;

public record DissociateUserRoleDTO(@NotNull Long userId, @NotNull Long roleId) {
}
