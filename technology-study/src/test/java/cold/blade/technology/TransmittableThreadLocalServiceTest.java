package cold.blade.technology;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cold.blade.technology.service.TransmittableThreadLocalService;

/**
 * @author cold_blade
 * @date 2022/3/1
 * @version 1.0
 */
public class TransmittableThreadLocalServiceTest extends BaseTest {

    @Autowired
    private TransmittableThreadLocalService service;

    @Test
    public void test() {
        service.execute("cold_blade");
    }
}
