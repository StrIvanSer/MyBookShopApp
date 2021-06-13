package com.example.MyBookShopApp.data.book;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "recently_viewed")
public class RecentlyViewedBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_recently_viewed_user"))
    private BookstoreUser user;

    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_recently_viewed_book"))
    private Book book;

    @Column(name="last_veiw_date_time")
    private Timestamp time;

}
