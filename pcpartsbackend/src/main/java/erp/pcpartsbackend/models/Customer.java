package erp.pcpartsbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
@PrimaryKeyJoinColumn(name = "customerId")
public class Customer extends User implements Serializable {
    private String firstName;
    private String lastName;
    private String adress;
    private String zipCode;
}
