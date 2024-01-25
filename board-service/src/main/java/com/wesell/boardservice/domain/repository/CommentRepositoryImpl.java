package com.wesell.boardservice.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wesell.boardservice.domain.entity.Comment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.wesell.boardservice.domain.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CustomCommentRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<Comment>> findCommentByPostId(Long postId) {
        List<Comment> comments = queryFactory.selectFrom(comment)
                .leftJoin(comment.parent)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(
                        comment.parent.id.asc().nullsFirst(),
                        comment.createdAt.asc()
                ).fetch();

        return Optional.ofNullable(comments);
    }
}
