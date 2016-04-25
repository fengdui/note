package net.smgui.service;

import net.smgui.common.Result;


public interface UserService {
    public Result login(String id, String pwd);
}
