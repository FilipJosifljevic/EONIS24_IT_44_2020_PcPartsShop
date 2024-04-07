package erp.pcpartsbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="providers")
@PrimaryKeyJoinColumn(name = "providerId")
public class Provider extends User implements Serializable {
    private String providerName;
    private String cityName;
    private int contactNumber;
}
