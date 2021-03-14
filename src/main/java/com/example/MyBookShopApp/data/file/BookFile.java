package com.example.MyBookShopApp.data.file;

import com.example.MyBookShopApp.data.Book.Book;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String hash;

    @Column(name = "type_id")
    private Integer typeId;

    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey =
    @ForeignKey(name = "fk_book_file__book"))
    private Book book;

    public String getBookFileExtensionString() {
        return BookFileType.getExtensionStringByTypeId(typeId);
    }

}
