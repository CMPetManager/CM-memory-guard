package com.catchthemoment.entity.embedded;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Cover {

    private String description;

}
