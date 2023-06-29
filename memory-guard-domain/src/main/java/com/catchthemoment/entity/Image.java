package com.catchthemoment.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NamedEntityGraph(name = "image-graph", attributeNodes = {
        @NamedAttributeNode("album")
})
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @Column(name = "link")
    private String link;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "id")
    private Album album;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
