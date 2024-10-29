CREATE SEQUENCE IF NOT EXISTS common_seq START WITH 1001 INCREMENT BY 50;

CREATE TABLE app_user
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    first_name         VARCHAR(50),
    last_name          VARCHAR(50),
    email              VARCHAR(254)                NOT NULL,
    password           VARCHAR(60)                 NOT NULL,
    active             BOOLEAN                     NOT NULL,
    CONSTRAINT pk_app_user PRIMARY KEY (id)
);

CREATE TABLE brand
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255)                NOT NULL,
    description        VARCHAR(255),
    sort_order         INTEGER                     NOT NULL,
    CONSTRAINT pk_brand PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id      BIGINT       NOT NULL,
    status  VARCHAR(255) NOT NULL,
    user_id BIGINT       NOT NULL,
    CONSTRAINT pk_cart PRIMARY KEY (id)
);

CREATE TABLE cart_item
(
    id         BIGINT           NOT NULL,
    status     VARCHAR(255)     NOT NULL,
    cart_id    BIGINT           NOT NULL,
    product_id BIGINT           NOT NULL,
    quantity   INTEGER          NOT NULL,
    amount     DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_cart_item PRIMARY KEY (id)
);

CREATE TABLE category
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255)                NOT NULL,
    description        VARCHAR(255),
    sort_order         INTEGER                     NOT NULL,
    CONSTRAINT pk_category PRIMARY KEY (id)
);

CREATE TABLE "order"
(
    id           BIGINT           NOT NULL,
    status       VARCHAR(255)     NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    user_id      BIGINT           NOT NULL,
    cart_id      BIGINT           NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);

CREATE TABLE order_item
(
    id         BIGINT           NOT NULL,
    order_id   BIGINT           NOT NULL,
    product_id BIGINT           NOT NULL,
    quantity   INTEGER          NOT NULL,
    amount     DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_order_item PRIMARY KEY (id)
);

CREATE TABLE product
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    name               VARCHAR(255)                NOT NULL,
    description        VARCHAR(255),
    image_url          VARCHAR(255)                NOT NULL,
    original_price     DOUBLE PRECISION            NOT NULL,
    selling_price      DOUBLE PRECISION            NOT NULL,
    sort_order         INTEGER                     NOT NULL,
    brand_id           BIGINT                      NOT NULL,
    category_id        BIGINT                      NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE role
(
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (name)
);

CREATE TABLE user_role_mapping
(
    role_name VARCHAR(50) NOT NULL,
    user_id   BIGINT      NOT NULL,
    CONSTRAINT pk_user_role_mapping PRIMARY KEY (role_name, user_id)
);

CREATE TABLE wishlist
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    user_id            BIGINT                      NOT NULL,
    CONSTRAINT pk_wishlist PRIMARY KEY (id)
);

CREATE TABLE wishlist_item
(
    id                 BIGINT                      NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE,
    status             VARCHAR(255)                NOT NULL,
    wishlist_id        BIGINT                      NOT NULL,
    product_id         BIGINT                      NOT NULL,
    CONSTRAINT pk_wishlist_item PRIMARY KEY (id)
);

ALTER TABLE app_user
    ADD CONSTRAINT uc_app_user_email UNIQUE (email);

ALTER TABLE wishlist
    ADD CONSTRAINT uc_wishlist_user UNIQUE (user_id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE cart_item
    ADD CONSTRAINT FK_CART_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE cart
    ADD CONSTRAINT FK_CART_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_ORDER FOREIGN KEY (order_id) REFERENCES "order" (id);

ALTER TABLE order_item
    ADD CONSTRAINT FK_ORDER_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_CART FOREIGN KEY (cart_id) REFERENCES cart (id);

ALTER TABLE "order"
    ADD CONSTRAINT FK_ORDER_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_BRAND FOREIGN KEY (brand_id) REFERENCES brand (id);

ALTER TABLE product
    ADD CONSTRAINT FK_PRODUCT_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES category (id);

ALTER TABLE wishlist_item
    ADD CONSTRAINT FK_WISHLIST_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE wishlist_item
    ADD CONSTRAINT FK_WISHLIST_ITEM_ON_WISHLIST FOREIGN KEY (wishlist_id) REFERENCES wishlist (id);

ALTER TABLE wishlist
    ADD CONSTRAINT FK_WISHLIST_ON_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE user_role_mapping
    ADD CONSTRAINT fk_userolmap_on_role FOREIGN KEY (role_name) REFERENCES role (name);

ALTER TABLE user_role_mapping
    ADD CONSTRAINT fk_userolmap_on_user FOREIGN KEY (user_id) REFERENCES app_user (id);