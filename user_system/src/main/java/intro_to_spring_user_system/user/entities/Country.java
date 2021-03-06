package intro_to_spring_user_system.user.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {
    private Long id;
    private String name;
    private Set<Town> towns;

    public Country() {
        this.towns = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(unique = true, nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    @OneToMany()
    @JoinColumn(referencedColumnName = "id")
    public Set<Town> getTowns() {
        return this.towns;
    }

    public void setTowns(final Set<Town> towns) {
        this.towns = towns;
    }
}
