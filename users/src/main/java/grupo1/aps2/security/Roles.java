package grupo1.aps2.security;

import lombok.Getter;

@Getter
public enum Roles {
    GUEST(0, "Gguest"),
    ALUNO(1, "Aluno"),
    PROFESSOR(2, "Professor"),
    ADMIN(5, "Admin");

    private final String role;
    private final Integer id;

    Roles(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public static String fromId(Integer idRole) {
        for (Roles r : Roles.values()) {
            if (r.getId().equals(idRole)) {
                return r.getRole();
            }
        }
        return null;
    }

    public static String contains(String role) {
        for (Roles r : Roles.values()) {
            if (r.getRole().equals(role)) {
                return r.getRole();
            }
        }
        return null;
    }
}
