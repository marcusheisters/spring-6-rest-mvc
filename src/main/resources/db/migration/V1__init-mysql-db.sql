
    drop table if exists beer;

    drop table if exists customer;

    create table beer (
                          id varchar(36) not null,
                          beer_name varchar(100),
                          beer_style smallint not null,
                          created_on datetime(6),
                          price decimal(38,2),
                          quantity_on_hand integer,
                          upc varchar(255),
                          updated_on datetime(6),
                          version integer,
                          primary key (id)
    ) engine=InnoDB;

    create table customer (
                              id varchar(36) not null,
                              created_on datetime(6),
                              customer_name varchar(255),
                              updated_on datetime(6),
                              version integer,
                              primary key (id)
    ) engine=InnoDB;
