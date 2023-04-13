package com.catchthemoment.entity.embedded;

import com.catchthemoment.entity.Image;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Cover {

    private String description;

    @OneToOne(mappedBy = "cover")
    private Image coverImage;

}
