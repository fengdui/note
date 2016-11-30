package com.zheyue.encrypt.service;

import com.zheyue.encrypt.dao.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author FD
 * @date 2016/11/30
 */

@Service
public class MaterialService {

    @Autowired
    private MaterialMapper materialMapper;

    public String getPathById(Integer materialid) {

        String path = null;
        try {
            path = materialMapper.selectPathById(materialid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
