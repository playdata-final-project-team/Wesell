package com.wesell.dealservice.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDealPost is a Querydsl query type for DealPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDealPost extends EntityPathBase<DealPost> {

    private static final long serialVersionUID = 711078899L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDealPost dealPost = new QDealPost("dealPost");

    public final QCategory category;

    public final DatePath<java.time.LocalDate> createdAt = createDate("createdAt", java.time.LocalDate.class);

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath link = createString("link");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final EnumPath<com.wesell.dealservice.domain.SaleStatus> status = createEnum("status", com.wesell.dealservice.domain.SaleStatus.class);

    public final StringPath title = createString("title");

    public final StringPath uuid = createString("uuid");

    public QDealPost(String variable) {
        this(DealPost.class, forVariable(variable), INITS);
    }

    public QDealPost(Path<? extends DealPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDealPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDealPost(PathMetadata metadata, PathInits inits) {
        this(DealPost.class, metadata, inits);
    }

    public QDealPost(Class<? extends DealPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

