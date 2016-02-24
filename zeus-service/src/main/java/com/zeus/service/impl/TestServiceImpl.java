package com.zeus.service.impl;

import com.zeus.service.TestService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created by quweixin on 16/2/24.
 */
public class TestServiceImpl implements TestService {

    public void testService() {
        System.out.print("测试");
    }
}
