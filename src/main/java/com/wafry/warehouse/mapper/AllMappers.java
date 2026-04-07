package com.wafry.warehouse.mapper;

import com.wafry.warehouse.entity.*;
import com.wafry.warehouse.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product entity);
    Product toEntity(ProductDTO dto);
    List<ProductDTO> toDTOList(List<Product> entities);
}

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseDTO toDTO(Warehouse entity);
    Warehouse toEntity(WarehouseDTO dto);
    List<WarehouseDTO> toDTOList(List<Warehouse> entities);
}

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDTO toDTO(Location entity);
    Location toEntity(LocationDTO dto);
    List<LocationDTO> toDTOList(List<Location> entities);
}

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierDTO toDTO(Supplier entity);
    Supplier toEntity(SupplierDTO dto);
    List<SupplierDTO> toDTOList(List<Supplier> entities);
}

@Mapper(componentModel = "spring")
public interface OrderMapper {
    PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder entity);
    PurchaseOrder toPurchaseOrderEntity(PurchaseOrderDTO dto);

    SalesOrderDTO toSalesOrderDTO(SalesOrder entity);
    SalesOrder toSalesOrderEntity(SalesOrderDTO dto);

    List<PurchaseOrderDTO> toPurchaseOrderDTOList(List<PurchaseOrder> entities);
    List<SalesOrderDTO> toSalesOrderDTOList(List<SalesOrder> entities);
}

