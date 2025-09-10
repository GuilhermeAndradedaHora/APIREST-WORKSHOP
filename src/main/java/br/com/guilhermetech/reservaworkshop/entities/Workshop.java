package br.com.guilhermetech.reservaworkshop.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "tb_workshop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Instant moment;
    private String locale;
    private Integer maxCapacity;

    @OneToMany(mappedBy = "workshop", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "registrations-workshop")
    private List<Registration> registrations;

    public Workshop(Long id) {
        this.id = id;
    }

    public Workshop(String title, String description, Instant moment, String locale, Integer maxCapacity) {
        this.title = title;
        this.description = description;
        this.moment = moment;
        this.locale = locale;
        this.maxCapacity = maxCapacity;
    }

    public List<Registration> getRegistrations() {
        if (this.registrations == null) {
            this.registrations = new ArrayList<>();
        }
        return registrations;
    }

    public void addRegistration(Registration registration) {
        registration.setWorkshop(this);
        this.registrations.add(registration);
    }
}
