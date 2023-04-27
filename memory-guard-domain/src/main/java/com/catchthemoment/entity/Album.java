package com.catchthemoment.entity;

import com.catchthemoment.entity.embedded.Cover;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album", indexes = @Index(name = "album_usr_ind",columnList = "id,user_id"))
@NamedEntityGraph(name = "album-graph", attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("cover"),
        @NamedAttributeNode("user")
})
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

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
