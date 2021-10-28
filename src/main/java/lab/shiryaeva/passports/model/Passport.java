package lab.shiryaeva.passports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "passport", schema = "public")
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("serialNumber")
    @Column(name = "serial_number")
    private Integer serialNumber;

    @JsonProperty("number")
    @Column(name = "number", nullable = false, unique = true)
    private Integer number;

    @JsonProperty("issueDate")
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @JsonProperty("expirationDate")
    @Temporal(TemporalType.DATE)
    @Column(name = "expiration_date", nullable = false)
    private java.util.Date expirationDate;

    @JsonProperty("active")
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @JsonProperty("passportType")
    @Enumerated
    @Column(name = "passport_type", nullable = false)
    private PassportType passportType;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String toString() {
        return id + " " + serialNumber + " " + number + " " + person.getLastName();
    }
}