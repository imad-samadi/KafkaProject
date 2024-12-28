package com.example.Consumer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wikimedia_change")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WikimediaChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String wiki;
    private String title;
    @Column(name = "username")
    private String user;
    private String comment;
    private boolean bot;
    private String notify_url ;
}
