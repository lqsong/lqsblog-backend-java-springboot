package cc.liqingsong.api;

import cc.liqingsong.common.utils.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class IdWorkerTests {


    @Autowired
    private IdWorker idWorker;

    //@Test
    public void testIdWorker() {
        String id = String.valueOf(idWorker.nextId());
        System.out.println("================== ID Start===================");
        System.out.println(id);
        System.out.println("================== ID End===================");
    }
}
