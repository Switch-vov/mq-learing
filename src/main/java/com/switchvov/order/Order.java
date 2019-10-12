package com.switchvov.order;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author switch
 * @since 2019/9/27
 */
@NoArgsConstructor
@Data
public class Order {
    public static final String ORDER_ID_STR = "orderId";

    private String id;
    private String something;
}
