package com.catchthemoment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "page")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

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

    @ManyToOne
    @JoinColumn(name = "album_id",referencedColumnName = "id")
    private Album album;

    @OneToMany(mappedBy = "page")
    private List<Image> images;

}
