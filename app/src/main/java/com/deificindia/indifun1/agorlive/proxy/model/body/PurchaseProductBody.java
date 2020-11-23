package com.deificindia.indifun1.agorlive.proxy.model.body;

public class PurchaseProductBody {
    public String productId;
    public int count;

    public PurchaseProductBody(String productId, int count) {
        this.productId = productId;
        this.count = count;
    }
}
