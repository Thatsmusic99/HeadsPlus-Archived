package io.github.thatsmusic99.headsplus.nms.v1_12_NMS;

import io.github.thatsmusic99.headsplus.nms.RecipeManager;
import org.bukkit.inventory.ShapelessRecipe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class RecipeManager1_12 extends RecipeManager {

    @Override
    public void removeRecipe(ShapelessRecipe r) {
        r.getResult().setType(org.bukkit.Material.AIR);
    }

    private Field stripPrivateFinal(Class clazz, String field) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        Field fieldF = clazz.getDeclaredField(field);
        fieldF.setAccessible(true);
        // Remove final modifier
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(fieldF, fieldF.getModifiers() & ~Modifier.FINAL);
        return fieldF;
    }

    private Object getPrivateField(String fieldName, Class clazz, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field;
        Object o;
        field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        o = field.get(object);
        return o;
    }
}
