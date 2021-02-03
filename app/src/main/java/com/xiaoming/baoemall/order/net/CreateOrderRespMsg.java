package com.xiaoming.baoemall.order.net;

import com.xiaoming.baoemall.mine.scene.BaseRespMsg;
import com.xiaoming.baoemall.order.bean.OrderRespMsg;

public class CreateOrderRespMsg extends BaseRespMsg {
    private OrderRespMsg data;

    public OrderRespMsg getData() {
        return data;
    }

    public void setData(OrderRespMsg data) {
        this.data = data;
    }
}
