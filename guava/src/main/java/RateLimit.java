import com.google.common.util.concurrent.RateLimiter;

/**
 * @author FD
 * @version v6.2.0
 * @date 2017/12/18
 */
public class RateLimit {
    private static RateLimiter one = RateLimiter.create(2);//每秒2个
    private static RateLimiter two = RateLimiter.create(2);//每秒2个

    public static void acquire(RateLimiter r, int num){
        double time = r.acquire(num);
        System.out.println("wait time="+time);
    }

    public static void main(String[] args) throws InterruptedException {
        acquire(one,1);
        acquire(one,1);
        acquire(one,1);
        System.out.println("-----");
        acquire(two,10);
        acquire(two,1);

    }
}
