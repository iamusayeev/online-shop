package az.online.shop.entity;

import static javax.persistence.CascadeType.PERSIST;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "products", callSuper = false)
@ToString(exclude = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bucket extends BaseEntity<Integer> {

    @OneToOne(fetch = FetchType.LAZY, cascade = PERSIST)
    private User user;

    @ManyToMany
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name = "bucket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}