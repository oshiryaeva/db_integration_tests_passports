package lab.shiryaeva.passports.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.springframework.data.jdbc.repository.query.Modifying;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Table(name = "view_active_passports")
@Entity
@Getter
public class Person2Passport implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "serial_number")
    private int serialNumber;
    @Column(name = "number")
    private int number;
    @Column(name = "issue_date")
    private Date issueDate;
    @Column(name = "expiration_date")
    private Date expirationDate;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private Date birthDate;

}