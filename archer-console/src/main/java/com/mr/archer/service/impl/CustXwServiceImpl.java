package com.mr.archer.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mr.archer.dao.CustXwMapper;
import com.mr.archer.entity.CustomerInfo;
import com.mr.archer.service.CustXwService;
import org.springframework.stereotype.Service;

@Service
public class CustXwServiceImpl extends ServiceImpl<CustXwMapper, CustomerInfo> implements CustXwService {

}
