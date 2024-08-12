package com.wanted.preonboading.domain.user;

public record UserInfo(
        Long id,
        String username
) {
    public static UserInfo of(User user) {
        return new UserInfo(user.getId(), user.getUsername());
    }
}
