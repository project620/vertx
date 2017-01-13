/**
*Jan 12, 2017, jim.huang, create
*/
package vertx.vertx;

import com.vertx3.cache.CacheManager;
import com.vertx3.entity.User;
import com.vertx3.start.BeanLoader;
import com.vertx3.start.Main;

import io.vertx.core.Vertx;
import redis.clients.jedis.Jedis;

/**
 * @author jim.huang
 */
public class JedisTest {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final Jedis jedis = new Jedis("localhost", 6379);
        System.out.println("connection success");
        System.out.println(jedis.ping());
        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Main(), rs -> {
            final CacheManager manager = BeanLoader.getBean("cacheManager");
            final User user1 = new User();
            user1.setId("12");
            manager.set("as", user1);
            final User user = manager.get("as", User.class);
            System.out.println(user.getId());
        });
    }

}
