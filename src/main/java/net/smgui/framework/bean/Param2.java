package net.smgui.framework.bean;

import net.smgui.framework.util.CastUtil;
import net.smgui.framework.util.CollectionUtil;
import net.smgui.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fd on 2016/7/6.
 */
public class Param2 {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param2(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param2(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        if (CollectionUtil.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<String, List<FileParam>>();
        if (CollectionUtil.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList1;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList1 = fileMap.get(fieldName);
                }
                else {
                    fileParamList1 = new ArrayList<FileParam>();
                }
                fileParamList1.add(fileParam);
                fileMap.put(fieldName, fileParamList1);
            }
        }
        return fileMap;
    }

    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }
    public boolean isEmpty() {
        return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isEmpty(fileParamList);
    }

    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }

    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
