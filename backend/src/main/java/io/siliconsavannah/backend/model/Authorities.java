package io.siliconsavannah.backend.model;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.metamodel.model.domain.internal.SingularAttributeImpl;
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authority;
}