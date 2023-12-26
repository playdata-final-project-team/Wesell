package com.wesell.dealservice.domain.repository.read;

import com.wesell.dealservice.domain.entity.DealPost;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DealPostReadRepository extends JpaRepository<DealPost, Long> {
    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.uuid =:uuid " +
            "AND dp.isDeleted = false " +
            "ORDER BY dp.createdAt desc")
    Page<DealPost> searchMyList(@Param("uuid") String uuid, Pageable pageable);

    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.id = :postId " +
            "AND dp.saleStatus = 'IN_PROGRESS' " +
            "AND dp.isDeleted = false")
    DealPost searchDealPost(@Param("postId") Long postId);

    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.saleStatus = 'IN_PROGRESS' " +
            "AND dp.isDeleted = false " +
            "ORDER BY dp.createdAt desc")
    Page<DealPost> searchDealPostList(Pageable pageable);

    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.saleStatus = 'IN_PROGRESS' " +
            "AND dp.isDeleted = false " +
            "AND dp.category.id = :categoryId " +
            "ORDER BY dp.createdAt desc")
    Page<DealPost> searchByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.saleStatus = 'IN_PROGRESS' " +
            "AND dp.isDeleted = false " +
            "AND dp.title = :title " +
            "ORDER BY dp.createdAt desc")
    Page<DealPost> searchByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT dp FROM DealPost dp " +
            "WHERE dp.saleStatus = 'IN_PROGRESS' " +
            "AND dp.isDeleted = false " +
            "AND dp.category.id = :categoryId " +
            "AND dp.title = :title " +
            "ORDER BY dp.createdAt desc")
    Page<DealPost> searchByCategoryAndTitle(@Param("categoryId") Long categoryId, @Param("title") String title, Pageable pageable);
}