package cc.liqingsong.api;

import cc.liqingsong.common.entity.Result;
import cc.liqingsong.common.enums.ResultCode;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.stream.DoubleStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testEnum(){
        System.out.println(new Result(ResultCode.SUCCESS));

    }

    @Test
    public void testLogger() {
        logger.trace("这是 trace ... ");
        logger.debug("这是 debug ... ");
        logger.info("这是 info ... ");
        logger.warn("这是 warn ... ");
        logger.error("这是 error ...");
    }

    @Test
    public void testMd5() {
        System.out.println(new Md5Hash("123456","lqsblog",3).toString());

    }

    @Test
    public void testKey() {
        String categoryIds = "1,2,3";
        ArrayList<String> objects = new ArrayList<>();
        String[] catArr = categoryIds.split(",");
        for (int i = catArr.length - 1; i >= 0; i--) {
            String join = StringUtils.join(Arrays.asList(catArr), '_');

            objects.add("category_" + catArr[i]);
            objects.add("category_" + join);
            catArr[i] = "0";
        }
        String j = StringUtils.join(objects, ' ');
        System.out.println(j);
        System.out.println(objects);
    }

    @Test
    public void textRandom() {
        Random random = new Random();
        double v = random.nextDouble();
        int i = random.nextInt(10);
        System.out.println(i);
    }

    @Test
    public void textRandomUUID() {
        String s = UUID.randomUUID().toString().substring(0,9);
        System.out.println(s);
    }
}