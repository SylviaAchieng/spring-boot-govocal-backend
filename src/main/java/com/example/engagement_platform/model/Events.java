package com.example.engagement_platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long eventId;

    @NotNull(message = "event title is mandatory")
    @NotBlank(message = "title field shld not be empty")
    private String title;

    @NotNull(message = "event description is mandatory")
    @NotBlank(message = "Description field shld not be empty")
    private String Description;

    @NotNull(message = "event creation date is mandatory")
    @NotBlank(message = "createdAt field shld not be empty")
    private String createdAt;

    @NotNull(message = "event date is mandatory")
    @NotBlank(message = "eventDate field shld not be empty")
    private String eventDate;

    @NotNull(message = "event location is mandatory")
    @NotBlank(message = "location field shld not be empty")
    private String location;

}
