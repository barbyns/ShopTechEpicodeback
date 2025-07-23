package model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "ruolo")
    private Set<String> ruoli;

    // Metodo di utilità per impostare un ruolo singolo (es: "USER")
    public void setRuolo(String ruolo) {
        this.ruoli = Set.of(ruolo);
    }


}
