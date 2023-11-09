INSERT INTO `magpie_order`.`product_order`(`id`, `order_id`, `product_order_id`, `product_id`, `product_name`, `sku_id`,
                                           `sku_info`, `channel_pid`, `channel_pname`, `channel_sku_id`,
                                           `channel_sku_info`, `product_price`, `platform_discount`, `store_discount`,
                                           `pay_amount`, `create_time`, `create_user`, `update_time`, `update_user`,
                                           `create_dept`, `tenant_id`, `is_deleted`, `status`, `is_after_sale`)
VALUES (PRODUCT_ORDER_TABLE_ID, ORDER_ID, 'PRODUCT_ORDER_ID', '1684049184930627585', '测试商品',
        '1684049847727128578', '', '648756163360', '上海九价HPV疫苗预约', 'CHANNEL_SKU_ID',
        '套餐名称:上海九价HPV疫苗预约预售2-4个月', 7688.00, 0.00, 0.00, 7688.00, '2023-07-28 15:00:03', NULL,
        '2023-07-28 15:00:03', NULL, NULL, '000000', 0, 1, 0);
