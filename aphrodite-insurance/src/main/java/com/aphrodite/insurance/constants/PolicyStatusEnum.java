package com.aphrodite.insurance.constants;


import com.aphrodite.common.constants.ErrCodeEnum;
import com.aphrodite.common.exception.AphroditeException;
import lombok.Getter;

/**
 * 保单状态枚举 - 基于核保通过且首期保费已支付后的生命周期
 */
@Getter
public enum PolicyStatusEnum {
    E("E", "正常缴费且有效，但未交清 Effective"),

    /** 已交清 (保费已缴全，保障继续) */
    P("P", "有效，已交清 Paid-up"),

    /** 中止 (因欠费导致效力暂时停止) */
    L("L", "欠费中止 Lapsed"),

    /** 满期终止 (自然到期) */
    M("M", "满期 Matured"),

    /** 退保终止 (客户主动解除) */
    S("S", "退保 Surrendered"),

    /** 理赔终止 (因理赔导致责任履行完毕) */
    C("C", "理赔终止 Claim Settled");

    private final String code;
    private final String description;

    PolicyStatusEnum(String codeName, String description) {
        this.code = codeName;
        this.description = description;
    }

    /**
     * 根据字母代码获取枚举对象
     */
    public static PolicyStatusEnum checkAndGet(String code) {
        for (PolicyStatusEnum status : PolicyStatusEnum.values()) {
            if (status.name().equals(code)) {
                return status;
            }
        }
        throw new AphroditeException(ErrCodeEnum.ILLEGAL_POLICY_STATUS, String.format("Illegal policy status: %s", code));
    }
}