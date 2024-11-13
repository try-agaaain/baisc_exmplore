import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

public class TestCompletableFuture {

    public static void processOrder(Long orderId, Long userId) {
        long startTime = System.currentTimeMillis();

        // 阶段1: 订单创建
        CompletableFuture<String> orderCreationFuture = createOrder(orderId)
            .thenCompose(orderDetails -> {
                CompletableFuture<String> userInfoFuture = getUserInfo(userId);
                CompletableFuture<String> inventoryCheckFuture = checkInventory(orderId);
                
                return CompletableFuture.allOf(userInfoFuture, inventoryCheckFuture)
                    .thenApply(v -> {
                        try {
                            String userInfo = userInfoFuture.get();
                            String inventoryStatus = inventoryCheckFuture.get();
                            return "Order created: " + orderDetails + ", User: " + userInfo + ", Inventory: " + inventoryStatus;
                        } catch (InterruptedException | ExecutionException e) {
                            throw new CompletionException(e);
                        }
                    });
            });

        // 阶段2: 支付处理
        CompletableFuture<String> paymentFuture = orderCreationFuture
            .thenCompose(orderInfo -> {
                CompletableFuture<String> paymentProcessFuture = processPayment(orderId);
                CompletableFuture<String> fraudCheckFuture = performFraudCheck(userId);
                
                return CompletableFuture.allOf(paymentProcessFuture, fraudCheckFuture)
                    .thenApply(v -> {
                        try {
                            String paymentStatus = paymentProcessFuture.get();
                            String fraudStatus = fraudCheckFuture.get();
                            return orderInfo + ", Payment: " + paymentStatus + ", Fraud Check: " + fraudStatus;
                        } catch (InterruptedException | ExecutionException e) {
                            throw new CompletionException(e);
                        }
                    });
            });

        // 阶段3: 物流安排
        CompletableFuture<String> logisticsFuture = paymentFuture
            .thenCompose(paymentInfo -> {
                CompletableFuture<String> warehouseAllocationFuture = allocateWarehouse(orderId);
                CompletableFuture<String> shippingArrangementFuture = arrangeShipping(orderId);
                
                return CompletableFuture.allOf(warehouseAllocationFuture, shippingArrangementFuture)
                    .thenApply(v -> {
                        try {
                            String warehouseInfo = warehouseAllocationFuture.get();
                            String shippingInfo = shippingArrangementFuture.get();
                            return paymentInfo + ", Warehouse: " + warehouseInfo + ", Shipping: " + shippingInfo;
                        } catch (InterruptedException | ExecutionException e) {
                            throw new CompletionException(e);
                        }
                    });
            });

        // 最终处理
        logisticsFuture.thenAccept(finalResult -> {
            long endTime = System.currentTimeMillis();
            System.out.println("Order processing completed:");
            System.out.println(finalResult);
            System.out.println("Total time taken: " + (endTime - startTime) + "ms");
        }).join(); // 等待整个过程完成
    }

    // 模拟方法，实际应用中这些方法会调用相应的服务
    private static CompletableFuture<String> createOrder(Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Order " + orderId;
        });
    }

    private static CompletableFuture<String> getUserInfo(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "User " + userId;
        });
    }

    private static CompletableFuture<String> checkInventory(Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(400);
            return "Inventory checked for order " + orderId;
        });
    }

    private static CompletableFuture<String> processPayment(Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(600);
            return "Payment processed for order " + orderId;
        });
    }

    private static CompletableFuture<String> performFraudCheck(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Fraud check passed for user " + userId;
        });
    }

    private static CompletableFuture<String> allocateWarehouse(Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(400);
            return "Warehouse allocated for order " + orderId;
        });
    }

    private static CompletableFuture<String> arrangeShipping(Long orderId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Shipping arranged for order " + orderId;
        });
    }

    private static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        processOrder(1234L, 5678L);
    }
}