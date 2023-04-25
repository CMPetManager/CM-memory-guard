package com.catchthemoment.entity;

import com.catchthemoment.entity.embedded.Cover;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

    @Column(name = "album_description")
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String albumDescription;

    @Column(name = "color")
    private String color;

    @Column(name = "template_page")
    private String templatePage;

    @Column(name = "tag_people")
    private String tagPeople;

    @Column(name = "tag_place")
    private String tagPlace;

    @Column(name = "animation")
    private String animation;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "description", column = @Column(name = "cover_description")),
    })
    private Cover cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "album")
    private List<Image> images;

}
