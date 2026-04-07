package com.wafry.warehouse.entity;

import jakarta.persistence.*;
import lombok.*;

public enum MovementType {
    IN, OUT, TRANSFER, ADJUSTMENT
}

