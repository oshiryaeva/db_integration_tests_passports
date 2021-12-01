package lab.shiryaeva.passports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Table(name = "person")
@Entity
public class Person {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonProperty("birthDate")
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @JsonIgnore
    @OneToMany()
    @JoinColumn(name = "person_id")
    private List<Passport> passports;
}