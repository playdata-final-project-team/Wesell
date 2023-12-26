package com.wesell.dealservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter
@Table(name = "image")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "i_url")
    private String imageUrl;

    @Builder
    public Image(Long postId, String imageUrl) {
        this.postId = postId;
        this.imageUrl = imageUrl;
    }

//    public void foreignDealPost(DealPost foreignPost) {
//        this.post = foreignPost;
//        post.getId()
//    }
}
