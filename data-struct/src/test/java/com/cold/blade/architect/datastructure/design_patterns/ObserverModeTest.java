package com.cold.blade.architect.datastructure.design_patterns;

import org.junit.Assert;
import org.junit.Test;

import com.cold.blade.architect.BaseTest;
import com.cold.blade.architect.design_patterns.ObserverMode.Guardian;
import com.cold.blade.architect.design_patterns.ObserverMode.NewYorkTimes;
import com.cold.blade.architect.design_patterns.ObserverMode.NewsSubject;

/**
 * @version 1.0
 */
public class ObserverModeTest extends BaseTest {

    @Test
    public void testNotifyAndReceive() {
        NewYorkTimes newYorkTimes = new NewYorkTimes();
        Guardian guardian = new Guardian();
        NewsSubject subject = new NewsSubject();
        subject.register(newYorkTimes::dealNewsNotification);
        subject.register(guardian::dealNewsNotification);

        subject.notification("the queen says her most favourite food is bread");
        Assert.assertFalse(newYorkTimes.isDealt());
        Assert.assertTrue(guardian.isDealt());

        subject.notification("the queen says her has a lot of money");
        Assert.assertTrue(newYorkTimes.isDealt());
        Assert.assertTrue(guardian.isDealt());
    }
}
