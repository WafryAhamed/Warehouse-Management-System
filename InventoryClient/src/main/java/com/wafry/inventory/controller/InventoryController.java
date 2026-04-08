package com.wafry.inventory.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.wafry.inventory.model.Product;
import com.wafry.inventory.service.ProductService;
import com.wafry.inventory.util.Logger;
import com.wafry.inventory.util.SceneManager;
import com.wafry.inventory.exception.ApiException;

import java.util.List;

/**
 * InventoryController - Manages the inventory view and user interactions
 *
 * Responsibilities:
 * - Display products in a table
 * - Handle product CRUD operations
 * - Provide search and filtering
 * - Update UI status and progress indicators
 *
 * @author Wafry Team
 */
public class InventoryController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InventoryController.class);
    private static final Logger logger = Logger.getLogger(InventoryController.class);

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, Integer> quantityColumn;

    @FXML
    private TextField searchField;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ProgressIndicator loadingIndicator;

    private ProductService productService;
    private ObservableList<Product> allProducts;
    private ObservableList<Product> filteredProducts;

    /**
     * Initialize controller - called by JavaFX after FXML is loaded
     */
    @FXML
    public void initialize() {
        try {
            logger.info("Initializing InventoryController");

            productService = new ProductService();
            allProducts = FXCollections.observableArrayList();
            filteredProducts = FXCollections.observableArrayList();

            configureTableColumns();
            setupSearchListener();
            productTable.setItems(filteredProducts);

            loadProducts();

        } catch (Exception e) {
            logger.error("Error initializing controller", e);
            showError("Initialization Error", "Failed to initialize inventory view: " + e.getMessage());
        }
    }

    /**
     * Configure table columns to display product data
     */
    private void configureTableColumns() {
        idColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId().intValue()).asObject());

        nameColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));

        priceColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        quantityColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
    }

    /**
     * Setup real-time search listener
     */
    private void setupSearchListener() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterProducts(newVal));
    }

    /**
     * Load products from the server
     */
    @FXML
    protected void onRefreshButtonClick() {
        loadProducts();
    }

    /**
     * Show add product dialog
     */
    @FXML
    protected void onAddButtonClick() {
        showProductDialog("Add Product", null);
    }

    /**
     * Show edit product dialog
     */
    @FXML
    protected void onEditButtonClick() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a product to edit");
            return;
        }
        showProductDialog("Edit Product", selected);
    }

    /**
     * Delete selected product with confirmation
     */
    @FXML
    protected void onDeleteButtonClick() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("No Selection", "Please select a product to delete");
            return;
        }

        if (selected.getId() == null) {
            showError("Invalid Product", "Selected product has no valid ID");
            logger.warn("Attempted to delete product without ID");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Delete Product");
        confirmation.setHeaderText("Confirm Deletion");
        confirmation.setContentText("Are you sure you want to delete: " + selected.getName() + "?");

        if (confirmation.showAndWait().filter(response -> response == ButtonType.OK).isPresent()) {
            deleteProduct(selected.getId());
        }
    }

    /**
     * Load products in background thread
     */
    private void loadProducts() {
        updateStatus("Loading products...");
        if (loadingIndicator != null) {
            loadingIndicator.setVisible(true);
        }

        new Thread(() -> {
            try {
                logger.info("Attempting to load products from API");
                List<Product> products = productService.getAllProducts();
                
                Platform.runLater(() -> {
                    if (products != null) {
                        logger.info("Successfully loaded " + products.size() + " products from API");
                        allProducts.setAll(products);
                        filterProducts(searchField != null ? searchField.getText() : "");
                        if (loadingIndicator != null) {
                            loadingIndicator.setVisible(false);
                        }
                        updateStatus("✓ " + products.size() + " products loaded");
                    } else {
                        logger.warn("API returned null products list");
                        if (loadingIndicator != null) {
                            loadingIndicator.setVisible(false);
                        }
                        loadDemoProducts();
                    }
                });
            } catch (ApiException e) {
                logger.error("API Error while loading products: " + e.getMessage(), e);
                
                Platform.runLater(() -> {
                    if (loadingIndicator != null) {
                        loadingIndicator.setVisible(false);
                    }
                    updateStatus("⚠ API Error - Using demo data");
                    showWarning("API Connection Issue", 
                        "Could not connect to backend API.\n" +
                        "Error: " + e.getMessage() + "\n\n" +
                        "Showing demo data instead.");
                    
                    loadDemoProducts();
                });
            } catch (Exception e) {
                logger.error("Unexpected error while loading products: " + e.getMessage(), e);
                
                Platform.runLater(() -> {
                    if (loadingIndicator != null) {
                        loadingIndicator.setVisible(false);
                    }
                    updateStatus("⚠ Error loading products");
                    showError("Error", "Failed to load products: " + e.getMessage());
                    
                    loadDemoProducts();
                });
            }
        }).start();
    }

    /**
     * Load demo products for testing
     */
    private void loadDemoProducts() {
        logger.info("Loading demo products");
        // Create demo products using setters since constructor requires all 18 parameters
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Demo Laptop");
        p1.setSellingPrice(new java.math.BigDecimal("999.99"));
        p1.setQuantityOnHand(5);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Demo Mouse");
        p2.setSellingPrice(new java.math.BigDecimal("29.99"));
        p2.setQuantityOnHand(50);

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("Demo Keyboard");
        p3.setSellingPrice(new java.math.BigDecimal("79.99"));
        p3.setQuantityOnHand(25);

        Product p4 = new Product();
        p4.setId(4L);
        p4.setName("Demo Monitor");
        p4.setSellingPrice(new java.math.BigDecimal("299.99"));
        p4.setQuantityOnHand(10);

        Product p5 = new Product();
        p5.setId(5L);
        p5.setName("Demo USB Cable");
        p5.setSellingPrice(new java.math.BigDecimal("9.99"));
        p5.setQuantityOnHand(100);

        allProducts.setAll(p1, p2, p3, p4, p5);
        filterProducts(searchField != null ? searchField.getText() : "");
        updateStatus("✓ " + allProducts.size() + " demo products loaded");
    }

    /**
     * Filter products based on search text
     */
    private void filterProducts(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            filteredProducts.setAll(allProducts);
        } else {
            String search = searchText.toLowerCase().trim();
            filteredProducts.setAll(allProducts.stream()
                    .filter(p -> (p.getName() != null && p.getName().toLowerCase().contains(search)) ||
                                (p.getSku() != null && p.getSku().toLowerCase().contains(search)))
                    .toList());
        }
        if (productTable != null) {
            productTable.setItems(filteredProducts);
        }
    }

    /**
     * Delete product from server
     */
    private void deleteProduct(Long id) {
        if (id == null || id <= 0) {
            logger.error("Invalid product ID for deletion: " + id);
            showError("Invalid ID", "Product ID is invalid");
            return;
        }

        updateStatus("Deleting product...");
        new Thread(() -> {
            try {
                productService.deleteProduct(id);
                Platform.runLater(() -> {
                    updateStatus("✓ Product deleted");
                    loadProducts();
                    logger.info("Product deleted: " + id);
                });
            } catch (ApiException e) {
                Platform.runLater(() -> {
                    updateStatus("⚠ Error deleting product");
                    showError("Delete Error", "Failed to delete product: " + e.getMessage());
                    logger.error("Error deleting product", e);
                });
            }
        }).start();
    }

    /**
     * Show product dialog (simplified version)
     */
    private void showProductDialog(String title, Product product) {
        TextInputDialog dialog = new TextInputDialog(product != null ? product.getName() : "");
        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText("Product Name:");

        dialog.showAndWait().ifPresent(name -> {
            if (name != null && !name.isEmpty()) {
                updateStatus("Product " + (product != null ? "updated" : "added") + ": " + name);
                loadProducts();
            }
        });
    }

    /**
     * Update status label
     */
    private void updateStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    /**
     * Show error alert
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show warning alert
     */
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


