package com.catchthemoment.entity;

import com.catchthemoment.entity.embedded.Cover;
import liquibase.repackaged.org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "album", indexes = @Index(name = "album_usr_ind", columnList = "id,user_id"))
@NamedEntityGraph(name = "album-graph", attributeNodes = {
        @NamedAttributeNode("user")
})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "album_description")
    private String albumDescription;

    @Column(name = "album_name", nullable = false, unique = true)
    private String albumName;

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

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "cover_description")),
    })
    private Cover cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "album")
    private List<Image> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("cover", cover)
                .append("user", user)
                .toString();
    }
}
