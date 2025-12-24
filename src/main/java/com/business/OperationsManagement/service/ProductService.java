package com.business.OperationsManagement.service;

import com.business.OperationsManagement.dto.CreateProductRequest;
import com.business.OperationsManagement.dto.ProductResponse;
import com.business.OperationsManagement.entity.Product;
import com.business.OperationsManagement.enums.ProductCategory;
import com.business.OperationsManagement.exception.ResourceNotFoundException;
import com.business.OperationsManagement.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.EnumSet;


@Service
public class ProductService {

    private final ProductRepository repository;
    private final ImageStorageService imageStorageService;

    public ProductService(
            ProductRepository repository,
            ImageStorageService imageStorageService
    ) {
        this.repository = repository;
        this.imageStorageService = imageStorageService;
    }
    
    private static final EnumSet<ProductCategory> CATEGORY_WITH_PRICE =
            EnumSet.of(
                    ProductCategory.SPARE_PARTS,
                    ProductCategory.ACCESSORIES,
                    ProductCategory.OIL
            );

    private static final EnumSet<ProductCategory> CATEGORY_WITH_CONTACT =
            EnumSet.of(
                    ProductCategory.MOTORS,
                    ProductCategory.SEWING_MACHINE_TOP,
                    ProductCategory.STAND,
                    ProductCategory.TABLE
            );


    // ADMIN: create product
    public ProductResponse createProduct(CreateProductRequest request, MultipartFile image) {
        // validations already written by you ✅

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStockQuantity());
        product.setRestockThreshold(request.getRestockThreshold());
        product.setUnitPrice(request.getUnitPrice());
        product.setWhatsappLink(request.getWhatsappLink());

        if (image != null && !image.isEmpty()) {
            String imageUrl = imageStorageService.store(image);
            product.setImageUrl(imageUrl);
        }

        product.setNeedsRestock(
            request.getStockQuantity() <= request.getRestockThreshold()
        );

        return ProductMapper.toResponse(repository.save(product));
    }


    // ADMIN: list all products
    public List<ProductResponse> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // ADMIN: filter by category
    public List<ProductResponse> getByCategory(ProductCategory category) {
        return repository.findByCategory(category)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // ADMIN: restock alerts
    public List<ProductResponse> getRestockProducts() {
        return repository.findByNeedsRestockTrue()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    // INTERNAL (used later by sales)
    public Product getProductOrThrow(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    // INTERNAL: update stock safely
    public void updateStock(Product product, int newQuantity) {
        product.setStockQuantity(newQuantity);
        product.setNeedsRestock(
                newQuantity <= product.getRestockThreshold()
        );
        repository.save(product);
    }
    
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.toResponse(product);
    }
    
    public ProductResponse updateProduct(
            Long productId,
            CreateProductRequest request,
            MultipartFile image
    ) {

        Product product = repository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // ===== VALIDATIONS (reuse same rules as create) =====
        if (CATEGORY_WITH_PRICE.contains(request.getCategory())
                && request.getUnitPrice() == null) {
            throw new IllegalArgumentException("Price is required for this category");
        }

        if (CATEGORY_WITH_CONTACT.contains(request.getCategory())
                && (request.getWhatsappLink() == null || request.getWhatsappLink().isBlank())) {
            throw new IllegalArgumentException("WhatsApp link is required for this category");
        }

        // ===== UPDATE FIELDS =====
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStockQuantity());
        product.setRestockThreshold(request.getRestockThreshold());
        product.setUnitPrice(request.getUnitPrice());
        product.setWhatsappLink(request.getWhatsappLink());
        product.setDescription(request.getDescription());

        // ===== IMAGE UPDATE (optional) =====
        if (image != null && !image.isEmpty()) {
            String imageUrl = imageStorageService.store(image);
            product.setImageUrl(imageUrl);
        }

        // ===== RESTOCK LOGIC =====
        product.setNeedsRestock(
                request.getStockQuantity() <= request.getRestockThreshold()
        );

        return ProductMapper.toResponse(repository.save(product));
    }


}
