package com.zeus.data.api.service.impl;

import com.zeus.data.api.service.DemoService;
import javax.inject.Inject;
import javax.inject.Named;
/**
 * Created by quweixin on 16/2/25.
 */
@Named
public class DemoServiceImpl implements DemoService {


    public void demoService() {
        System.out.println("Hello World!");
    }
}
