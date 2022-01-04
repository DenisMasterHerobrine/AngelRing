package dev.denismasterherobrine.angelring.recipes;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;

public class ClassicAngelRingRecipeProvider extends RecipeProvider {
    public ClassicAngelRingRecipeProvider(final DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    /*
    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> recipeConsumer) {
        {
            System.out.println("Initialize BuildShapelessRecipe method invocation");
            if (!Configuration.BalancedRecipe.get()) {
                System.out.println("Classic Recipe is pushed to ShapedRecipeBuilder");
                ShapedRecipeBuilder.shaped(ItemRegistry.ItemRing, 1)
                        .pattern("FGF")
                        .pattern("GEG")
                        .pattern("XGX")
                        .define('F', Items.FEATHER)
                        .define('E', ItemRegistry.ItemDiamondRing)
                        .define('G', Items.GOLD_BLOCK)
                        .define('X', Items.BLAZE_ROD)
                        .unlockedBy("has_feather", has(Items.FEATHER))
                        .save(recipeConsumer);
            }
        }

            {
            if (Configuration.BalancedRecipe.get()){
                System.out.println("Balanced Recipe is pushed to ShapedRecipeBuilder");
                ShapedRecipeBuilder.shaped(ItemRegistry.ItemRing, 1)
                    .pattern("FXF")
                    .pattern("GEG")
                    .pattern("NGN")
                    .define('F', Items.FEATHER)
                    .define('E', ItemRegistry.ItemDiamondRing)
                    .define('G', Items.GOLD_BLOCK)
                    .define('X', Items.BLAZE_ROD)
                    .define('N', Items.NETHER_STAR)
                    .unlockedBy("has_nether_star", has(Items.NETHER_STAR))
                    .save(recipeConsumer);
            }
        }


    }
    */
}