package com.ecom.config;

import com.ecom.entity.Country;
import com.ecom.entity.Product;
import com.ecom.entity.ProductCategory;
import com.ecom.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        HttpMethod[] theUnsupportedActions = {HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE, HttpMethod.PATCH};

        // Disable http methods for Product:
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        // Disable http methods for ProductCategory:
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        // Disable http methods for ProductCategory:
        disableHttpMethods(Country.class, config, theUnsupportedActions);

        // Disable http methods for ProductCategory:
        disableHttpMethods(State.class, config, theUnsupportedActions);

//        Call an internal helper method:
        exposeIds(config);

//        configure cors mapping:
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

//    Common Method to disable http methods for classes:
    private static void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
//        expose entity ids:

//        - get a list of all entity classes from the entity manager:
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

//        - create an array of the entity types:
        List<Class> entityClasses = new ArrayList<>();

//        - get the entity types for the entities:
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

//        - expose the entity ids for the array of entity/domain types:
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}
