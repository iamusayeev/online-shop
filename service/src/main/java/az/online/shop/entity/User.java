package az.online.shop.entity;


import static javax.persistence.EnumType.STRING;

import az.online.shop.model.Role;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"bucket", "orders"}, callSuper = false)
@ToString(exclude = {"bucket", "orders"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User extends BaseEntity<Integer> {

    private String username;
    private String email;
    private String password;
    private String image;

    @Embedded
    private PersonalInfo personalInfo;

    @Enumerated(STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private Bucket bucket;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders;
}