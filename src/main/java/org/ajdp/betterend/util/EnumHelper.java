package org.ajdp.betterend.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import com.google.common.collect.ImmutableList;

public class EnumHelper {

	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T create(Class<T> type, String name, int index, Object... params)
			throws Exception {
		Constructor<?> c = getConstructor(type, Arrays.stream(params).map(e -> e.getClass()).toArray(Class[]::new));
		Object accessor = getConstructorAccessor(c);
		Method newInstance = accessor.getClass().getMethod("newInstance", Object[].class);
		newInstance.setAccessible(true);
		T t = (T) newInstance.invoke(accessor, new Object[] {
				new ImmutableList.Builder<>().add(name).add(index).addAll(Arrays.asList(params)).build().toArray() });
		Field values = null;
		for (Field field : type.getDeclaredFields())
			if (field.getType().isArray() && field.getType().getComponentType() == type
					&& field.getName().contains("$VALUES")) {
				values = field;
				break;
			}
		if (values == null) {
			System.err.println("cant extend values!");
			return t;
		}
		makeAccessible(values);
		T[] ts = (T[]) values.get(null);
		ts = ImmutableList.<T>builder().addAll(Arrays.asList(ts)).add(t).build().toArray(ts);
		values.set(null, ts);
		return t;
	}

	private static Constructor<?> getConstructor(Class<?> type, Class<?>[] paramTypes) {
		for (Constructor<?> c : type.getDeclaredConstructors()) {
			Class<?>[] types = c.getParameterTypes();
			if (types.length != paramTypes.length + 2)
				continue;
			boolean cnt = false;
			for (int i = 2; i < types.length; i++) {
				Class<?> c1 = types[i];
				Class<?> c2 = paramTypes[i - 2];
				if (!c1.isAssignableFrom(c2))
					cnt = true;
			}
			if (!cnt)
				return c;
		}
		return null;
	}

	public static void makeAccessible(Field field) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	}

	private static Object getConstructorAccessor(Constructor<?> c) throws Exception {
		Field ca = Constructor.class.getDeclaredField("constructorAccessor");
		ca.setAccessible(true);
		Object accessor = ca.get(c);
		if (accessor == null) {
			Method m = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
			m.setAccessible(true);
			accessor = m.invoke(c);
			ca.set(c, accessor);
		}
		return accessor;
	}
}
