package com.kucharka.kucharka;

import com.kucharka.kucharka.DTO.RecipeDTO;
import com.kucharka.kucharka.DTO.RecipeGroceryDTO;
import com.kucharka.kucharka.entity.Recipe;
import com.kucharka.kucharka.entity.RecipeGrocery;
import com.kucharka.kucharka.entity.User;
import com.kucharka.kucharka.exception.UserNotFoundException;
import com.kucharka.kucharka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    private final UserRepository userRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Long, User> userIdToUserConverter = context -> {
            Long userId = context.getSource();
            if (userId == null) {
                throw new UserNotFoundException("User not found");
            }
            return userRepository.findById(userId).orElse(null);
        };

        // Mapping from Recipe to RecipeDTO
        modelMapper.createTypeMap(Recipe.class, RecipeDTO.class)
                .addMapping(src -> src.getUser().getId(), RecipeDTO::setUserId);

        // Mapping from RecipeDTO to Recipe
        modelMapper.createTypeMap(RecipeDTO.class, Recipe.class)
                .addMappings(mapper -> {
                    mapper.using(userIdToUserConverter).map(RecipeDTO::getUserId, Recipe::setUser);
                });

        // Mapping from RecipeGrocery to RecipeGroceryDTO
        TypeMap<RecipeGrocery, RecipeGroceryDTO> recipeGroceryToDTOTypeMap =
                modelMapper.createTypeMap(RecipeGrocery.class, RecipeGroceryDTO.class);
        recipeGroceryToDTOTypeMap.addMapping(src -> src.getGrocery().getId(), RecipeGroceryDTO::setGroceryId);

        // Mapping from RecipeGroceryDTO to RecipeGrocery
        TypeMap<RecipeGroceryDTO, RecipeGrocery> recipeGroceryDTOToTypeMap =
                modelMapper.createTypeMap(RecipeGroceryDTO.class, RecipeGrocery.class);
        recipeGroceryDTOToTypeMap.addMapping(RecipeGroceryDTO::getGroceryId, (dest, value) -> dest.getGrocery().setId((Long) value));

        return modelMapper;
    }
}
