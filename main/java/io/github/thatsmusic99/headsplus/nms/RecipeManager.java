package io.github.thatsmusic99.headsplus.nms;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeManager {

    public boolean hasNext() {
        return Bukkit.recipeIterator().hasNext();
    }

    public void addRecipe(ShapelessRecipe r) {
        Bukkit.addRecipe(r);
    }

    public void removeRecipe(ShapelessRecipe r) {
        if (Bukkit.recipeIterator().hasNext()) {
            if (Bukkit.recipeIterator().next() == r) {
                Bukkit.recipeIterator().remove();
            }
        }
    }
}
