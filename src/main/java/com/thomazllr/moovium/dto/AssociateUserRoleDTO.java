package com.thomazllr.moovium.dto;

import jakarta.validation.constraints.NotNull;

public record AssociateUserRoleDTO(@NotNull(message = "User ID is required") Long userId, @NotNull(message = "Role ID is required") Long roleId) {
}
