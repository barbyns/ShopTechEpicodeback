package ShopTech.ShopTechEpicode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
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
    private Set<String> ruoli = new java.util.HashSet<>(); // ✅ inizializzazione sicura

    // ✅ Metodo di utilità per aggiungere un ruolo singolo
    public void setRuolo(String ruolo) {
        this.ruoli = Set.of(ruolo);
    }

    public void addRuolo(String ruolo) {
        this.ruoli.add(ruolo);
    }
}
