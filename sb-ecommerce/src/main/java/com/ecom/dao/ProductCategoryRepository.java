package com.ecom.dao;

import com.ecom.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
@RepositoryRestResource(collectionResourceRel = "productCategory", path = "product-category", excerptProjection = ProductCategoryRepository.ProductCategoryProjection.class)
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
        @Projection(name = "productCategoryProjection", types = { ProductCategory.class })
        public interface ProductCategoryProjection {
            Long getId();
            String getCategoryName();
            String getIcon();
        }
    }

