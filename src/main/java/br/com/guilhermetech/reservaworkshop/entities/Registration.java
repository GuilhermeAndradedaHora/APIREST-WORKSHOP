package br.com.guilhermetech.reservaworkshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tb_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant dateRegistration;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name = "workshop_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_registration_workshop"))
    @JsonBackReference(value = "registrations-workshop")
    private Workshop workshop;

    @Transient
    private Long workshopId;
    @Transient
    private String workshopName;

    public Registration(Long userId) {
        this.dateRegistration = Instant.now();
        this.userId = userId;
    }

    public Workshop getWorkshop() {
        return null;
    }

    public Long getWorkshopId() {
        return this.workshop.getId();
    }

    public String getWorkshopName() {
        return this.workshop.getTitle();
    }
}
