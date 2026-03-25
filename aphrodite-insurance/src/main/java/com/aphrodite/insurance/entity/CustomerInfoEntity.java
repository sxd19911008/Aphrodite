package com.aphrodite.insurance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * CUSTOMER_INFO 客户信息表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("CUSTOMER_INFO")
@Schema(title = "客户信息表")
public class CustomerInfoEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    @Schema(title = "客户主键ID (自增)")
    private Long id;

    @Schema(title = "客户姓名")
    private String customerName;

    @Schema(title = "出生日期")
    private LocalDateTime birthDate;

    @Schema(title = "性别", description = "M-男;F-女")
    private String gender;

    @Schema(title = "联系电话")
    private String phoneNumber;

    @Schema(title = "居住地址")
    private String address;

    @Schema(title = "生存状态", description = "1-生存, 0-死亡")
    private Long lifeStatus;

    @Schema(title = "创建人")
    private String createdBy;

    @Schema(title = "创建时间")
    private LocalDateTime createdAt;

    @Schema(title = "更新人")
    private String updatedBy;

    @Schema(title = "更新时间")
    private LocalDateTime updatedAt;
}

