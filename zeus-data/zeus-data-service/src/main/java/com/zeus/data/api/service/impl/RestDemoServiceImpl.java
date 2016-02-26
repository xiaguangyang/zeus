package com.zeus.data.api.service.impl;

import com.zeus.data.api.service.DemoDomain;
import com.zeus.data.api.service.RestDemoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by quweixin on 16/2/26.
 */
@Path("restDemo")
public class RestDemoServiceImpl implements RestDemoService {

    @GET
    @Path("getDemoDomain")
    @Produces("application/json;charset=UTF-8")
    public DemoDomain getDemoDomain() {
        DemoDomain demoDomain =  new DemoDomain();
        demoDomain.setIdCard(100);
        demoDomain.setNo("张三");
        return demoDomain;
    }
}
