package org.openapitools.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;

import java.util.Objects;

/**
 * PageCreateDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-07T20:09:54.805483300+03:00[Europe/Moscow]")
public class PageCreateDto {

  @JsonProperty("description")
  private String description;

  @JsonProperty("color")
  private String color;

  @JsonProperty("template_page")
  private String templatePage;

  @JsonProperty("tag_people")
  private String tagPeople;

  @JsonProperty("tag_place")
  private String tagPlace;

  @JsonProperty("animation")
  private String animation;

  public PageCreateDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", example = "some words about page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PageCreateDto color(String color) {
    this.color = color;
    return this;
  }

  /**
   * Get color
   * @return color
  */
  
  @Schema(name = "color", example = "red", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public PageCreateDto templatePage(String templatePage) {
    this.templatePage = templatePage;
    return this;
  }

  /**
   * Get templatePage
   * @return templatePage
  */
  
  @Schema(name = "template_page", example = "Horizontal image and text field below", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTemplatePage() {
    return templatePage;
  }

  public void setTemplatePage(String templatePage) {
    this.templatePage = templatePage;
  }

  public PageCreateDto tagPeople(String tagPeople) {
    this.tagPeople = tagPeople;
    return this;
  }

  /**
   * Get tagPeople
   * @return tagPeople
  */
  
  @Schema(name = "tag_people", example = "Andrew,Egor", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTagPeople() {
    return tagPeople;
  }

  public void setTagPeople(String tagPeople) {
    this.tagPeople = tagPeople;
  }

  public PageCreateDto tagPlace(String tagPlace) {
    this.tagPlace = tagPlace;
    return this;
  }

  /**
   * Get tagPlace
   * @return tagPlace
  */
  
  @Schema(name = "tag_place", example = "Germany,Berlin", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getTagPlace() {
    return tagPlace;
  }

  public void setTagPlace(String tagPlace) {
    this.tagPlace = tagPlace;
  }

  public PageCreateDto animation(String animation) {
    this.animation = animation;
    return this;
  }

  /**
   * Get animation
   * @return animation
  */
  
  @Schema(name = "animation", example = "slide", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getAnimation() {
    return animation;
  }

  public void setAnimation(String animation) {
    this.animation = animation;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PageCreateDto pageCreateDto = (PageCreateDto) o;
    return Objects.equals(this.description, pageCreateDto.description) &&
        Objects.equals(this.color, pageCreateDto.color) &&
        Objects.equals(this.templatePage, pageCreateDto.templatePage) &&
        Objects.equals(this.tagPeople, pageCreateDto.tagPeople) &&
        Objects.equals(this.tagPlace, pageCreateDto.tagPlace) &&
        Objects.equals(this.animation, pageCreateDto.animation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, color, templatePage, tagPeople, tagPlace, animation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageCreateDto {\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    color: ").append(toIndentedString(color)).append("\n");
    sb.append("    templatePage: ").append(toIndentedString(templatePage)).append("\n");
    sb.append("    tagPeople: ").append(toIndentedString(tagPeople)).append("\n");
    sb.append("    tagPlace: ").append(toIndentedString(tagPlace)).append("\n");
    sb.append("    animation: ").append(toIndentedString(animation)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

