package com.example.MyBookShopApp.data;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "balance_transaction")
public class BalanceTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inv_id")
    private Long invId;

    @Column(name = "user_id")
    private Integer userId;

    private Date time;

    private Integer value;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type_status")
    private TypeStatus typeStatus;

    @Column(columnDefinition = "TEXT")
    private String description;

    @AllArgsConstructor
    @Getter
    public enum TypeStatus {

        PROCESSING("В обработке"),
        OK("Удачно"),
        FAIL("Ошибка");

        public final String TypeName;

        @Override
        public String toString() {
            return TypeName;
        }
    }


}
