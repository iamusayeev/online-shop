package az.online.shop.entity;

import static javax.persistence.CascadeType.REMOVE;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"buckets", "categories"}, callSuper = false)
@ToString(exclude = {"buckets", "categories"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product extends BaseEntity<Integer> {

    private String title;
    private BigDecimal price;
    private String image;

    @ManyToMany(cascade = REMOVE)
    @JoinTable(name = "buckets_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "bucket_id"))
    private List<Bucket> buckets;

    @OneToMany(mappedBy = "product")
    private List<ProductsCategories> categories;
}