package lab.shiryaeva.passports.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Table(name = "passport")
@Entity
public class Passport {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonProperty("serialNumber")
    @Column(name = "serial_number")
    private Integer serialNumber;

    @JsonProperty("number")
    @Column(name = "number", nullable = false)
    private Integer number;

    @JsonProperty("issueDate")
    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @JsonProperty("expirationDate")
    @Column(name = "expiration_date")
    private Date expirationDate;

    @JsonProperty("active")
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @JsonProperty("passportType")
    @Enumerated
    @Column(name = "passport_type", nullable = false)
    private PassportType passportType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}