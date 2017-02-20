package com.fengdui.oa.framework.util.reflection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wander.Zeng
 * @data 2015-7-2 下午2:25:23
 * @desc ReflectionUtil.java
 */
public class ReflectionUtil {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class<T> getSuperClassParam(final Class clazz) {
		return getSuperClassParam(clazz, 0);
	}

	/**
	 * 获得父类泛型的第 index 类
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassParam(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();// 获得带有泛型的父类
		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();// 如果支持泛型，返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class，因为可能有多个，所以是数组
		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	public static List<Field> getAnnotationFieldList(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
		List<Field> fieldList = new ArrayList<Field>();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fieldArray = superClass.getDeclaredFields();
			for (Field field : fieldArray) {
				if (field.isAnnotationPresent(annotationClass)) {
					field.setAccessible(true);
					fieldList.add(field);
				}
			}
		}
		return fieldList;
	}

	public static boolean hasAnnotationField(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fieldArray = superClass.getDeclaredFields();
			for (Field field : fieldArray) {
				if (field.isAnnotationPresent(annotationClass)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 调用Get方法
	 */
	public static Object invokeMethodOfGet(Object object, String propertyName) {
		String methodName = "get" + StringUtils.capitalize(propertyName);
		return invokeMethod(object, methodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用Set方法. 使用value的Class来查找Set方法.
	 */
	public static void invokeMethodOfSet(Object object, String propertyName, Object value) {
		invokeMethodOfSet(object, propertyName, value, null);
	}

	/**
	 * 调用Set方法. 用于查找Setter方法,为空时使用value的Class替代.
	 */
	public static void invokeMethodOfSet(Object obj, String propertyName, Object value, Class<?> propertyType) {
		Class<?> type = propertyType != null ? propertyType : value.getClass();
		String methodName = "set" + StringUtils.capitalize(propertyName);
		invokeMethod(obj, methodName, new Class[] { type }, new Object[] { value });
	}

	/**
	 * 直接调用对象方法, 无视private/protected修饰符. 用于一次性调用的情况.
	 */
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
		Method method = getAccessibleMethod(object, methodName, parameterTypes);
		if (null == method) {
			logger.error("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		try {
			return method.invoke(object, args);
		} catch (Exception e) {
			logger.error("invokeMethod error : " + e);
			return null;
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredMethod,并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 * 先使用本函数先取得Method,然后调用Method.invoke(Object obj, Object... args)
	 */
	public static Method getAccessibleMethod(final Object object, final String methodName, final Class<?>... parameterTypes) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
				method.setAccessible(true);
				return method;
			} catch (Exception e) {
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static Object getFieldValue(final Object object, final String fieldName) {
		Field field = getAccessibleField(object, fieldName);

		if (field == null) {
			logger.error("Could not find field [" + fieldName + "] on target [" + object + "]");
			return null;
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (Exception e) {
			logger.error("getFieldValue error : " + e);
		}
		return result;
	}

	/**
	 * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
	 */
	public static void setFieldValue(final Object object, final String fieldName, final Object value) {
		Field field = getAccessibleField(object, fieldName);

		if (field == null) {
			logger.error("Could not find field [" + fieldName + "] on target [" + object + "]");
		}

		try {
			field.set(object, value);
		} catch (Exception e) {
			logger.error("setFieldValue error : " + e);
		}
	}

	/**
	 * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问. 如向上转型到Object仍无法找到, 返回null.
	 */
	public static Field getAccessibleField(final Object object, final String fieldName) {
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (Exception e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
