CREATE TABLE IF NOT EXISTS tb_users_roles (
    user_uuid UUID REFERENCES tb_users(uuid),
    role_uuid UUID REFERENCES tb_roles(uuid),
    PRIMARY KEY (user_uuid, role_uuid)
);
